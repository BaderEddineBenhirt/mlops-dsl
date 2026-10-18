"""Compare selected C00 runtime requirements against a direct Kustomize baseline."""
import argparse
import hashlib
import json
import platform
import shutil
import subprocess
from datetime import datetime, timezone
from pathlib import Path

import yaml
from run_comparison import inspect_artifacts, inventory

ROOT = Path(__file__).resolve().parents[1]
FILENAMES = {"Namespace": "namespace.yaml", "Deployment": "deployment.yaml",
             "Service": "service.yaml", "Ingress": "ingress.yaml"}


def execute(command, directory, label):
    try:
        result = subprocess.run(command, capture_output=True, text=True,
                                encoding="utf-8", timeout=60)
        stdout, stderr, code = result.stdout, result.stderr, result.returncode
    except subprocess.TimeoutExpired as exc:
        stdout = exc.stdout.decode("utf-8", errors="replace") if isinstance(exc.stdout, bytes) else exc.stdout or ""
        stderr, code = "Timed out after 60 seconds", None
    (directory / f"{label}.stdout.txt").write_text(stdout, encoding="utf-8")
    (directory / f"{label}.stderr.txt").write_text(stderr, encoding="utf-8")
    return {"argv": command, "exit_code": code}, stdout


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--generated", type=Path,
                        default=ROOT / "evaluation/comparison/generated/c00-after-tls")
    parser.add_argument("--baseline-source", type=Path,
                        default=ROOT / "evaluation/comparison/baseline/c00-runtime")
    parser.add_argument("--contract", type=Path,
                        default=ROOT / "evaluation/comparison/c00-contract.json")
    parser.add_argument("--dsl", type=Path,
                        default=ROOT / "mlops-tests/src/validation/c00_valid_azure.mlops")
    parser.add_argument("--kubectl", default="kubectl")
    parser.add_argument("--out", type=Path, required=True,
                        help="New directory for this run; existing observations are never overwritten")
    args = parser.parse_args()
    if args.out.exists():
        parser.error("Run directory already exists. Choose a new directory.")
    if not args.baseline_source.is_dir() or not args.generated.is_dir():
        parser.error("Baseline source or generated artifact directory is missing.")
    contract = json.loads(args.contract.read_text(encoding="utf-8"))
    args.out.mkdir(parents=True)
    report = {
        "recorded_at_utc": datetime.now(timezone.utc).isoformat(),
        "python": platform.python_version(), "pyyaml": yaml.__version__,
        "platform": platform.platform(), "scope": "Selected C00 runtime manifest requirements only",
        "baseline_provenance": "Assistant-assisted directly authored Kustomize configuration; not a human authoring trial",
        "authoring_time": "not_measured", "full_functional_equivalence": "not_reviewed",
        "terraform": "not_run", "deployment": "not_run",
        "contract_sha256": hashlib.sha256(args.contract.read_bytes()).hexdigest(),
        "runner_sha256": hashlib.sha256(Path(__file__).read_bytes()).hexdigest(),
        "dsl_inventory": inventory([args.dsl.resolve()], args.dsl.resolve().parent),
        "baseline_source_inventory": inventory(list(args.baseline_source.rglob("*.yaml")), args.baseline_source),
        "status": "blocked", "commands": [],
    }
    exit_code = 2
    try:
        executable = shutil.which(args.kubectl)
        if not executable:
            raise ValueError("kubectl not found. Install kubectl, reopen PowerShell, and use a new run directory.")
        version, _ = execute([executable, "version", "--client", "-o", "json"], args.out, "kubectl-version")
        report["commands"].append(version)
        if version["exit_code"] != 0:
            raise ValueError("kubectl client version command failed; inspect its logs.")
        build, text = execute([executable, "kustomize", str(args.baseline_source.resolve())], args.out, "kustomize-build")
        report["commands"].append(build)
        if build["exit_code"] != 0:
            raise ValueError("Kustomize build failed; inspect its logs. This is not a completed comparison.")
        docs = [d for d in yaml.safe_load_all(text) if d is not None]
        kinds = [d.get("kind") for d in docs]
        if len(docs) != 4 or set(kinds) != set(FILENAMES):
            raise ValueError(f"Expected exactly one Namespace/Deployment/Service/Ingress; received {kinds}")
        baseline = args.out / "baseline-rendered"
        runtime = baseline / "runtime/k8s"
        runtime.mkdir(parents=True)
        for doc in docs:
            (runtime / FILENAMES[doc["kind"]]).write_text(yaml.safe_dump(doc, sort_keys=False), encoding="utf-8")
        for label, directory in [("generated", args.generated), ("baseline", baseline)]:
            report[label] = inspect_artifacts(directory, contract, False, args.out / "unused-terraform-logs")
            report[label]["runtime_inventory"] = inventory(
                list((directory / "runtime/k8s").glob("*.yaml")), directory)
        failures = any(c["status"] != "pass" for label in ("generated", "baseline")
                       for group in ("syntax", "requirement_checks") for c in report[label][group])
        report["status"] = "fail" if failures else "pass_selected_requirements"
        exit_code = 1 if failures else 0
    except (ValueError, OSError, yaml.YAMLError) as exc:
        report["reason"] = str(exc)
    finally:
        (args.out / "report.json").write_text(json.dumps(report, indent=2) + "\n", encoding="utf-8")
    for label in ("generated", "baseline"):
        if label in report:
            checks = report[label]["requirement_checks"]
            print(f"{label}: {sum(c['status'] == 'pass' for c in checks)}/{len(checks)} selected requirements passed")
    print(f"status: {report['status']}")
    if "reason" in report:
        print(report["reason"])
    print(f"Saved observations and logs: {args.out}")
    return exit_code


if __name__ == "__main__":
    raise SystemExit(main())
