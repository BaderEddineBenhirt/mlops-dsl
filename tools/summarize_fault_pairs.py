#!/usr/bin/env python3
"""Audit paired fault observations. No execution or synthetic results."""
import argparse
import csv
import json
from collections import defaultdict
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
COMMON = {"NOT_RUN", "BLOCKED", "NOT_APPLICABLE"}
FAULTS = {"DETECTED", "MISSED"}
CONTROLS = {"PASS", "FALSE_ALARM"}
REQUIRED = {"case_id", "case_kind", "rule_category", "approach",
            "outcome", "detection_stage", "evidence_path", "n_a_reason"}


def summarize(rows):
    errors = []
    by_case = defaultdict(dict)
    for number, r in enumerate(rows, start=2):
        cid, approach = r["case_id"], r["approach"]
        kind, outcome = r["case_kind"], r["outcome"]
        if not cid or approach not in {"dsl", "baseline"}:
            errors.append(f"row {number}: missing case_id or unknown approach")
            continue
        if approach in by_case[cid]:
            errors.append(f"row {number}: duplicate {cid}/{approach}")
        by_case[cid][approach] = r
        allowed = COMMON | (FAULTS if kind == "fault" else CONTROLS if kind == "control" else set())
        if outcome not in allowed:
            errors.append(f"row {number}: invalid {kind} outcome {outcome}")
        if outcome in FAULTS | CONTROLS:
            if not r["evidence_path"]:
                errors.append(f"row {number}: executed outcome missing evidence_path")
            if outcome in FAULTS and not r["detection_stage"] and outcome == "DETECTED":
                errors.append(f"row {number}: detected fault missing detection_stage")
        if outcome == "NOT_APPLICABLE" and not r["n_a_reason"]:
            errors.append(f"row {number}: NOT_APPLICABLE requires a reason")
    for cid, pair in by_case.items():
        if set(pair) != {"dsl", "baseline"}:
            errors.append(f"{cid}: must contain both DSL and baseline observations")
        if len({r["case_kind"] for r in pair.values()}) > 1:
            errors.append(f"{cid}: mismatched case kinds")
        if len({r["rule_category"] for r in pair.values()}) > 1:
            errors.append(f"{cid}: mismatched rule categories")
    totals = {}
    for approach in ("dsl", "baseline"):
        own = [p[approach] for p in by_case.values() if approach in p]
        semantic = [r for r in own if r["case_kind"] == "fault" and r["rule_category"] == "semantic"]
        linking = [r for r in own if r["case_kind"] == "fault" and r["rule_category"] == "linking"]
        controls = [r for r in own if r["case_kind"] == "control"]
        counts = lambda group, statuses: {s: sum(r["outcome"] == s for r in group) for s in statuses}
        s = counts(semantic, sorted(FAULTS | COMMON))
        l = counts(linking, sorted(FAULTS | COMMON))
        c = counts(controls, sorted(CONTROLS | COMMON))
        semantic_denominator = s["DETECTED"] + s["MISSED"]
        false_alarm_denominator = c["PASS"] + c["FALSE_ALARM"]
        totals[approach] = {
            "semantic_faults": s, "linking_faults": l, "valid_controls": c,
            "semantic_recall": s["DETECTED"] / semantic_denominator if semantic_denominator else None,
            "semantic_recall_denominator": semantic_denominator,
            "false_alarm_rate": c["FALSE_ALARM"] / false_alarm_denominator if false_alarm_denominator else None,
            "false_alarm_denominator": false_alarm_denominator,
        }
    comparable = sum(
        pair.get("dsl", {}).get("outcome") in FAULTS | CONTROLS
        and pair.get("baseline", {}).get("outcome") in FAULTS | CONTROLS
        for pair in by_case.values()
    )
    paired_faults = [pair for pair in by_case.values() if set(pair) == {"dsl", "baseline"}
                     and all(r["case_kind"] == "fault" and r["rule_category"] == "semantic"
                             and r["outcome"] in FAULTS for r in pair.values())]
    paired_controls = [pair for pair in by_case.values() if set(pair) == {"dsl", "baseline"}
                       and all(r["case_kind"] == "control" and r["outcome"] in CONTROLS for r in pair.values())]
    matched = {}
    for approach in ("dsl", "baseline"):
        detected = sum(p[approach]["outcome"] == "DETECTED" for p in paired_faults)
        alarms = sum(p[approach]["outcome"] == "FALSE_ALARM" for p in paired_controls)
        matched[approach] = {"detected": detected, "missed": len(paired_faults) - detected,
            "semantic_recall_denominator": len(paired_faults),
            "semantic_recall": detected / len(paired_faults) if paired_faults else None,
            "false_alarms": alarms, "valid_controls": len(paired_controls),
            "false_alarm_rate": alarms / len(paired_controls) if paired_controls else None}
    return {"case_count": len(by_case), "executed_paired_cases": comparable,
            "approaches": totals, "matched_semantic_and_control_comparison": matched, "errors": errors,
            "note": "Not-run, blocked and inapplicable rows excluded; no unobserved results inferred."}


def load_matrix(path):
    with path.open(newline="", encoding="utf-8-sig") as f:
        reader = csv.DictReader(f, delimiter="\t")
        if not reader.fieldnames or not REQUIRED.issubset(reader.fieldnames):
            raise ValueError("Missing columns: " + ", ".join(sorted(REQUIRED - set(reader.fieldnames or []))))
        return list(reader)


def main():
    p = argparse.ArgumentParser(description=__doc__)
    p.add_argument("--matrix", type=Path, default=ROOT / "evaluation/journal/fault-pairs.tsv")
    p.add_argument("--out", type=Path)
    args = p.parse_args()
    try:
        report = summarize(load_matrix(args.matrix))
    except (OSError, ValueError) as exc:
        p.error(str(exc))
    if args.out:
        if args.out.exists():
            p.error("Output already exists; select a new observation report path.")
        args.out.parent.mkdir(parents=True, exist_ok=True)
        args.out.write_text(json.dumps(report, indent=2) + "\n", encoding="utf-8")
    print(json.dumps(report, indent=2))
    return 1 if report["errors"] else 0


if __name__ == "__main__":
    raise SystemExit(main())

