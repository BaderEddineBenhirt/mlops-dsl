#!/usr/bin/env python3
"""Descriptive source inventory, NOT a fair-scope superiority percentage."""
import argparse
import hashlib
import json
import re
from pathlib import Path

SUFFIXES = {".tf", ".yaml", ".yml", ".json", ".mlops"}
TOKEN = re.compile(r'[A-Za-z_][A-Za-z_0-9]*|\d+(?:\.\d+)?|"(?:\\.|[^"\\])*"|[^\s]')
ROOT = Path(__file__).resolve().parents[1]


def eligible(file, base):
    relative = file.relative_to(base)
    return file.is_file() and file.suffix.lower() in SUFFIXES and not any(
        part.startswith(".") or part in {"generated", "src-gen", ".terraform", "__pycache__"}
        for part in relative.parts
    )


def inventory(source):
    if not source.exists():
        raise ValueError(f"Missing source: {source}")
    base = source.parent if source.is_file() else source
    files = [source] if source.is_file() else sorted(p for p in source.rglob("*") if eligible(p, base))
    rows = []
    for file in files:
        if file.suffix.lower() not in SUFFIXES:
            continue
        raw = file.read_bytes()
        text = raw.decode("utf-8-sig")
        relevant = [s for s in text.splitlines()
                    if s.strip() and not s.lstrip().startswith(("#", "//", "/*", "*", "*/"))]
        # Transparent textual proxy, not language-aware parsing or true NCLOC.
        tokens = sum(len(TOKEN.findall(s)) for s in relevant)
        rows.append({"path": file.relative_to(base).as_posix(), "bytes": len(raw),
                     "sha256": hashlib.sha256(raw).hexdigest(),
                     "nonblank_noncomment_physical_lines_proxy": len(relevant),
                     "lexical_token_proxy": tokens})
    return {"files": rows, "file_count": len(rows),
            "line_proxy_total": sum(r["nonblank_noncomment_physical_lines_proxy"] for r in rows),
            "token_proxy_total": sum(r["lexical_token_proxy"] for r in rows)}


def main():
    p = argparse.ArgumentParser(description=__doc__)
    p.add_argument("--dsl", type=Path, required=True)
    p.add_argument("--baseline", type=Path, required=True)
    p.add_argument("--out", type=Path, required=True)
    args = p.parse_args()
    if args.out.exists():
        p.error("Output already exists; preserve immutable measurements.")
    try:
        dsl, baseline = inventory(args.dsl), inventory(args.baseline)
    except (ValueError, UnicodeError, OSError) as exc:
        p.error(str(exc))
    report = {
        "scope_equivalence": "NOT_REVIEWED",
        "baseline_provenance": "Requires human review and documentation",
        "claimed_productivity_or_maintainability": None,
        "method": "UTF-8 physical nonblank/non-full-line-comment and regex lexical token proxies",
        "limitations": [
            "Inline comments and multiline syntax are not language-aware stripped",
            "Generated output excluded when the selected input is authoritative only",
            "Different-scope DSL and baseline totals must not be interpreted comparatively",
            "Source locations duplicated and actual editing time require separate observation"
        ],
        "dsl": dsl, "baseline": baseline,
    }
    args.out.parent.mkdir(parents=True, exist_ok=True)
    args.out.write_text(json.dumps(report, indent=2) + "\n", encoding="utf-8")
    print("Archived descriptive inventories only:", args.out)
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
