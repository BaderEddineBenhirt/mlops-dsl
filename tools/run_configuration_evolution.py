"""Freeze real before/after models and artifacts for registry/exposure evolution."""
import argparse
import difflib
import json
import shutil
from pathlib import Path

import yaml
from journal_common import ROOT, files, inventory, save, stamp
from run_fault_comparison import replace_once, update_yaml
from run_journal_comparison import inspect, materialize


def changes(before, after, diff_dir):
    diff_dir.mkdir(parents=True)
    old = {p.relative_to(before).as_posix(): p for p in files(before)}
    new = {p.relative_to(after).as_posix(): p for p in files(after)}
    result = []
    for name in sorted(set(old) | set(new)):
        a = old[name].read_text(encoding="utf-8-sig") if name in old else ""
        b = new[name].read_text(encoding="utf-8-sig") if name in new else ""
        if a == b:
            continue
        diff = list(difflib.unified_diff(a.splitlines(True), b.splitlines(True), fromfile="before/" + name, tofile="after/" + name))
        target = diff_dir / (name.replace("/", "__") + ".diff")
        target.write_text("".join(diff), encoding="utf-8")
        result.append({"path": name, "hunks": sum(s.startswith("@@") for s in diff),
                       "added_lines": sum(s.startswith("+") and not s.startswith("+++") for s in diff),
                       "removed_lines": sum(s.startswith("-") and not s.startswith("---") for s in diff)})
    return {"files_changed": len(result), "files": result}


def contract_for(task, phase):
    c = json.loads((ROOT / "evaluation/journal/c00-broader-contract.json").read_text())
    if task == "registry" and phase == "after":
        c = json.loads(json.dumps(c).replace("vision_defects_model_v1", "vision_defects_model_v2"))
    if task == "exposure" and phase == "before":
        c["checks"] = [x for x in c["checks"] if x["id"] != "explicit_tls_host"]
    return c


def failed(result):
    return result["parsing_errors"] or result["domain_oracle"] or [c["id"] for c in result["requirements"] if c["status"] != "PASS"]


def prepare(args):
    if args.run.exists():
        raise ValueError("Use a new run directory")
    original = args.dsl.read_text(encoding="utf-8-sig")
    if args.task == "registry":
        if original.count("vision_defects_model_v1") != 6:
            raise ValueError("Expected C00's six explicit model-name references (including environment and workflow artifacts)")
        expected = original.replace("vision_defects_model_v1", "vision_defects_model_v2")
    else:
        if original.count("exposure = PRIVATE") != 1 or original.count("tls = false") != 1:
            raise ValueError("Exposure task starts with C00 PRIVATE, tls=false; save and generate that starting model first")
        expected = original.replace("exposure = PRIVATE", "exposure = PUBLIC").replace("tls = false", "tls = true")
    args.run.mkdir(parents=True)
    for phase, text in (("before", original), ("expected-after", expected)):
        folder = args.run / ("dsl-" + phase)
        folder.mkdir()
        (folder / "c00.mlops").write_text(text, encoding="utf-8")
    shutil.copytree(args.generated, args.run / "generated-before", ignore=shutil.ignore_patterns(".terraform", "__pycache__", "*.tfstate*"))
    source_before, source_after = args.run / "baseline-source-before", args.run / "baseline-source-after"
    shutil.copytree(ROOT / "evaluation/journal/baseline/c00-azure", source_before)
    runtime = ROOT / "evaluation/comparison/baseline/c00-runtime"
    shutil.copytree(runtime / "runtime", source_before / "runtime")
    shutil.copy2(runtime / "kustomization.yaml", source_before / "kustomization.yaml")
    if args.task == "exposure":
        replace_once(source_before / "infra/terraform/main.tf", 'variable "public_exposure" {\n  type    = bool\n  default = true', 'variable "public_exposure" {\n  type    = bool\n  default = false')
        update_yaml(source_before, "runtime/k8s/ingress.yaml", lambda d: d["spec"].pop("tls"))
        # Private starting model has no TLS target for the host replacement.
        update_yaml(source_before, "kustomization.yaml", lambda d: d["replacements"].pop())
    shutil.copytree(source_before, source_after)
    if args.task == "registry":
        for file in ("mlops/model-registry.yaml", "mlops/cicdct.yaml"):
            path = source_after / file
            path.write_text(path.read_text().replace("vision_defects_model_v1", "vision_defects_model_v2"), encoding="utf-8")
    else:
        for file in ("infra/terraform/main.tf", "runtime/k8s/ingress.yaml", "kustomization.yaml"):
            original_path = (ROOT / "evaluation/journal/baseline/c00-azure" / file) if file.endswith(".tf") else runtime / file
            shutil.copy2(original_path, source_after / file)
    for phase, source in (("before", source_before), ("after", source_after)):
        materialize(args.run / ("baseline-" + phase), args.kubectl, args.run / ("logs-" + phase), source=source, runtime=source)
        save(args.run / ("contract-" + phase + ".json"), contract_for(args.task, phase))
    observed = {label: inspect(args.run / (label + "-before"), contract_for(args.task, "before")) for label in ("generated", "baseline")}
    save(args.run / "before-observations.json", observed)
    valid = not any(failed(r) for r in observed.values())
    save(args.run / "task.json", {"task": args.task, "prepared_at_utc": stamp(), "dsl_source": str(args.dsl.resolve()),
         "starting_controls_valid": valid, "baseline_edit": "mechanical, no timing measurement", "scope_equivalence": "NOT_REVIEWED"})
    print("Prepared:", args.run, "; starting controls valid:", valid)
    print("Apply only the source changes in dsl-expected-after/c00.mlops to your runtime Eclipse model, then save/regenerate.")
    if not valid:
        raise ValueError("Starting control failed. Inspect before-observations.json before preparing a new trial")


def evaluate(args):
    if (args.run / "summary.json").exists():
        raise ValueError("Already evaluated; preserve the run")
    task = json.loads((args.run / "task.json").read_text())
    if not task["starting_controls_valid"]:
        raise ValueError("Cannot evaluate from invalid starting controls")
    actual = Path(task["dsl_source"]).read_text(encoding="utf-8-sig")
    expected = (args.run / "dsl-expected-after/c00.mlops").read_text()
    if actual.strip() != expected.strip():
        raise ValueError("Live source differs from the prepared change")
    (args.run / "dsl-after").mkdir()
    (args.run / "dsl-after/c00.mlops").write_text(actual, encoding="utf-8")
    shutil.copytree(args.generated, args.run / "generated-after", ignore=shutil.ignore_patterns(".terraform", "__pycache__", "*.tfstate*"))
    results = {label: inspect(args.run / (label + "-after"), contract_for(task["task"], "after")) for label in ("generated", "baseline")}
    report = {"recorded_at_utc": stamp(), "task": task["task"], "authoring_time": "NOT_MEASURED", "after": results,
              "source_changes": {"dsl": changes(args.run / "dsl-before", args.run / "dsl-after", args.run / "diffs-dsl"),
                                 "baseline": changes(args.run / "baseline-source-before", args.run / "baseline-source-after", args.run / "diffs-baseline")},
              "generated_changes": changes(args.run / "generated-before", args.run / "generated-after", args.run / "diffs-generated"),
              "remaining_inconsistencies": {label: failed(result) for label, result in results.items()},
              "scope_equivalence": "NOT_REVIEWED", "baseline_edit": task["baseline_edit"]}
    report["status"] = "FAIL" if any(report["remaining_inconsistencies"].values()) else "PASS_SELECTED_EVOLUTION"
    save(args.run / "summary.json", report)
    print("status:", report["status"], "; source files:", {k: v["files_changed"] for k, v in report["source_changes"].items()})
    return 0 if report["status"] == "PASS_SELECTED_EVOLUTION" else 1


def main():
    p = argparse.ArgumentParser(description=__doc__)
    sub = p.add_subparsers(dest="action", required=True)
    for action in ("prepare", "evaluate"):
        s = sub.add_parser(action)
        s.add_argument("--run", type=Path, required=True)
        s.add_argument("--generated", type=Path, required=True)
        if action == "prepare":
            s.add_argument("--dsl", type=Path, required=True)
            s.add_argument("--task", choices=["registry", "exposure"], required=True)
            s.add_argument("--kubectl", default="kubectl")
    args = p.parse_args()
    try:
        return prepare(args) if args.action == "prepare" else evaluate(args)
    except (ValueError, OSError, yaml.YAMLError) as exc:
        p.error(str(exc))


if __name__ == "__main__":
    raise SystemExit(main())
