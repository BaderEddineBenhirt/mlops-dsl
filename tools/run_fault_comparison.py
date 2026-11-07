"""Execute baseline mutations and join hash-checked REAL Eclipse observations."""
import argparse
import copy
import csv
import json
import shutil
from pathlib import Path

import yaml
from journal_common import ROOT, digest, domain_checks, inventory, parse, save, stamp, terraform
from run_journal_comparison import materialize
from summarize_fault_pairs import load_matrix, summarize

INAPPLICABLE = {
    "S01": "Dataset split ratios are not emitted in the selected target configurations",
    "S02": "Operational training GPU allocation is not represented by the selected serving-infrastructure baseline",
    "S10": "Resolved model-task identities/edges are not emitted in the lifecycle-policy baseline",
    "S11": "The generated Deployment does not preserve OperationalPlan.entryCmd",
    "S12": "Dataset split ratios are not emitted in the selected target configurations",
}


def update_yaml(root, file, mutate):
    p = root / file
    doc = yaml.safe_load(p.read_text(encoding="utf-8"))
    mutate(doc)
    p.write_text(yaml.safe_dump(doc, sort_keys=False), encoding="utf-8")


def set_ports(root, value):
    def deployment(d):
        for c in d["spec"]["template"]["spec"]["containers"]:
            c["ports"][0]["containerPort"] = value
            for probe in ("readinessProbe", "livenessProbe"):
                c[probe]["httpGet"]["port"] = value
    update_yaml(root, "runtime/k8s/deployment.yaml", deployment)
    def service(d):
        d["spec"]["ports"][0].update(port=value, targetPort=value)
    update_yaml(root, "runtime/k8s/service.yaml", service)
    update_yaml(root, "runtime/k8s/ingress.yaml", lambda d: d["spec"]["rules"][0]["http"]["paths"][0]["backend"]["service"]["port"].update(number=value))


def replace_once(path, old, new):
    text = path.read_text(encoding="utf-8")
    if text.count(old) != 1:
        raise ValueError(f"Expected one mutation target in {path}: {old}")
    path.write_text(text.replace(old, new), encoding="utf-8")


def mutate(root, case):
    tf = root / "infra/terraform/main.tf"
    if case in {"S03", "C01"}:
        update_yaml(root, "runtime/k8s/ingress.yaml", lambda d: d["spec"].pop("tls"))
        if (root / "kustomization.yaml").exists():
            # Keep this seeded TLS omission structurally renderable: remove the
            # replacement whose target is the deleted TLS host.
            update_yaml(root, "kustomization.yaml", lambda d: d["replacements"].pop())
    if case == "C01":
        replace_once(tf, 'variable "public_exposure" {\n  type    = bool\n  default = true', 'variable "public_exposure" {\n  type    = bool\n  default = false')
    if case == "S04":
        update_yaml(root, "runtime/k8s/service.yaml", lambda d: d["spec"]["ports"][0].pop("port"))
    if case == "S05":
        replace_once(tf, 'default = "westeurope"', 'default = ""')
    if case == "S06":
        update_yaml(root, "runtime/k8s/ingress.yaml", lambda d: d["spec"]["rules"][0].update(host=""))
    if case == "S07":
        replace_once(tf, "node_count          = 2", "node_count          = 0")
    if case == "S08":
        update_yaml(root, "mlops/decision-gate.yaml", lambda d: d["decisionGate"]["criteria"][0].update(weight=0.25))
    if case == "S09":
        update_yaml(root, "mlops/decision-gate.yaml", lambda d: d["decisionGate"]["criteria"][0].update(minValue=0.95, maxValue=0.8))
    if case in {"C02", "C03"}:
        value = 1 if case == "C02" else 65535
        if (root / "kustomization.yaml").exists():
            update_yaml(root, "runtime/k8s/service.yaml", lambda d: d["spec"]["ports"][0].update(port=value))
        else:
            set_ports(root, value)


def load_dsl(path):
    data = json.loads(path.read_text(encoding="utf-8"))
    indexed = {r["case_id"]: r for r in data["cases"]}
    if len(indexed) != len(data["cases"]):
        raise ValueError("Duplicate DSL observation IDs")
    return indexed


def main():
    p = argparse.ArgumentParser(description=__doc__)
    p.add_argument("--out", type=Path, required=True)
    p.add_argument("--dsl-observations", type=Path)
    p.add_argument("--kubectl", default="kubectl")
    p.add_argument("--terraform", default=None)
    args = p.parse_args()
    if args.out.exists():
        p.error("Output exists; use a fresh run directory")
    dsl = load_dsl(args.dsl_observations) if args.dsl_observations else {}
    rows = load_matrix(ROOT / "evaluation/journal/fault-pairs.tsv")
    fixtures = {r["case_id"]: r["dsl_fixture"] for r in rows}
    for cid, observation in dsl.items():
        if cid not in fixtures or observation["source_sha256"] != digest(ROOT / fixtures[cid]):
            p.error("DSL observation fixture hash/ID mismatch: " + cid)
        if observation["outcome"] not in {"PASS", "FALSE_ALARM", "DETECTED", "MISSED", "BLOCKED"}:
            p.error("Invalid DSL outcome: " + cid)
    args.out.mkdir(parents=True)
    if args.dsl_observations:
        shutil.copy2(args.dsl_observations, args.out / "dsl-observations.json")
    source = args.out / "baseline-source-control"
    shutil.copytree(ROOT / "evaluation/journal/baseline/c00-azure", source)
    runtime = ROOT / "evaluation/comparison/baseline/c00-runtime"
    shutil.copytree(runtime / "runtime", source / "runtime")
    shutil.copy2(runtime / "kustomization.yaml", source / "kustomization.yaml")
    materialize(args.out / "baseline-control", args.kubectl, args.out / "logs", source=source, runtime=source)
    docs, errors = parse(args.out / "baseline-control")
    if errors or domain_checks(docs):
        p.error("Baseline control is not valid; preserve logs and fix before fault injection")
    native, observations = {}, {}
    for cid in fixtures:
        if cid in INAPPLICABLE:
            continue
        case_dir = args.out / "mutations" / cid
        maintained = case_dir / "source"
        destination = case_dir / "artifacts"
        shutil.copytree(source, maintained)
        mutate(maintained, cid)
        build_failed = False
        try:
            materialize(destination, args.kubectl, case_dir / "native-logs", source=maintained, runtime=maintained)
        except ValueError:
            build_failed = True
        if build_failed:
            log = (case_dir / "native-logs/kustomize.stderr.txt").read_text()
            # S04 deletes the authoritative replacement source, specifically.
            targeted = cid == "S04" and "spec.ports.0.port" in log and "missing" in log.lower()
            outcome = "DETECTED" if targeted else "BLOCKED"
            observation = {"case_id": cid, "outcome": outcome, "detection_stage": "kustomize_build",
                           "targeted_diagnostic": targeted, "source_inventory": inventory(maintained), "build_error": log}
            save(case_dir / "observation.json", observation)
            observations[cid] = observation
            native[cid] = {"stage": "kustomize_build", "outcome": outcome, "evidence": f"mutations/{cid}/native-logs/kustomize.stderr.txt"}
            continue
        docs, errors = parse(destination)
        issues = domain_checks(docs) if not errors else []
        fault = cid.startswith("S")
        target = any(i["rule"] == cid for i in issues)
        outcome = ("BLOCKED" if errors or (issues and not target) else "DETECTED" if target else "MISSED") if fault else ("FALSE_ALARM" if errors or issues else "PASS")
        observation = {"case_id": cid, "outcome": outcome, "detection_stage": "study_domain_guard" if issues else "none",
                       "parsing_errors": errors, "issues": issues, "inventory": inventory(destination), "source_inventory": inventory(maintained),
                       "fixture_sha256": digest(ROOT / fixtures[cid])}
        save(case_dir / "observation.json", observation)
        observations[cid] = observation
        if cid in {"S05", "S07"}:
            result = terraform(destination, args.terraform, case_dir / "native-logs/terraform") if args.terraform else {"status": "NOT_RUN"}
            native[cid] = {"stage": "terraform_validate", **result}
        else:
            native[cid] = {"stage": "kustomize_build_and_YAML_HCL_parser", "outcome": ("DETECTED" if errors else "MISSED") if fault else ("FALSE_ALARM" if errors else "PASS"),
                           "limitation": "No Kubernetes API schema validation or native lifecycle-policy validator executed"}
    for r in rows:
        cid = r["case_id"]
        r.update(outcome="NOT_RUN", detection_stage="", evidence_path="", n_a_reason="")
        if r["approach"] == "baseline":
            if cid in INAPPLICABLE:
                r.update(outcome="NOT_APPLICABLE", n_a_reason=INAPPLICABLE[cid])
            else:
                o = observations[cid]
                r.update(outcome=o["outcome"], detection_stage=o["detection_stage"], evidence_path=f"mutations/{cid}/observation.json")
        elif cid in dsl:
            o = dsl[cid]
            r.update(outcome=o["outcome"], detection_stage=o["detection_stage"], evidence_path="dsl-observations.json")
    with (args.out / "fault-pairs.tsv").open("w", newline="", encoding="utf-8") as f:
        writer = csv.DictWriter(f, fieldnames=list(rows[0]), delimiter="\t")
        writer.writeheader(); writer.writerows(rows)
    report = {"recorded_at_utc": stamp(), "runner_sha256": digest(Path(__file__)), "summary": summarize(rows),
              "native_validation": native, "baseline_guard_provenance": "Study-specific Python domain guard, not native validators",
              "dsl_observations": "IMPORTED_HASH_CHECKED" if dsl else "NOT_RUN",
              "paired_equivalence": "NOT_REVIEWED", "inapplicable": INAPPLICABLE}
    save(args.out / "report.json", report)
    print("Baseline Kustomize + study domain guard:", {k: v["outcome"] for k, v in observations.items()})
    print("Executed pairs:", report["summary"]["executed_paired_cases"], "; DSL:", report["dsl_observations"])
    return 1 if report["summary"]["errors"] else 0


if __name__ == "__main__":
    raise SystemExit(main())
