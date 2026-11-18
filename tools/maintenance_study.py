"""Exact edit-subset experiment. Structural maintenance evidence, no human data."""
import argparse
from collections import Counter, defaultdict
import difflib
import itertools
import json
from pathlib import Path
import re
import shutil
import statistics

from controlled_studies import (build_baseline, inspect, inside, new_dir, passes,
                                read, stamp, tree, unintended_changes, verify_export)
from study_materials import ROOT, save, sha

# Quoted values take precedence over comments and unquoted names/numbers.
TOKEN = re.compile(r'''"(?:\\.|[^"\\])*"|'(?:\\.|[^'\\])*'|//[^\n]*|\#[^\n]*|[A-Za-z0-9_][A-Za-z0-9_.:/-]*|[^\s]''')


def tokens(text):
    return [m for m in TOKEN.finditer(text) if not m.group().startswith(("//", "#"))]


def edit_units(before, after):
    """One changed scalar/name occurrence is one unit, independent of layout."""
    first, last = tokens(before), tokens(after)
    matcher = difflib.SequenceMatcher(a=[m.group() for m in first],
                                     b=[m.group() for m in last], autojunk=False)
    result = []
    for kind, i, j, k, l in matcher.get_opcodes():
        if kind == "equal":
            continue
        if kind != "replace" or j - i != l - k:
            raise ValueError("Only paired token replacements are supported. Review this workload explicitly.")
        for a, b in zip(first[i:j], last[k:l]):
            result.append({"start": a.start(), "end": a.end(), "old": a.group(), "new": b.group()})
    return result


def apply_units(text, units):
    for edit in sorted(units, key=lambda e: e["start"], reverse=True):
        if text[edit["start"]:edit["end"]] != edit["old"]:
            raise ValueError("Edit target no longer matches the frozen starting source")
        text = text[:edit["start"]] + edit["new"] + text[edit["end"]:]
    return text


def subsets(n):
    for size in range(n + 1):
        yield from itertools.combinations(range(n), size)


def units_in_sources(before, after, approach):
    names = ["model.mlops"] if approach == "dsl" else sorted(tree(before / "baseline"))
    root_a = before if approach == "dsl" else before / "baseline"
    root_b = after if approach == "dsl" else after / "baseline"
    if approach == "baseline" and set(names) != set(tree(root_b)):
        raise ValueError("Source files were added/removed. This experiment covers value substitutions only.")
    result = []
    for name in names:
        a, b = (root_a / name).read_text(encoding="utf-8-sig"), (root_b / name).read_text(encoding="utf-8-sig")
        edits = edit_units(a, b)
        full = apply_units(a, edits)
        if [m.group() for m in tokens(full)] != [m.group() for m in tokens(b)]:
            raise ValueError("Full edit set does not reproduce the target token stream")
        result.extend({"file": name, **edit} for edit in edits)
    return result


def source_for_subset(before, destination, approach, units, selected):
    new_dir(destination)
    if approach == "dsl":
        shutil.copy2(before / "model.mlops", destination / "model.mlops")
    else:
        shutil.copytree(before / "baseline", destination / "baseline")
    by_file = defaultdict(list)
    for index in selected:
        by_file[units[index]["file"]].append(units[index])
    for name, edits in by_file.items():
        path = destination / name if approach == "dsl" else destination / "baseline" / name
        path.write_bytes(apply_units(path.read_text(encoding="utf-8-sig"), edits).encode("utf-8"))


def prepare(args):
    if not 1 <= args.max_units <= 12:
        raise ValueError("Predeclare an enumeration bound between one and 12 edit units")
    manifest = read(args.materials / "manifest.json")
    evolution = [r for r in manifest["items"] if r["kind"] == "evolution"]
    if not evolution:
        raise ValueError("No evolution tasks in the supplied manifest")
    ids = [item["id"] for item in evolution]
    if len(set(ids)) != len(ids) or any(not re.fullmatch(r"[A-Za-z0-9_-]+", ident) for ident in ids):
        raise ValueError("Task identifiers must be unique safe filenames")
    # Preflight first: do not create a half-prepared experiment for stale exports.
    reviewed, pins = [], set()
    for item in evolution:
        folder = inside(args.materials, item["directory"])
        for phase in ("before", "after"):
            receipt = verify_export(folder / phase / "dsl-export", folder / phase / "model.mlops")
            if receipt["generator"] != "ACCEPT" or receipt["diagnostics"]:
                raise ValueError("The starting/full DSL control was not valid: " + item["id"])
            contract = read(folder / phase / "contract.json")
            if not passes(inspect(item["provider"], folder / phase / "dsl-export/artifacts", contract)):
                raise ValueError("The existing control fails its output contract: " + item["id"])
            pins.add(receipt["generator_class_sha256"])
        for approach in ("dsl", "baseline"):
            units = units_in_sources(folder / "before", folder / "after", approach)
            if not 1 <= len(units) <= args.max_units:
                raise ValueError(f"{item['id']}/{approach}: {len(units)} units, exceeds the declared enumeration scope")
            reviewed.append((item, approach, units))
    if len(pins) != 1:
        raise ValueError("Control exports used different loaded generator classes")
    new_dir(args.out)
    tasks, scenarios, jobs = [], [], ["id\tsource\tdestination"]
    for item in evolution:
        folder = inside(args.materials, item["directory"])
        target = args.out / "controls" / item["id"]
        for phase in ("before", "after"):
            dest = target / phase
            dest.mkdir(parents=True)
            shutil.copy2(folder / phase / "contract.json", dest / "contract.json")
            shutil.copytree(folder / phase / "dsl-export", dest / "dsl-export")
            shutil.copy2(folder / phase / "model.mlops", dest / "model.mlops")
            shutil.copytree(folder / phase / "baseline", dest / "baseline")
        tasks.append(item)
    for item, approach, units in reviewed:
        control = args.out / "controls" / item["id"] / "before"
        for selection in subsets(len(units)):
            suffix = "none" if not selection else "-".join(map(str, selection))
            ident = f"{item['id']}-{approach}-{suffix}"
            destination = args.out / "cases" / ident
            source_for_subset(control, destination, approach, units, selection)
            row = {"id": ident, "task_id": item["id"], "project_id": item["project_id"],
                   "provider": item["provider"], "family": item["family"], "variant": item["variant"],
                   "approach": approach, "unit_count": len(units), "selected": list(selection),
                   "files_edited": len({units[i]["file"] for i in selection}),
                   "phase": "zero_control" if not selection else "full_control" if len(selection) == len(units) else "partial",
                   "directory": destination.relative_to(args.out).as_posix()}
            scenarios.append(row)
            if approach == "dsl":
                jobs.append("\t".join((ident, str((destination / "model.mlops").resolve()), str((destination / "dsl-export").resolve()))))
    definitions = [{"task_id": item["id"], "approach": approach, "units": units} for item, approach, units in reviewed]
    save(args.out / "design.json", {"status": "PREPARED_NOT_EXECUTED", "created_at_utc": stamp(),
         "generator_class_sha256": next(iter(pins)), "tasks": tasks, "edit_sets": definitions,
         "scenarios": scenarios, "maximum_edit_units": args.max_units,
         "scope": "Exact finite edit-subset benchmark on selected configuration contracts. No human outcomes."})
    (args.out / "jobs.tsv").write_bytes(("\n".join(jobs) + "\n").encode("utf-8"))
    save(args.out / "lock.json", {"inputs": tree(args.out), "script_sha256": sha(Path(__file__)),
        "scorer_sha256": sha(ROOT / "tools/controlled_studies.py"),
        "provider_scorers": {name: sha(ROOT / "tools" / name) for name in
            ("run_journal_comparison.py", "run_gcp_comparison.py", "journal_common.py", "run_comparison.py")}})
    return {"status": "PREPARED_NOT_EXECUTED", "tasks": len(tasks), "scenarios": len(scenarios),
            "xtext_exports_required": len(jobs) - 1, "jobs": str((args.out / "jobs.tsv").resolve())}


def verify_lock(study):
    lock = read(study / "lock.json")
    if lock["script_sha256"] != sha(Path(__file__)) or lock["scorer_sha256"] != sha(ROOT / "tools/controlled_studies.py"):
        raise ValueError("Study/scoring code changed after preparation. Preserve this run and prepare a new one.")
    for name, digest in lock["provider_scorers"].items():
        if sha(ROOT / "tools" / name) != digest:
            raise ValueError("Provider scoring code changed after preparation")
    for name, digest in lock["inputs"].items():
        if sha(inside(study, name)) != digest:
            raise ValueError("Frozen source, control or contract changed: " + name)


def classify(provider, root, before, after, reference):
    observation = inspect(provider, root, after)
    unintended = unintended_changes(reference, root, before, after)
    if unintended:
        state = "ACCEPTED_UNINTENDED_CHANGE"
    elif passes(observation):
        state = "ACCEPTED_CORRECT_TARGET"
    else:
        state = "ACCEPTED_INCOMPLETE_TARGET"
    return {"state": state, "output_observation": observation, "unintended_changes": unintended}


def summarise(design, rows):
    groups = defaultdict(list)
    for row in rows:
        groups[(row["task_id"], row["approach"])].append(row)
    result = []
    for (task_id, approach), group in sorted(groups.items()):
        successful = [r for r in group if r["state"] == "ACCEPTED_CORRECT_TARGET"]
        units = group[0]["unit_count"]
        by_omitted = []
        for omitted in range(units + 1):
            subset = [r for r in group if units - len(r["selected"]) == omitted]
            by_omitted.append({"omitted_units": omitted, "enumerated_subsets": len(subset),
                               "outcomes": dict(Counter(r["state"] for r in subset))})
        result.append({"task_id": task_id, "approach": approach, "project_id": group[0]["project_id"],
            "family": group[0]["family"], "value_substitutions_in_full_edit_set": units,
            "minimum_passing_substitutions_within_enumerated_set": min((len(r["selected"]) for r in successful), default=None),
            "minimum_passing_files_within_enumerated_set": min((r["files_edited"] for r in successful), default=None),
            "full_control_pass": all(r["state"] == "ACCEPTED_CORRECT_TARGET" for r in group if r["phase"] == "full_control"),
            "zero_control_reaches_target": any(r["state"] == "ACCEPTED_CORRECT_TARGET" for r in group if r["phase"] == "zero_control"),
            "by_omitted_units": by_omitted})
    contrasts = []
    for task in design["tasks"]:
        pair = {r["approach"]: r for r in result if r["task_id"] == task["id"]}
        a, b = (pair[x]["minimum_passing_substitutions_within_enumerated_set"] for x in ("dsl", "baseline"))
        contrasts.append({"task_id": task["id"], "family": task["family"], "dsl": a, "baseline": b,
                          "baseline_minus_dsl": b - a if a is not None and b is not None else None})
    values = [r["baseline_minus_dsl"] for r in contrasts if r["baseline_minus_dsl"] is not None]
    integrity_ok = all(r["full_control_pass"] and not r["zero_control_reaches_target"] for r in result)
    return {"status": "COMPLETE_FINITE_BENCHMARK" if integrity_ok else "CONTROL_FAILURE_REQUIRES_INVESTIGATION",
            "per_task": result, "paired_contrasts": contrasts,
            "descriptive_contrasts": {"median": statistics.median(values) if values else None,
                "range": [min(values), max(values)] if values else None,
                "dsl_fewer": sum(v > 0 for v in values), "ties": sum(v == 0 for v in values),
                "baseline_fewer": sum(v < 0 for v in values)},
            "inference": "Exact enumeration conditional on selected edit sets. No p-values, population error rates or human productivity inference."}


def evaluate(args):
    verify_lock(args.study)
    design = read(args.study / "design.json")
    # Verify every actual DSL export before starting baseline runs.
    for row in design["scenarios"]:
        if row["approach"] == "dsl":
            here = inside(args.study, row["directory"])
            verify_export(here / "dsl-export", here / "model.mlops", design["generator_class_sha256"])
    new_dir(args.out)
    references = {}
    for task in design["tasks"]:
        control = args.study / "controls" / task["id"]
        for phase in ("before", "after"):
            source = control / phase
            dest = args.out / "baseline-controls" / task["id"] / phase
            if build_baseline(source / "baseline", dest, args.kubectl) != "PASS":
                raise ValueError("Baseline endpoint control failed: " + str(dest))
            if not passes(inspect(task["provider"], dest / "artifacts", read(source / "contract.json"))):
                raise ValueError("Baseline endpoint control violates its contract: " + str(dest))
            references[(task["id"], phase)] = dest / "artifacts"
    rows = []
    for row in design["scenarios"]:
        source = inside(args.study, row["directory"])
        control = args.study / "controls" / row["task_id"]
        before, after = (read(control / phase / "contract.json") for phase in ("before", "after"))
        record = dict(row)
        if row["approach"] == "dsl":
            receipt = verify_export(source / "dsl-export", source / "model.mlops", design["generator_class_sha256"])
            record["diagnostics"] = receipt["diagnostics"]
            if receipt["generator"] == "REJECT":
                record["state"] = "REJECTED_AT_SOURCE"
            elif receipt["diagnostics"]:
                record["state"] = "SOURCE_ERRORS_WITH_OUTPUT"
            else:
                record.update(classify(row["provider"], source / "dsl-export/artifacts", before, after,
                                       control / "before/dsl-export/artifacts"))
            record["export_receipt_sha256"] = sha(source / "dsl-export/receipt.json")
        else:
            dest = args.out / "baseline-cases" / row["id"]
            status = build_baseline(source / "baseline", dest, args.kubectl)
            if status != "PASS":
                record["state"] = "BLOCKED" if status == "BLOCKED" else "NATIVE_BUILD_REJECTED_UNATTRIBUTED"
            else:
                record.update(classify(row["provider"], dest / "artifacts", before, after,
                                       references[(row["task_id"], "before")]))
            record["baseline_receipt_sha256"] = sha(dest / "receipt.json")
        rows.append(record)
        save(args.out / "rows" / (row["id"] + ".json"), record)
    summary = summarise(design, rows)
    if any(r["state"] in {"BLOCKED", "NATIVE_BUILD_REJECTED_UNATTRIBUTED", "SOURCE_ERRORS_WITH_OUTPUT"} for r in rows):
        summary["status"] = "BLOCKED_OR_UNATTRIBUTED_RESULTS"
    save(args.out / "report.json", {**summary, "recorded_at_utc": stamp(), "design_sha256": sha(args.study / "design.json"),
                                    "lock_sha256": sha(args.study / "lock.json"), "rows": rows})
    for row in summary["paired_contrasts"]:
        print(f"{row['task_id']}: minimum selected value edits DSL={row['dsl']} baseline={row['baseline']}")
    return {"status": summary["status"], "scenarios": len(rows), "report": str(args.out / "report.json")}


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    sub = parser.add_subparsers(dest="action", required=True)
    p = sub.add_parser("prepare")
    p.add_argument("--materials", type=Path, required=True); p.add_argument("--out", type=Path, required=True)
    p.add_argument("--max-units", type=int, default=8)
    p = sub.add_parser("evaluate")
    p.add_argument("--study", type=Path, required=True); p.add_argument("--out", type=Path, required=True)
    p.add_argument("--kubectl", default="kubectl")
    args = parser.parse_args()
    try:
        result = prepare(args) if args.action == "prepare" else evaluate(args)
        print(json.dumps(result, indent=2))
        return 0 if args.action == "prepare" or result["status"] == "COMPLETE_FINITE_BENCHMARK" else 1
    except (ValueError, OSError, KeyError) as exc:
        parser.error(str(exc))


if __name__ == "__main__":
    raise SystemExit(main())
