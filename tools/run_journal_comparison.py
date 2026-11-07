"""Materialize the broader C00 baseline and inspect both frozen artifact sets."""
import argparse
import importlib.metadata
import json
import platform
import shutil
from pathlib import Path

import yaml
from journal_common import ROOT, command, digest, domain_checks, inventory, parse, resources, root_defaults, save, stamp, terraform
from run_comparison import select


def materialize(destination, kubectl, logs, source=None, runtime=None):
    source = source or ROOT / "evaluation/journal/baseline/c00-azure"
    shutil.copytree(source, destination)
    runtime = runtime or ROOT / "evaluation/comparison/baseline/c00-runtime"
    record, output = command([kubectl, "kustomize", str(runtime)], logs, "kustomize")
    if record["status"] != "PASS":
        raise ValueError("Baseline Kustomize build blocked/failed; inspect logs")
    names = {"Namespace": "namespace", "Deployment": "deployment", "Service": "service", "Ingress": "ingress"}
    target = destination / "runtime/k8s"
    target.mkdir(parents=True, exist_ok=True)
    docs = list(yaml.safe_load_all(output))
    if len(docs) != 4 or {d["kind"] for d in docs} != set(names):
        raise ValueError("Unexpected Kustomize document set")
    for doc in docs:
        (target / (names[doc["kind"]] + ".yaml")).write_text(yaml.safe_dump(doc, sort_keys=False), encoding="utf-8")
    return record


def inspect(root, contract):
    docs, errors = parse(root)
    checks = []
    for c in contract["checks"]:
        if c["kind"] == "yaml":
            actual = select(docs.get(c["file"]), c["path"])
            ok = bool(actual) and actual == c["expected"]
        elif c["kind"] == "resource":
            actual = len(resources(docs, c["resource"]))
            ok = actual >= c["minimum"]
        elif c["kind"] == "region":
            actual = root_defaults(docs).get("region")
            ok = actual == c["expected"]
        elif c["kind"] == "network":
            actual = [v.get("address_space") for v in resources(docs, "azurerm_virtual_network")]
            ok = actual == [c["expected"]]
        elif c["kind"] == "subnet_literals":
            # A declared-literal check, not an HCL expression interpreter.
            text = "\n".join(p.read_text(encoding="utf-8") for p in (root / "infra/terraform").rglob("*.tf") if ".terraform" not in p.parts)
            actual = [x for x in c["expected"] if '"' + x + '"' in text]
            ok = actual == c["expected"]
        else:
            raise ValueError("Unknown contract kind: " + c["kind"])
        checks.append({**c, "actual": actual, "status": "PASS" if ok else "FAIL"})
    return {"parsing_errors": errors, "requirements": checks, "domain_oracle": domain_checks(docs), "inventory": inventory(root)}


def main():
    p = argparse.ArgumentParser(description=__doc__)
    p.add_argument("--generated", type=Path, required=True)
    p.add_argument("--out", type=Path, required=True)
    p.add_argument("--kubectl", default="kubectl")
    p.add_argument("--terraform", default=None, help="Optional Terraform executable; logs real init/validate")
    args = p.parse_args()
    if args.out.exists() or not args.generated.is_dir():
        p.error("Use a new output directory and an existing generated directory")
    args.out.mkdir(parents=True)
    report = {"recorded_at_utc": stamp(), "status": "BLOCKED", "python": platform.python_version(),
              "platform": platform.platform(), "dependencies": {x: importlib.metadata.version(x) for x in ("PyYAML", "python-hcl2")},
              "runner_sha256": digest(Path(__file__)), "scope_equivalence": "NOT_REVIEWED",
              "deployment": "NOT_RUN", "authoring_time": "NOT_MEASURED"}
    try:
        contract_path = ROOT / "evaluation/journal/c00-broader-contract.json"
        contract = json.loads(contract_path.read_text())
        report["contract_sha256"] = digest(contract_path)
        report["baseline_build"] = materialize(args.out / "baseline", args.kubectl, args.out / "logs")
        report["kubectl_version"], _ = command([args.kubectl, "version", "--client", "-o", "json"], args.out / "logs", "kubectl-version")
        shutil.copytree(args.generated, args.out / "generated", ignore=shutil.ignore_patterns(".terraform", "__pycache__", "*.tfstate*"))
        for label in ("baseline", "generated"):
            report[label] = inspect(args.out / label, contract)
            report[label]["terraform"] = terraform(args.out / label, args.terraform, args.out / "logs" / label) if args.terraform else {"status": "NOT_RUN"}
        report["status"] = "FAIL" if any(report[x]["parsing_errors"] or report[x]["domain_oracle"] or
            any(c["status"] != "PASS" for c in report[x]["requirements"]) or report[x]["terraform"]["status"] == "FAIL" for x in ("baseline", "generated")) else "PASS_SELECTED_REQUIREMENTS"
        report["native_validation_blocked"] = any(report[x]["terraform"]["status"] == "BLOCKED" for x in ("baseline", "generated"))
        if report["native_validation_blocked"] and report["status"] != "FAIL":
            report["status"] = "BLOCKED"
    except (ValueError, OSError, yaml.YAMLError) as exc:
        report["reason"] = str(exc)
    save(args.out / "report.json", report)
    for label in ("baseline", "generated"):
        if label in report:
            r = report[label]
            print(label, "failed requirements:", [c["id"] for c in r["requirements"] if c["status"] == "FAIL"], "domain issues:", r["domain_oracle"], "terraform:", r["terraform"]["status"])
    print("status:", report["status"], "report:", args.out / "report.json")
    return 0 if report["status"] == "PASS_SELECTED_REQUIREMENTS" else 1


if __name__ == "__main__":
    raise SystemExit(main())
