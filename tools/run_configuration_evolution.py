"""Freeze real before/after models and artifacts for port/registry/exposure evolution."""
import argparse
import difflib
import json
import re
import shutil
from pathlib import Path
from types import SimpleNamespace

import yaml
from journal_common import ROOT, digest, files, inventory, save, stamp
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
    if task == "port" and phase == "after":
        ports = [check for check in c["checks"] if check.get("file", "").startswith("runtime/k8s/")
                 and check.get("expected") == [8080]]
        if len(ports) != 6:
            raise ValueError("Port task requires exactly six original 8080 postconditions")
        for check in ports:
            check["expected"] = [9090]
    return c


def change_port(source):
    changed, count = re.subn(r"^([ \t]*port[ \t]*=[ \t]*)8080([ \t]*)$",
                             r"\g<1>9090\2", source, flags=re.MULTILINE)
    if count != 1:
        raise ValueError("Port task requires exactly one ServingSpec.port = 8080 declaration")
    return changed


def failed(result):
    # Keep every category: a domain error must not hide stale registry/network
    # requirements, and a parsing error must not hide other observations.
    return ([{"stage": "parsing", **error} for error in result["parsing_errors"]]
            + [{"stage": "domain", **issue} for issue in result["domain_oracle"]]
            + [{"stage": "requirement", "id": c["id"],
                "expected": c.get("expected"), "actual": c.get("actual")}
               for c in result["requirements"] if c["status"] != "PASS"])


def prepare(args):
    if args.run.exists():
        raise ValueError("Use a new run directory")
    original = args.dsl.read_text(encoding="utf-8-sig")
    if args.task == "registry":
        if original.count("vision_defects_model_v1") != 6:
            raise ValueError("Expected C00's six explicit model-name references (including environment and workflow artifacts)")
        expected = original.replace("vision_defects_model_v1", "vision_defects_model_v2")
    elif args.task == "exposure":
        if original.count("exposure = PRIVATE") != 1 or original.count("tls = false") != 1:
            raise ValueError("Exposure task starts with C00 PRIVATE, tls=false; save and generate that starting model first")
        expected = original.replace("exposure = PRIVATE", "exposure = PUBLIC").replace("tls = false", "tls = true")
    else:
        expected = change_port(original)
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
    elif args.task == "exposure":
        for file in ("infra/terraform/main.tf", "runtime/k8s/ingress.yaml", "kustomization.yaml"):
            original_path = (ROOT / "evaluation/journal/baseline/c00-azure" / file) if file.endswith(".tf") else runtime / file
            shutil.copy2(original_path, source_after / file)
    else:
        # Preserve YAML formatting/comments so edit metrics reflect one value
        # change. Kustomize propagates the authoritative Service.port.
        replace_once(source_after / "runtime/k8s/service.yaml", "port: 8080", "port: 9090")
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
              "retry_of": task.get("retry_of"), "previous_summary_sha256": task.get("previous_summary_sha256"),
              "source_changes": {"dsl": changes(args.run / "dsl-before", args.run / "dsl-after", args.run / "diffs-dsl"),
                                 "baseline": changes(args.run / "baseline-source-before", args.run / "baseline-source-after", args.run / "diffs-baseline")},
              "generated_changes": changes(args.run / "generated-before", args.run / "generated-after", args.run / "diffs-generated"),
              "remaining_inconsistencies": {label: failed(result) for label, result in results.items()},
              "scope_equivalence": "NOT_REVIEWED", "baseline_edit": task["baseline_edit"]}
    report["status"] = "FAIL" if any(report["remaining_inconsistencies"].values()) else "PASS_SELECTED_EVOLUTION"
    save(args.run / "summary.json", report)
    print("status:", report["status"], "; source files:", {k: v["files_changed"] for k, v in report["source_changes"].items()})
    return 0 if report["status"] == "PASS_SELECTED_EVOLUTION" else 1


def retry(args):
    """New attempt of the same frozen task; the original result stays intact."""
    if args.out.exists():
        raise ValueError("Retry destination exists; choose a fresh directory")
    previous = json.loads((args.run / "summary.json").read_text())
    if previous.get("status") == "PASS_SELECTED_EVOLUTION":
        raise ValueError("This task already passed; a retry is for a failed attempt")
    task = json.loads((args.run / "task.json").read_text())
    if not task.get("starting_controls_valid"):
        raise ValueError("A retry requires valid frozen starting controls")
    expected = (args.run / "dsl-expected-after/c00.mlops").read_text(encoding="utf-8")
    actual = Path(task["dsl_source"]).read_text(encoding="utf-8-sig")
    if actual.strip() != expected.strip():
        raise ValueError("Live source no longer matches the recorded evolution")
    prepared = ["baseline-after", "baseline-before", "baseline-source-after", "baseline-source-before",
                "before-observations.json", "contract-after.json", "contract-before.json",
                "dsl-before", "dsl-expected-after", "generated-before", "logs-after", "logs-before", "task.json"]
    for name in prepared:
        if not (args.run / name).exists():
            raise ValueError("Incomplete frozen preparation: " + name)
    args.out.mkdir(parents=True)
    for name in prepared:
        source, target = args.run / name, args.out / name
        if source.is_dir():
            shutil.copytree(source, target)
        else:
            shutil.copy2(source, target)
    task["retry_of"] = str(args.run.resolve())
    task["previous_summary_sha256"] = digest(args.run / "summary.json")
    task["attempt_recorded_at_utc"] = stamp()
    task["retry_semantics"] = "Another attempt of the same frozen task; not an independent case or participant trial"
    save(args.out / "task.json", task)
    result = evaluate(SimpleNamespace(run=args.out, generated=args.generated))
    print("Original result preserved:", args.run / "summary.json")
    return result


def main():
    p = argparse.ArgumentParser(description=__doc__)
    sub = p.add_subparsers(dest="action", required=True)
    for action in ("prepare", "evaluate", "retry"):
        s = sub.add_parser(action)
        s.add_argument("--run", type=Path, required=True)
        s.add_argument("--generated", type=Path, required=True)
        if action == "retry":
            s.add_argument("--out", type=Path, required=True)
        if action == "prepare":
            s.add_argument("--dsl", type=Path, required=True)
            s.add_argument("--task", choices=["port", "registry", "exposure"], required=True)
            s.add_argument("--kubectl", default="kubectl")
    args = p.parse_args()
    try:
        if args.action == "retry":
            return retry(args)
        return prepare(args) if args.action == "prepare" else evaluate(args)
    except (ValueError, OSError, yaml.YAMLError) as exc:
        p.error(str(exc))


if __name__ == "__main__":
    raise SystemExit(main())
