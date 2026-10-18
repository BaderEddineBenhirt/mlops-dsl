#!/usr/bin/env python3
"""Build deterministic validation fixtures. --check audits files, not Xtext behavior."""
import argparse
import csv
import hashlib
import io
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
BASE = ROOT / "mlops-tests/src/examples/azure.mlops"
RUNTIME = Path("mlops-tests/src/validation")
RESOURCES = Path("org.xtext.ucd.mlopsdsl.tests/src/validation")


def replace_once(text, old, new):
    if text.count(old) != 1:
        raise ValueError(f"Expected exactly one occurrence: {old!r}")
    return text.replace(old, new, 1)


def build():
    base = BASE.read_text(encoding="utf-8").replace("\r\n", "\n").rstrip() + "\n"
    plan_gpu = 'entryCmd = "python serve.py"\n\n  resources = [\n    Resource { key = "GPU" amount = 1 }'
    cases = [
        ("C00", "valid_azure", "ACCEPT", "control", "", "Unmodified Azure requirements", base),
        ("C01", "valid_private_without_tls", "ACCEPT", "control", "", "Private exposure permits TLS disabled", replace_once(replace_once(base, "exposure = PUBLIC", "exposure = PRIVATE"), "tls = true", "tls = false")),
        ("C02", "valid_min_api_port", "ACCEPT", "boundary", "", "API port lower boundary", replace_once(base, "port = 8080", "port = 1")),
        ("C03", "valid_max_api_port", "ACCEPT", "boundary", "", "API port upper boundary", replace_once(base, "port = 8080", "port = 65535")),
        ("S01", "invalid_split", "REJECT", "semantic", "Dataset split ratios must sum to 1.0", "Split total is 0.90", replace_once(base, 'name = "train" ratio = 0.80', 'name = "train" ratio = 0.70')),
        ("S02", "missing_gpu", "REJECT", "semantic", "GPU compute profile requires at least one GPU resource", "Operational GPU amount is zero", replace_once(base, plan_gpu, plan_gpu.replace("amount = 1", "amount = 0"))),
        ("S03", "public_without_tls", "REJECT", "semantic", "Public network exposure requires TLS", "Public endpoint disables TLS", replace_once(base, "tls = true", "tls = false")),
        ("S04", "missing_api_port", "REJECT", "semantic", "API serving requires a port between 1 and 65535", "API port omitted", replace_once(base, "  port = 8080\n", "")),
        ("S05", "missing_cloud_region", "REJECT", "semantic", "InfraPlan.region is required for cloud providers", "Azure region blank", replace_once(base, 'region = "westeurope"', 'region = ""')),
        ("S06", "missing_public_domain", "REJECT", "semantic", "Public network exposure requires a domain", "Public domain blank with TLS enabled", replace_once(base, 'domain = "vision.azure.company.com"', 'domain = ""')),
        ("S07", "invalid_node_counts", "REJECT", "semantic", "Node pool counts must satisfy minNodes <= desiredNodes <= maxNodes", "Desired nodes below minimum", replace_once(base, "desiredNodes = 2", "desiredNodes = 0")),
        ("S08", "invalid_decision_weights", "REJECT", "semantic", "Decision criterion weights must sum to 1.0", "Weight total is 0.90", replace_once(base, "weight = 0.35", "weight = 0.25")),
        ("S09", "contradictory_criterion_bounds", "REJECT", "semantic", "Decision criterion minValue cannot exceed maxValue", "Criterion minimum exceeds maximum", replace_once(base, "minValue = 0.90", "minValue = 0.90\n      maxValue = 0.80")),
        ("S10", "broken_workflow_reference", "REJECT", "linking", "missing_validation_task", "Edge target does not exist", replace_once(base, "Edge { from = w_prepare to = w_version }", "Edge { from = w_prepare to = missing_validation_task }")),
        ("S11", "empty_entry_command", "REJECT", "semantic", "OperationalPlan.entryCmd is required", "Execution command blank", replace_once(base, 'entryCmd = "python serve.py"', 'entryCmd = ""')),
        ("S12", "zero_split_ratio", "REJECT", "semantic", "A dataset split ratio must be greater than 0 and at most 1", "One zero split with total still 1.00", replace_once(base, 'SplitSpec { name = "train" ratio = 0.80 },\n    SplitSpec { name = "valid" ratio = 0.10 }', 'SplitSpec { name = "train" ratio = 0.00 },\n    SplitSpec { name = "valid" ratio = 0.90 }')),
    ]
    files, rows = {}, []
    for sid, label, expected, category, diagnostic, mutation, text in cases:
        text = text.replace("vision_defects_infra", "validation_" + sid.lower())
        filename = sid.lower() + "_" + label + ".mlops"
        for folder in (RUNTIME, RESOURCES):
            files[folder / filename] = text
        rows.append([sid, filename, expected, category, diagnostic, mutation,
                     hashlib.sha256(text.encode()).hexdigest()])
    out = io.StringIO(newline="")
    writer = csv.writer(out, delimiter="\t", lineterminator="\n")
    writer.writerow(["id", "file", "expected", "category", "diagnostic_fragment", "mutation", "sha256"])
    writer.writerows(rows)
    for folder in (Path("evaluation/validation"), RESOURCES):
        files[folder / "scenarios.tsv"] = out.getvalue()
    return files


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--check", action="store_true")
    args = parser.parse_args()
    files = build()
    for relative, content in files.items():
        path = ROOT / relative
        if args.check:
            if not path.exists() or path.read_text(encoding="utf-8") != content:
                raise SystemExit(f"Fixture differs or is missing: {relative}")
        else:
            path.parent.mkdir(parents=True, exist_ok=True)
            path.write_text(content, encoding="utf-8", newline="\n")
    print("Fixture audit: 12 negative cases and 4 valid controls; canonical copies and SHA-256 manifest agree.")
    print("This audit does not execute the Xtext parser, validators, generator, or deployment.")


if __name__ == "__main__":
    main()
