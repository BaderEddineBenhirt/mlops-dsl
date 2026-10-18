"""Prepare and evaluate one controlled C00 port change; no authoring-time claims."""
import argparse
import difflib
import hashlib
import json
import re
import shutil
import subprocess
import sys
from datetime import datetime, timezone
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]


def save_json(path, value):
    path.write_text(json.dumps(value, indent=2) + "\n", encoding="utf-8")


def change_once(text, pattern, replacement):
    result, count = re.subn(pattern, replacement, text, flags=re.MULTILINE)
    if count != 1:
        raise ValueError(f"Expected exactly one source edit, found {count} matches.")
    return result


def save_diff(path, before, after, label):
    lines = list(difflib.unified_diff(before.splitlines(True), after.splitlines(True),
                                   fromfile=label + ":before", tofile=label + ":after"))
    path.write_text("".join(lines), encoding="utf-8")
    return sum(line.startswith("@@") for line in lines)


def prepare(args):
    if args.run.exists():
        raise ValueError("Run directory already exists; choose a new directory.")
    original = args.dsl.resolve().read_text(encoding="utf-8-sig")
    expected = change_once(original, r"^([ \t]*port[ \t]*=[ \t]*)8080([ \t]*)$", r"\g<1>9090\2")
    baseline = ROOT / "evaluation/comparison/baseline/c00-runtime"
    service_text = (baseline / "runtime/k8s/service.yaml").read_text(encoding="utf-8")
    service_after = change_once(service_text, r"^([ \t]*port:[ \t]*)8080([ \t]*)$", r"\g<1>9090\2")
    contract = json.loads((ROOT / "evaluation/comparison/c00-contract.json").read_text())
    port_ids = [check["id"] for check in contract["checks"] if check["expected"] == 8080]
    evolved = json.loads(json.dumps(contract))
    for check in evolved["checks"]:
        if check["id"] in port_ids:
            check["expected"] = 9090
    args.run.mkdir(parents=True)
    for name, text in [("dsl-before.mlops", original), ("dsl-expected-after.mlops", expected)]:
        (args.run / name).write_text(text, encoding="utf-8")
    shutil.copytree(baseline, args.run / "baseline-before")
    shutil.copytree(baseline, args.run / "baseline-after")
    (args.run / "baseline-after/runtime/k8s/service.yaml").write_text(service_after, encoding="utf-8")
    save_json(args.run / "contract-before.json", contract)
    save_json(args.run / "contract-after.json", evolved)
    dsl_hunks = save_diff(args.run / "planned-dsl-change.diff", original, expected, "ServingSpec.port")
    baseline_hunks = save_diff(args.run / "baseline-change.diff", service_text, service_after, "Service.spec.ports[0].port")
    save_json(args.run / "task.json", {
        "prepared_at_utc": datetime.now(timezone.utc).isoformat(),
        "task": "C00 API port 8080 to 9090", "dsl_source": str(args.dsl.resolve()),
        "scope": "Selected runtime port propagation; not a participant productivity experiment",
        "authoring_time": "not_measured", "baseline_edit": "Applied mechanically by preparation script",
        "port_check_ids": port_ids,
        "planned_dsl_source_edit_hunks": dsl_hunks,
        "baseline_source_edit_hunks": baseline_hunks,
    })
    print(f"Prepared: {args.run}")
    print(f"In runtime Eclipse open: {args.dsl.resolve()}")
    print("Change only ServingSpec.port from 8080 to 9090, then save to regenerate.")
    print("Next run evaluate with this same --run and the fresh --generated directory.")


def evaluate(args):
    if not (args.run / "task.json").is_file():
        raise ValueError("Prepare the task first.")
    if (args.run / "summary.json").exists():
        raise ValueError("Summary already exists; preserve this run and prepare a new one.")
    task = json.loads((args.run / "task.json").read_text())
    actual = Path(task["dsl_source"]).read_text(encoding="utf-8-sig")
    expected = (args.run / "dsl-expected-after.mlops").read_text(encoding="utf-8")
    if actual.strip() != expected.strip():
        raise ValueError("DSL source does not match the prepared one-field port change. Check its port and other edits.")
    generated_snapshot = args.run / "generated-after"
    if generated_snapshot.exists():
        raise ValueError("Generated snapshot already exists; preserve this observation and prepare a fresh run.")
    shutil.copytree(args.generated.resolve(), generated_snapshot,
                    ignore=shutil.ignore_patterns(".terraform", "__pycache__"))
    (args.run / "dsl-after.mlops").write_text(actual, encoding="utf-8")
    original = (args.run / "dsl-before.mlops").read_text(encoding="utf-8")
    hunks = save_diff(args.run / "actual-dsl-change.diff", original, actual, "ServingSpec.port")
    summary = {
        "recorded_at_utc": datetime.now(timezone.utc).isoformat(), "task": task["task"],
        "scope": task["scope"], "authoring_time": "not_measured",
        "runner_sha256": hashlib.sha256(Path(__file__).read_bytes()).hexdigest(),
        "status": "blocked", "source_changes": {
            "dsl": {"source_files_changed": 1, "diff_hunks": hunks},
            "baseline": {"source_files_changed": 1, "diff_hunks": task["baseline_source_edit_hunks"],
                         "edit_provenance": task["baseline_edit"]}},
        "phases": {},
    }
    save_json(args.run / "summary.json", summary) # Reserve a readable observation before subprocesses.
    phases = [("before", ROOT / "evaluation/comparison/generated/c00-after-tls", "dsl-before.mlops"),
              ("after", generated_snapshot.resolve(), "dsl-after.mlops")]
    for phase, generated, dsl_name in phases:
        destination = args.run / phase
        if destination.exists():
            summary["reason"] = f"Existing phase directory: {destination}; prepare a fresh run."
            break
        command = [sys.executable, str(ROOT / "tools/run_runtime_comparison.py"),
                   "--generated", str(generated), "--baseline-source", str((args.run / ("baseline-" + phase)).resolve()),
                   "--contract", str((args.run / ("contract-" + phase + ".json")).resolve()),
                   "--dsl", str((args.run / dsl_name).resolve()),
                   "--kubectl", args.kubectl, "--out", str(destination)]
        try:
            result = subprocess.run(command, capture_output=True, text=True, encoding="utf-8", timeout=150)
        except subprocess.TimeoutExpired:
            summary["reason"] = f"{phase} comparison timed out; inspect available phase logs."
            break
        (args.run / (phase + ".stdout.txt")).write_text(result.stdout, encoding="utf-8")
        (args.run / (phase + ".stderr.txt")).write_text(result.stderr, encoding="utf-8")
        summary["phases"][phase] = {"exit_code": result.returncode, "argv": command}
        if result.returncode != 0:
            summary["status"] = "fail" if result.returncode == 1 else "blocked"
            summary["reason"] = f"{phase} checks did not pass; preserve and inspect the phase report."
            break
    else:
        before = json.loads((args.run / "before/report.json").read_text())
        after = json.loads((args.run / "after/report.json").read_text())
        ids = set(task["port_check_ids"])
        summary["observed_port_changes"] = {}
        for approach in ("generated", "baseline"):
            old = {c["id"]: c for c in before[approach]["requirement_checks"]}
            new = {c["id"]: c for c in after[approach]["requirement_checks"]}
            changed = [key for key in sorted(ids) if old[key]["actual"] == [8080] and new[key]["actual"] == [9090]]
            summary["observed_port_changes"][approach] = changed
        summary["status"] = "pass_selected_evolution" if all(
            len(changes) == len(ids) for changes in summary["observed_port_changes"].values()) else "fail"
    save_json(args.run / "summary.json", summary)
    print(f"status: {summary['status']}")
    if "observed_port_changes" in summary:
        for approach, changes in summary["observed_port_changes"].items():
            print(f"{approach}: {len(changes)}/{len(task['port_check_ids'])} required port values changed")
        print("Source files changed: DSL=1; baseline=1. Time savings were not measured.")
    if "reason" in summary:
        print(summary["reason"])
    print(f"Saved: {args.run / 'summary.json'}")
    return 0 if summary["status"] == "pass_selected_evolution" else 1


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    sub = parser.add_subparsers(dest="action", required=True)
    prep = sub.add_parser("prepare")
    prep.add_argument("--dsl", type=Path, required=True)
    prep.add_argument("--run", type=Path, required=True)
    evaluation = sub.add_parser("evaluate")
    evaluation.add_argument("--run", type=Path, required=True)
    evaluation.add_argument("--generated", type=Path, required=True)
    evaluation.add_argument("--kubectl", default="kubectl")
    args = parser.parse_args()
    try:
        return prepare(args) if args.action == "prepare" else evaluate(args)
    except (OSError, ValueError) as exc:
        parser.error(str(exc))


if __name__ == "__main__":
    raise SystemExit(main())
