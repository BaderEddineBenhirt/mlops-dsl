#!/usr/bin/env python3
"""Validate the journal work-package ledger; never infer study completion."""
import argparse
import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
STATUSES = {"not_run", "partial", "blocked", "complete"}


def audit(plan):
    problems = []
    if plan.get("schema_version") != 1 or not isinstance(plan.get("work_packages"), list):
        return ["Expected work-packages schema_version=1 and work_packages list"], []
    seen = set()
    packages = plan["work_packages"]
    for index, item in enumerate(packages):
        if not isinstance(item, dict):
            problems.append(f"work_packages[{index}]: not an object")
            continue
        wid = item.get("id")
        if not isinstance(wid, str) or not wid:
            problems.append(f"work_packages[{index}]: missing id")
        elif wid in seen:
            problems.append(f"duplicate package {wid}")
        else:
            seen.add(wid)
        for name in ("title", "reference", "acceptance_criteria"):
            if not isinstance(item.get(name), str) or not item[name].strip():
                problems.append(f"{wid}: missing {name}")
        status = item.get("status")
        if status not in STATUSES:
            problems.append(f"{wid}: unknown status {status}")
        if not isinstance(item.get("completed"), bool):
            problems.append(f"{wid}: completed must be boolean")
        records = item.get("evidence_records")
        if not isinstance(records, list):
            problems.append(f"{wid}: evidence_records must be a list")
            records = []
        if item.get("completed") and (status != "complete" or not records):
            problems.append(f"{wid}: completion requires status complete and evidence")
        if status == "complete" and not item.get("completed"):
            problems.append(f"{wid}: status complete but completed is false")
        for evidence in records:
            if not isinstance(evidence, dict) or not all(
                isinstance(evidence.get(k), str) and evidence[k].strip()
                for k in ("path", "sha256", "scope", "recorded_at_utc")
            ):
                problems.append(f"{wid}: evidence entry lacks path, sha256, scope or timestamp")
    return problems, packages


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--plan", type=Path, default=ROOT / "evaluation/journal/work-packages.json")
    parser.add_argument("--strict", action="store_true",
                        help="Fail if even one work package is not completed")
    args = parser.parse_args()
    try:
        data = json.loads(args.plan.read_text(encoding="utf-8"))
    except (OSError, json.JSONDecodeError) as exc:
        parser.error(str(exc))
    problems, packages = audit(data)
    if problems:
        for issue in problems:
            print("INVALID:", issue)
        return 1
    for p in packages:
        print(f"{p['id']}: {p['status'].upper()} - {p['title']}")
    pending = [p["id"] for p in packages if not p["completed"]]
    print("Pending evidence packages:", ", ".join(pending) if pending else "none")
    print("This is a planning/evidence audit, NOT an experiment or acceptance probability.")
    return 2 if args.strict and pending else 0


if __name__ == "__main__":
    raise SystemExit(main())
