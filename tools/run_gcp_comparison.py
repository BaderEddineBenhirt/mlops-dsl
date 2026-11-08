"""Compare actual exported GCP generation with a separately authored baseline.

Never generates DSL output in Python. Requires hash-checked Xtext observations.
"""
import argparse
import csv
import json
import math
import platform
import re
import shutil
from pathlib import Path

import yaml

from journal_common import ROOT, command, digest, domain_checks, files, inventory, parse, resources, root_defaults, save, stamp, terraform
from run_comparison import select
from run_configuration_evolution import changes
from run_fault_comparison import replace_once, update_yaml
from run_journal_comparison import materialize

STUDY = ROOT / "evaluation/journal/gcp"
FIXTURE = ROOT / "mlops-tests/src/validation/gcp/c10_fraud_gcp.mlops"


def cases():
    with (STUDY / "cases.tsv").open(encoding="utf-8", newline="") as stream:
        return list(csv.DictReader(stream, delimiter="\t", quoting=csv.QUOTE_NONE))


def changed_source(original, case):
    original = original.replace("\r\n", "\n")
    if not case["old"]:
        return original
    old, new = case["old"].split("||"), case["new"].split("||")
    if len(old) != len(new):
        raise ValueError("Unpaired mutation: " + case["case_id"])
    for a, b in zip(old, new):
        a, b = a.replace("\\n", "\n"), b.replace("\\n", "\n")
        if not a or a not in original:
            raise ValueError("Missing mutation target: " + case["case_id"])
        original = original.replace(a, b)
    return original


def equal(a, b):
    if isinstance(a, bool) or isinstance(b, bool):
        return type(a) is type(b) and a == b
    if isinstance(a, (int, float)) and isinstance(b, (int, float)):
        return math.isfinite(a) and math.isfinite(b) and math.isclose(a, b, abs_tol=1e-6)
    if isinstance(a, list) and isinstance(b, list):
        return len(a) == len(b) and all(equal(x, y) for x, y in zip(a, b))
    if isinstance(a, dict) and isinstance(b, dict):
        return a.keys() == b.keys() and all(equal(a[k], b[k]) for k in a)
    return type(a) is type(b) and a == b


def gcp_domain_checks(docs):
    issues = domain_checks(docs)
    for pool in resources(docs, "google_container_node_pool"):
        scale = pool.get("autoscaling", [{}])[0]
        lo, desired, hi = scale.get("min_node_count"), pool.get("initial_node_count"), scale.get("max_node_count")
        if not all(type(v) is int for v in (lo, desired, hi)) or not 0 <= lo <= desired <= hi:
            issues.append({"rule": "S07", "detail": "GKE per-zone initial count must satisfy min <= initial <= max"})
    return issues


def contract_for(case):
    contract = json.loads((STUDY / "contract.json").read_text(encoding="utf-8"))
    cid = case["case_id"]
    if cid == "GE01":
        contract = json.loads(json.dumps(contract).replace("fraud_classifier_v1", "fraud_classifier_v2"))
    for check in contract["checks"]:
        if cid in {"GC02", "GC03", "GE02"} and check["id"] in {
                "container-port", "readiness-port", "liveness-port", "service-port", "target-port", "ingress-port"}:
            check["expected"] = [{"GC02": 1, "GC03": 65535, "GE02": 9092}[cid]]
        if cid == "GE03" and check["id"] == "replicas":
            check["expected"] = [5]
        if cid == "GC01" and check["id"] in {"default:public_exposure", "default:tls_enabled"}:
            check["expected"] = False
    if cid == "GC01":
        contract["checks"] = [c for c in contract["checks"] if c["id"] not in {"tls-host", "tls-secret"}]
        contract["checks"].append({"id": "private-no-tls", "kind": "yaml",
            "file": "runtime/k8s/ingress.yaml", "path": ["spec", "tls"], "expected": []})
    return contract


def inspect(root, contract):
    docs, errors = parse(root)
    results = []
    for c in contract["checks"]:
        kind = c["kind"]
        if kind == "yaml":
            actual = select(docs.get(c["file"]), c["path"])
        elif kind == "resource":
            actual = len(resources(docs, c["resource"]))
        elif kind == "default":
            actual = root_defaults(docs).get(c["name"])
        elif kind == "hcl":
            actual = [v for r in resources(docs, c["resource"]) for v in select(r, c["path"])]
        elif kind == "subnets":
            # Explicit literal/count preservation only, not HCL evaluation.
            actual = [v for r in resources(docs, "google_compute_subnetwork")
                      for v in re.findall(r'[\"\x27](\d+\.\d+\.\d+\.\d+/\d+)[\"\x27]', str(r.get("ip_cidr_range", "")))]
        else:
            raise ValueError("Unknown requirement kind: " + kind)
        results.append({**c, "actual": actual, "status": "PASS" if equal(actual, c["expected"]) else "FAIL"})
    return {"parsing_errors": errors, "requirements": results,
            "domain_oracle": gcp_domain_checks(docs) if not errors else [], "inventory": inventory(root)}


def failures(observation):
    return ([{"stage": "parse", **e} for e in observation["parsing_errors"]]
            + [{"stage": "study_domain_guard", **e} for e in observation["domain_oracle"]]
            + [{"stage": "requirement", "id": c["id"]} for c in observation["requirements"] if c["status"] != "PASS"])


def import_observations(folder):
    """Reject stale/missing/duplicate cases, source mismatches and altered output."""
    data = json.loads((folder / "dsl-observations.json").read_text(encoding="utf-8"))
    # Java reads UTF-8 bytes and preserves CRLF. Compare with the packaged test
    # resource as bytes, rather than relying on text-mode newline conversion.
    test_fixture = ROOT / "org.xtext.ucd.mlopsdsl.tests/src/validation/gcp/c10_fraud_gcp.mlops"
    matrix = ROOT / "org.xtext.ucd.mlopsdsl.tests/src/validation/gcp/cases.tsv"
    if FIXTURE.read_bytes() != test_fixture.read_bytes() or matrix.read_bytes() != (STUDY / "cases.tsv").read_bytes():
        raise ValueError("Runtime and JUnit fixture/matrix copies differ")
    if data.get("base_sha256") != digest(test_fixture) or data.get("matrix_sha256") != digest(matrix):
        raise ValueError("GCP export came from a different fixture or case matrix")
    rows = data.get("cases", [])
    indexed = {r["case_id"]: r for r in rows}
    if len(indexed) != len(rows) or set(indexed) != {c["case_id"] for c in cases()}:
        raise ValueError("Missing, extra or duplicate GCP observation IDs")
    for c in cases():
        cid = c["case_id"]
        source = folder / cid / "source.mlops"
        expected = changed_source(test_fixture.read_bytes().decode("utf-8"), c)
        if source.read_bytes() != expected.encode("utf-8") or indexed[cid]["source_sha256"] != digest(source):
            raise ValueError("Source mismatch for " + cid)
        generated = folder / cid / "generated"
        recorded = indexed[cid].get("generated_manifest", [])
        paths = [m["path"] for m in recorded]
        if len(paths) != len(set(paths)):
            raise ValueError("Duplicate generated paths: " + cid)
        actual = {p.relative_to(generated).as_posix(): digest(p) for p in files(generated)} if generated.exists() else {}
        if actual != {m["path"]: m["sha256"] for m in recorded}:
            raise ValueError("Generated manifest mismatch: " + cid)
        accepted = indexed[cid]["generator"] == "ACCEPT"
        if accepted != bool(recorded):
            raise ValueError("Missing/contradictory generation evidence: " + cid)
    return data, indexed


def mutate_baseline(root, cid):
    tf = root / "infra/terraform/main.tf"
    if cid in {"GF01", "GC01"}:
        update_yaml(root, "runtime/k8s/ingress.yaml", lambda d: d["spec"].pop("tls"))
        update_yaml(root, "kustomization.yaml", lambda d: d["replacements"].pop())
        replace_once(tf, 'variable "tls_enabled" {\n  type    = bool\n  default = true',
                          'variable "tls_enabled" {\n  type    = bool\n  default = false')
    if cid == "GC01":
        replace_once(tf, 'variable "public_exposure" {\n  type    = bool\n  default = true',
                          'variable "public_exposure" {\n  type    = bool\n  default = false')
    if cid == "GF02":
        update_yaml(root, "runtime/k8s/service.yaml", lambda d: d["spec"]["ports"][0].pop("port"))
    if cid == "GF03":
        replace_once(tf, 'default = "europe-west4"', 'default = ""')
    if cid == "GF04":
        update_yaml(root, "runtime/k8s/ingress.yaml", lambda d: d["spec"]["rules"][0].update(host=""))
    if cid == "GF05":
        replace_once(tf, "initial_node_count = 3", "initial_node_count = 1")
    if cid == "GF06":
        update_yaml(root, "mlops/decision-gate.yaml", lambda d: d["decisionGate"]["criteria"][0].update(weight=0.50))
    if cid == "GF07":
        update_yaml(root, "mlops/decision-gate.yaml", lambda d: d["decisionGate"]["criteria"][0].update(minValue=1.10))
    if cid == "GE01":
        for name in ("mlops/model-registry.yaml", "mlops/cicdct.yaml"):
            p = root / name
            p.write_text(p.read_text(encoding="utf-8").replace("fraud_classifier_v1", "fraud_classifier_v2"), encoding="utf-8")
    if cid in {"GC02", "GC03", "GE02"}:
        # One authoritative Kustomize source. Preserve formatting in edit counts.
        replace_once(root / "runtime/k8s/service.yaml", "- port: 9091",
                     "- port: " + str({"GC02": 1, "GC03": 65535, "GE02": 9092}[cid]))
    if cid == "GE03":
        replace_once(root / "runtime/k8s/deployment.yaml", "replicas: 3", "replicas: 5")


def run(args):
    data, indexed = import_observations(args.observations)
    if args.out.exists():
        raise ValueError("Choose a new run directory. Existing evidence is preserved.")
    args.out.mkdir(parents=True)
    report = {"recorded_at_utc": stamp(), "status": "BLOCKED", "case": "C10_GCP_FRAUD",
              "runner_sha256": digest(Path(__file__)), "python": platform.python_version(),
              "contract_sha256": digest(STUDY / "contract.json"),
              "matrix_sha256": digest(STUDY / "cases.tsv"), "fixture_sha256": digest(FIXTURE),
              "generator_java_source_sha256": digest(ROOT / "org.xtext.ucd.mlopsdsl/src/org/xtext/ucd/mlopsdsl/generator/MlopsGenerator.java"),
              "generator_class_sha256": data.get("generator_class_sha256"),
              "platform": platform.platform(), "dsl": "IMPORTED_HASH_CHECKED", "scope_equivalence": "NOT_REVIEWED",
              "baseline_provenance": "Study-authored with assistant support, not an independent practitioner baseline",
              "deployment": "NOT_RUN", "human_effort": "NOT_MEASURED", "cases": {}, "evolution": {}}
    save(args.out / "contract.json", json.loads((STUDY / "contract.json").read_text()))
    shutil.copytree(args.observations, args.out / "eclipse-observations")
    shutil.copy2(STUDY / "cases.tsv", args.out / "cases.tsv")
    shutil.copy2(FIXTURE, args.out / "base.mlops")
    report["tool_versions"] = {}
    for name, cmd in (("kubectl", [args.kubectl, "version", "--client", "-o", "json"]),
                      ("terraform", [args.terraform, "version", "-json"])):
        report["tool_versions"][name], _ = command(cmd, args.out / "logs", name + "-version")
    try:
        for c in cases():
            cid, fault = c["case_id"], c["category"] == "fault"
            dest = args.out / cid
            maintained = dest / "baseline-source"
            shutil.copytree(STUDY / "baseline", maintained,
                            ignore=shutil.ignore_patterns(".terraform", "*.tfstate*", "*.tfplan", "__pycache__"))
            mutate_baseline(maintained, cid)
            row = {"category": c["category"], "rule": c["rule"], "dsl": indexed[cid]}
            try:
                materialize(dest / "baseline", args.kubectl, dest / "logs", source=maintained, runtime=maintained)
                baseline = inspect(dest / "baseline", contract_for(c))
                row["baseline"] = baseline
                issues = baseline["domain_oracle"]
                target = any(x["rule"] == c["rule"] for x in issues)
                unrelated = any(x["rule"] != c["rule"] for x in issues)
                row["baseline_guard"] = ("BLOCKED" if baseline["parsing_errors"] or unrelated else "DETECTED" if target else "MISSED") if fault else ("FALSE_ALARM" if failures(baseline) else "PASS")
                native = terraform(dest / "baseline", args.terraform, dest / "logs/terraform") if not fault or cid in {"GF03", "GF05"} else {"status": "NOT_RUN"}
                row["baseline_terraform"] = native
                if fault:
                    row["native_fault_outcome"] = "BLOCKED" if native["status"] == "BLOCKED" else "UNATTRIBUTED_FAILURE" if native["status"] == "FAIL" else "MISSED"
                else:
                    generated = inspect(args.out / "eclipse-observations" / cid / "generated", contract_for(c))
                    row["generated"] = generated
                    row["generated_terraform"] = terraform(args.out / "eclipse-observations" / cid / "generated", args.terraform, dest / "logs/generated-terraform")
                    row["selected_requirements"] = "FAIL" if failures(baseline) or failures(generated) else "PASS"
            except ValueError as exc:
                log = dest / "logs/kustomize.stderr.txt"
                message = log.read_text(encoding="utf-8") if log.exists() else str(exc)
                targeted = cid == "GF02" and "spec.ports.0.port" in message and "missing" in message.lower()
                row.update(baseline_guard="DETECTED" if targeted else "BLOCKED",
                           native_fault_outcome="DETECTED" if targeted else "BLOCKED", build_error=message)
            report["cases"][cid] = row
            save(dest / "observation.json", row)
            print(cid, "DSL=" + indexed[cid]["outcome"], "baseline=" + row["baseline_guard"], flush=True)
        for c in cases():
            if c["category"] != "evolution":
                continue
            cid = c["case_id"]
            d = args.out / cid
            before = args.out / "eclipse-observations/GC00"
            after = args.out / "eclipse-observations" / cid
            # Only the actual model source files belong in this edit comparison.
            for label, parent in (("before", before), ("after", after)):
                f = d / ("dsl-" + label)
                f.mkdir()
                shutil.copy2(parent / "source.mlops", f / "c10.mlops")
            generated_files = changes(before / "generated", after / "generated", d / "generated-diff")
            report["evolution"][cid] = {
                "dsl_source": changes(d / "dsl-before", d / "dsl-after", d / "dsl-diff"),
                "baseline_source": changes(args.out / "GC00/baseline-source", d / "baseline-source", d / "baseline-diff"),
                "generated_changes": generated_files,
                "generated_configuration_files_changed": sum(x["path"].endswith((".tf", ".yaml", ".yml")) for x in generated_files["files"]),
                "remaining": {k: failures(report["cases"][cid][k]) for k in ("baseline", "generated") if k in report["cases"][cid]},
                "authoring": "Mechanical paired changes, no timed human tasks"}
        fault_rows = [report["cases"][c["case_id"]] for c in cases() if c["category"] == "fault"]
        controls = [report["cases"][c["case_id"]] for c in cases() if c["category"] in {"control", "boundary"}]
        report["summary"] = {"matched_faults": len(fault_rows),
            "dsl_detected": sum(r["dsl"]["outcome"] == "DETECTED" for r in fault_rows),
            "baseline_guard_detected": sum(r["baseline_guard"] == "DETECTED" for r in fault_rows),
            "native_detected": sum(r.get("native_fault_outcome") == "DETECTED" for r in fault_rows),
            "controls": len(controls), "dsl_false_alarms": sum(r["dsl"]["outcome"] == "FALSE_ALARM" for r in controls),
            "baseline_false_alarms": sum(r["baseline_guard"] == "FALSE_ALARM" for r in controls),
            "native_outcomes": {k: sum(r.get("native_fault_outcome") == k for r in fault_rows)
                                for k in ("DETECTED", "MISSED", "BLOCKED", "UNATTRIBUTED_FAILURE")},
            "native_recall": None,
            "native_recall_note": "No complete-set native recall is inferred when any native stage is blocked or unattributed"}
        if all(r.get("native_fault_outcome") in {"DETECTED", "MISSED"} for r in fault_rows):
            report["summary"]["native_recall"] = report["summary"]["native_detected"] / len(fault_rows)
        expected = all(r["dsl"]["outcome"] == ("DETECTED" if r["category"] == "fault" else "PASS")
                       and r["dsl"]["generator"] == ("REJECT" if r["category"] == "fault" else "ACCEPT")
                       and r["baseline_guard"] == ("DETECTED" if r["category"] == "fault" else "PASS")
                       for r in report["cases"].values())
        valid_rows = [r for r in report["cases"].values() if r["category"] != "fault"]
        native_status = [r.get(k, {}).get("status", "BLOCKED") for r in valid_rows for k in ("baseline_terraform", "generated_terraform")]
        if not expected or any(r.get("selected_requirements") != "PASS" for r in valid_rows) or "FAIL" in native_status:
            report["status"] = "FAIL"
        elif any(x != "PASS" for x in native_status) or any(r.get("native_fault_outcome") in {"BLOCKED", "UNATTRIBUTED_FAILURE"} for r in fault_rows):
            report["status"] = "BLOCKED"
        else:
            report["status"] = "PASS_SELECTED_REQUIREMENTS"
    except (OSError, ValueError, KeyError) as exc:
        report["reason"] = str(exc)
    save(args.out / "report.json", report)
    print("status:", report["status"], "report:", args.out / "report.json")
    return 0 if report["status"] == "PASS_SELECTED_REQUIREMENTS" else 1


def main():
    p = argparse.ArgumentParser(description=__doc__)
    p.add_argument("--observations", type=Path, required=True, help="GCP OBSERVATIONS folder printed by the actual JUnit test")
    p.add_argument("--out", type=Path, required=True)
    p.add_argument("--kubectl", default="kubectl")
    p.add_argument("--terraform", default="terraform")
    args = p.parse_args()
    try:
        return run(args)
    except (ValueError, OSError, KeyError) as exc:
        p.error(str(exc))


if __name__ == "__main__":
    raise SystemExit(main())
