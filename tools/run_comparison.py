"""Collect actual pilot observations; never infer manual effort or equivalence."""
import argparse
import hashlib
import json
import platform
import shutil
import subprocess
from datetime import datetime, timezone
from pathlib import Path

import yaml


def select(node, path):
    if not path:
        return [node]
    key, *rest = path
    if key == "*":
        children = node.values() if isinstance(node, dict) else node if isinstance(node, list) else []
        return [value for child in children for value in select(child, rest)]
    if isinstance(node, dict) and key in node:
        return select(node[key], rest)
    return []


def inventory(paths, root):
    records = []
    for path in sorted(paths):
        content = path.read_bytes()
        text = content.decode("utf-8-sig")
        # A transparent physical-line proxy, not a language-aware LOC metric.
        lines = [line for line in text.splitlines() if line.strip()]
        records.append({
            "path": str(path.relative_to(root)), "bytes": len(content),
            "nonblank_lines": len(lines),
            "sha256": hashlib.sha256(content).hexdigest(),
        })
    return {"files": records, "file_count": len(records),
            "nonblank_lines": sum(r["nonblank_lines"] for r in records)}


def inspect_artifacts(root, contract, terraform, log_root):
    root = root.resolve()
    if not root.is_dir():
        raise ValueError(f"Artifact directory does not exist: {root}")
    paths = [p for p in root.rglob("*") if p.is_file()
             and p.suffix in {".yaml", ".yml", ".json", ".tf"}
             and not any(part.startswith(".") for part in p.relative_to(root).parts)]
    parsed, syntax = {}, []
    for path in sorted(paths):
        if path.suffix == ".tf":
            continue
        relative = path.relative_to(root).as_posix()
        try:
            text = path.read_text(encoding="utf-8-sig")
            documents = [json.loads(text)] if path.suffix == ".json" else list(yaml.safe_load_all(text))
            parsed[relative] = documents
            syntax.append({"file": relative, "status": "pass"})
        except (ValueError, yaml.YAMLError) as exc:
            syntax.append({"file": relative, "status": "fail", "error": str(exc)})
    checks = []
    for check in contract["checks"]:
        documents = parsed.get(check["file"], [])
        actual = [v for document in documents for v in select(document, check["path"])]
        passed = bool(actual) and all(value == check["expected"] for value in actual)
        checks.append({**check, "actual": actual, "status": "pass" if passed else "fail"})
    tf_result = {"status": "not_run"}
    if terraform:
        executable = shutil.which("terraform")
        tf_root = root / "infra" / "terraform"
        if not executable or not tf_root.is_dir():
            tf_result = {"status": "blocked", "reason": "Terraform executable or infra/terraform directory missing"}
        else:
            log_root.mkdir(parents=True, exist_ok=True)
            tf_result = {"status": "pass", "commands": []}
            for name, arguments in [("init", ["init", "-backend=false", "-input=false", "-no-color"]),
                                    ("validate", ["validate", "-json"])]:
                try:
                    result = subprocess.run([executable, *arguments], cwd=tf_root,
                                            capture_output=True, text=True, timeout=180)
                    (log_root / f"{name}.stdout.txt").write_text(result.stdout, encoding="utf-8")
                    (log_root / f"{name}.stderr.txt").write_text(result.stderr, encoding="utf-8")
                    tf_result["commands"].append({"argv": [executable, *arguments], "exit_code": result.returncode})
                    if result.returncode:
                        tf_result["status"] = "fail"
                        break
                except subprocess.TimeoutExpired:
                    tf_result["status"] = "blocked"
                    tf_result["reason"] = f"{name} timed out after 180 seconds"
                    break
    return {"root": str(root), "inventory": inventory(paths, root),
            "syntax": syntax, "requirement_checks": checks, "terraform": tf_result}


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--dsl", type=Path, required=True)
    parser.add_argument("--generated", type=Path, required=True)
    parser.add_argument("--baseline", type=Path)
    parser.add_argument("--contract", type=Path, default=Path(__file__).resolve().parents[1] / "evaluation/comparison/c00-contract.json")
    parser.add_argument("--out", type=Path, required=True)
    parser.add_argument("--terraform", action="store_true")
    args = parser.parse_args()
    if args.out.exists():
        parser.error("Output already exists; use a new run filename to preserve raw observations.")
    contract = json.loads(args.contract.read_text(encoding="utf-8"))
    report = {
        "recorded_at_utc": datetime.now(timezone.utc).isoformat(),
        "python": platform.python_version(), "pyyaml": yaml.__version__,
        "platform": platform.platform(), "contract": contract,
        "contract_sha256": hashlib.sha256(args.contract.read_bytes()).hexdigest(),
        "runner_sha256": hashlib.sha256(Path(__file__).read_bytes()).hexdigest(),
        "equivalence_status": "not_reviewed", "authoring_time": "not_measured",
        "scope": "Configuration inventory, parsing and selected output requirements; not deployment or full functional equivalence.",
        "dsl": inventory([args.dsl.resolve()], args.dsl.resolve().parent),
    }
    for label, root in [("generated", args.generated), ("baseline", args.baseline)]:
        report[label] = inspect_artifacts(root, contract, args.terraform,
                                         args.out.parent / (args.out.stem + "-logs") / label) if root else {"status": "not_run"}
    args.out.parent.mkdir(parents=True, exist_ok=True)
    args.out.write_text(json.dumps(report, indent=2) + "\n", encoding="utf-8")
    failed = False
    for label in ("generated", "baseline"):
        result = report[label]
        if "requirement_checks" not in result:
            print(f"{label}: not_run")
            continue
        bad = [c["id"] for c in result["requirement_checks"] if c["status"] != "pass"]
        syntax_bad = sum(c["status"] != "pass" for c in result["syntax"])
        print(f"{label}: failed requirements={bad}; parsing errors={syntax_bad}; Terraform={result['terraform']['status']}")
        failed |= bool(bad or syntax_bad or result["terraform"]["status"] in {"fail", "blocked"})
    print(f"Saved actual observations: {args.out}")
    return 1 if failed else 0


if __name__ == "__main__":
    raise SystemExit(main())
