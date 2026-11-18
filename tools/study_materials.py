"""Build development task pairs from existing, explicitly non-independent cases."""
import copy
import hashlib
import json
import re
import shutil
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]


def save(path, value):
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(value, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")


def sha(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def replace(path, old, new, count=None):
    text = path.read_text(encoding="utf-8-sig")
    n = text.count(old)
    if n == 0 or (count is not None and n != count):
        raise ValueError(f"Unexpected edit target in {path}: {old!r}, count={n}")
    path.write_text(text.replace(old, new), encoding="utf-8")


def materialise(out):
    """No generated output is fabricated. Java export and Kustomize run later."""
    if out.exists():
        raise ValueError("Use a new materials directory")
    out.mkdir(parents=True)
    items = []
    for provider in ("azure", "gcp"):
        azure = provider == "azure"
        fixture = ROOT / ("mlops-tests/src/validation/c00_valid_azure.mlops" if azure else
                          "mlops-tests/src/validation/gcp/c10_fraud_gcp.mlops")
        baseline = ROOT / ("evaluation/journal/baseline/c00-azure" if azure else
                           "evaluation/journal/gcp/baseline")
        contract_path = ROOT / ("evaluation/journal/c00-broader-contract.json" if azure else
                                "evaluation/journal/gcp/contract.json")
        contract = json.loads(contract_path.read_text())
        port, replicas = (8080, 2) if azure else (9091, 3)
        model_name = "vision_defects_model_v1" if azure else "fraud_classifier_v1"
        domain = "vision.azure.company.com" if azure else "fraud.example.org"
        for family in ("port", "registry", "replicas", "domain"):
            for variant in ("A", "B"):
                ident = f"{provider}-{family}-{variant}"
                folder = out / ident
                for phase in ("before", "after"):
                    dest = folder / phase
                    dest.mkdir(parents=True)
                    shutil.copy2(fixture, dest / "model.mlops")
                    shutil.copytree(baseline, dest / "baseline", ignore=shutil.ignore_patterns(
                        ".terraform", "*.tfstate*", "__pycache__", ".terraform.lock.hcl"))
                    if azure:
                        runtime = ROOT / "evaluation/comparison/baseline/c00-runtime"
                        shutil.copytree(runtime / "runtime", dest / "baseline/runtime")
                        shutil.copy2(runtime / "kustomization.yaml", dest / "baseline/kustomization.yaml")
                after = folder / "after"
                expected = copy.deepcopy(contract)
                if family == "port":
                    value = 9097 if variant == "A" else 9099
                    replace(after / "model.mlops", f"port = {port}", f"port = {value}", 1)
                    p = after / "baseline/runtime/k8s/service.yaml"
                    text = p.read_text()
                    match = re.search(r"(?m)^([ \t]*-?[ \t]*port:[ \t]*)" + str(port) + r"\b", text)
                    if not match:
                        raise ValueError("Authoritative Service port not found")
                    p.write_text(text[:match.start()] + match.group(1) + str(value) + text[match.end():])
                    for c in expected["checks"]:
                        if c.get("file", "").startswith("runtime/k8s/") and c.get("expected") == [port]:
                            c["expected"] = [value]
                elif family == "registry":
                    value = model_name.replace("_v1", "_v2" if variant == "A" else "_v3")
                    replace(after / "model.mlops", model_name, value)
                    for name in ("mlops/model-registry.yaml", "mlops/cicdct.yaml"):
                        replace(after / "baseline" / name, model_name, value)
                    expected = json.loads(json.dumps(expected).replace(model_name, value))
                elif family == "replicas":
                    value = 5 if variant == "A" else 7
                    replace(after / "model.mlops", f"replicas = {replicas}", f"replicas = {value}", 1)
                    replace(after / "baseline/runtime/k8s/deployment.yaml", f"replicas: {replicas}", f"replicas: {value}", 1)
                    for c in expected["checks"]:
                        if c.get("file") == "runtime/k8s/deployment.yaml" and c.get("path") == ["spec", "replicas"]:
                            c["expected"] = [value]
                else:
                    value = "serving-a.example.org" if variant == "A" else "serving-b.example.org"
                    replace(after / "model.mlops", domain, value, 1)
                    # Kustomize propagates the routing host into its TLS host.
                    replace(after / "baseline/runtime/k8s/ingress.yaml", domain, value)
                    expected = json.loads(json.dumps(expected).replace(domain, value))
                save(folder / "before/contract.json", contract)
                save(folder / "after/contract.json", expected)
                items.append({"id": ident, "project_id": "C00" if azure else "C10", "provider": provider,
                    "family": family, "variant": variant, "kind": "evolution", "development": True,
                    "expected_valid": True, "reviewed_by": None,
                    "instruction": f"Change {family} to {value}. Preserve all other required properties.",
                    "directory": ident})
    # Values already used for legacy fault development remain development cases.
    from run_gcp_comparison import changed_source, cases, mutate_baseline
    gcp_fixture = ROOT / "mlops-tests/src/validation/gcp/c10_fraud_gcp.mlops"
    original = gcp_fixture.read_text(encoding="utf-8")
    for case in cases():
        if case["category"] != "fault":
            continue
        ident = "gcp-" + case["case_id"]
        folder = out / ident
        folder.mkdir()
        (folder / "model.mlops").write_text(changed_source(original, case), encoding="utf-8")
        shutil.copytree(ROOT / "evaluation/journal/gcp/baseline", folder / "baseline")
        mutate_baseline(folder / "baseline", case["case_id"])
        items.append({"id": ident, "project_id": "C10", "provider": "gcp", "family": case["rule"],
                      "variant": "fault", "kind": "fault", "development": True,
                      "expected_valid": False, "expected_diagnostic": case["expected_message"],
                      "expected_rule": case["rule"], "reviewed_by": None, "directory": ident})
    save(out / "manifest.json", {"schema": 1, "status": "DEVELOPMENT_MATERIALS_NOT_NEW_RESULTS",
                                "independent_case_provenance": None, "items": items})
    lines = ["id\tsource\tdestination"]
    for item in items:
        folder = out / item["directory"]
        phases = ("before", "after") if item["kind"] == "evolution" else ("",)
        for phase in phases:
            here = folder / phase
            lines.append("\t".join((item["id"] + ("-" + phase if phase else ""),
                                     str((here / "model.mlops").resolve()),
                                     str((here / "dsl-export").resolve()))))
    (out / "jobs.tsv").write_text("\n".join(lines) + "\n", encoding="utf-8")
    return {"status": "PREPARED_NOT_EXECUTED", "evolution_tasks": 16, "development_faults": 7,
            "xtext_jobs": len(lines) - 1, "manifest": str(out / "manifest.json")}
