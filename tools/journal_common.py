"""Shared offline observations. Domain rules are separate from native validators."""
import hashlib
import json
import math
import subprocess
from datetime import datetime, timezone
from pathlib import Path

import hcl2
import yaml

ROOT = Path(__file__).resolve().parents[1]


def save(path, value):
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(value, indent=2) + "\n", encoding="utf-8")


def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def files(root):
    return sorted(p for p in root.rglob("*") if p.is_file() and
                  not any(x in {".terraform", "__pycache__", ".git"} for x in p.relative_to(root).parts))


def inventory(root):
    return [{"path": p.relative_to(root).as_posix(), "sha256": digest(p), "bytes": p.stat().st_size}
            for p in files(root)]


def command(argv, logs, name, cwd=None):
    logs.mkdir(parents=True, exist_ok=True)
    try:
        r = subprocess.run(argv, cwd=cwd, capture_output=True, text=True,
                           encoding="utf-8", errors="replace", timeout=55)
        out, err, code = r.stdout, r.stderr, r.returncode
        status = "PASS" if code == 0 else "FAIL"
    except (OSError, subprocess.TimeoutExpired) as exc:
        out, err, code, status = "", str(exc), None, "BLOCKED"
    (logs / (name + ".stdout.txt")).write_text(out, encoding="utf-8")
    (logs / (name + ".stderr.txt")).write_text(err, encoding="utf-8")
    return {"argv": argv, "exit_code": code, "status": status,
            "stdout": str(logs / (name + ".stdout.txt")), "stderr": str(logs / (name + ".stderr.txt"))}, out


def parse(root):
    docs, errors = {}, []
    for p in files(root):
        if p.suffix not in {".tf", ".yaml", ".yml"}:
            continue
        try:
            text = p.read_text(encoding="utf-8-sig")
            docs[p.relative_to(root).as_posix()] = hcl2.loads(text) if p.suffix == ".tf" else yaml.safe_load(text)
        except Exception as exc:
            errors.append({"file": p.relative_to(root).as_posix(), "error": str(exc)})
    return docs, errors


def resources(docs, kind):
    return [value for name, doc in docs.items() if name.endswith(".tf")
            for block in doc.get("resource", []) for value in block.get(kind, {}).values()]


def root_defaults(docs):
    return {key: value.get("default") for name, doc in docs.items()
            if name.startswith("infra/terraform/") and "/modules/" not in name and name.endswith(".tf")
            for block in doc.get("variable", []) for key, value in block.items()}


def domain_checks(docs):
    """Study oracle, NOT a Kubernetes schema or Terraform provider validator."""
    issues = []
    def fail(rule, detail):
        issues.append({"rule": rule, "detail": detail})
    ingress = docs.get("runtime/k8s/ingress.yaml", {}) or {}
    spec = ingress.get("spec", {})
    # C01 retains an internal Ingress; presence alone must not mean PUBLIC.
    defaults = root_defaults(docs)
    public = defaults.get("public_exposure", True)
    if public:
        if not spec.get("tls") or not all(t.get("hosts") and t.get("secretName") for t in spec["tls"]):
            fail("S03", "Public ingress has no TLS host/Secret reference")
        if not spec.get("rules") or any(not r.get("host", "").strip() for r in spec["rules"]):
            fail("S06", "Public ingress host is empty")
    deployment = docs.get("runtime/k8s/deployment.yaml", {}) or {}
    service = docs.get("runtime/k8s/service.yaml", {}) or {}
    ports = []
    for c in deployment.get("spec", {}).get("template", {}).get("spec", {}).get("containers", []):
        ports.extend(p.get("containerPort") for p in c.get("ports", []))
        ports.extend(c.get(probe, {}).get("httpGet", {}).get("port") for probe in ("livenessProbe", "readinessProbe"))
    for p in service.get("spec", {}).get("ports", []):
        ports.extend([p.get("port"), p.get("targetPort")])
    for r in spec.get("rules", []):
        ports.extend(p.get("backend", {}).get("service", {}).get("port", {}).get("number")
                     for p in r.get("http", {}).get("paths", []))
    if len(ports) != 6 or any(type(p) is not int or not 1 <= p <= 65535 for p in ports):
        fail("S04", "API requires six valid numeric port references")
    elif len(set(ports)) != 1:
        fail("PORT_CONSISTENCY", "API port references disagree")
    if not isinstance(defaults.get("region"), str) or not defaults["region"].strip():
        fail("S05", "Root cloud region default is missing/blank (no tfvars supplied in this study)")
    pools = resources(docs, "azurerm_kubernetes_cluster_node_pool")
    pools += [p for cluster in resources(docs, "azurerm_kubernetes_cluster") for p in cluster.get("default_node_pool", [])]
    for p in pools:
        lo, desired, hi = p.get("min_count"), p.get("node_count"), p.get("max_count")
        if all(type(x) is int for x in (lo, hi)):
            if lo < 0 or hi < lo or (type(desired) is int and not lo <= desired <= hi):
                fail("S07", "Node pool requires nonnegative min <= desired <= max")
    criteria = (docs.get("mlops/decision-gate.yaml", {}) or {}).get("decisionGate", {}).get("criteria", [])
    weights = [c.get("weight") for c in criteria]
    if not weights or any(not isinstance(x, (int, float)) or not math.isfinite(x) or x <= 0 for x in weights) or abs(sum(weights) - 1) > 0.0001:
        fail("S08", "Decision weights must be finite, positive and sum to one")
    if any(c.get("minValue") is not None and c.get("maxValue") is not None and c["minValue"] > c["maxValue"] for c in criteria):
        fail("S09", "Criterion minValue exceeds maxValue")
    return issues


def terraform(root, executable, logs):
    """Initialization/validation only; never plan/apply or authenticate to a cloud."""
    tf = root / "infra/terraform"
    records = []
    for name, args in [("version", ["version", "-json"]),
                       ("init", ["init", "-backend=false", "-input=false", "-no-color"]),
                       ("validate", ["validate", "-json"])]:
        record, output = command([executable, *args], logs, name, tf)
        records.append(record)
        if record["status"] != "PASS":
            blocked = name != "validate"
            if name == "validate":
                try:
                    diagnostics = json.loads(output).get("diagnostics", [])
                    blocked = any(d.get("summary") == "Failed to load plugin schemas" for d in diagnostics)
                except (ValueError, AttributeError):
                    blocked = True
            return {"status": "BLOCKED" if blocked else record["status"], "commands": records}
    return {"status": "PASS", "commands": records}


def stamp():
    return datetime.now(timezone.utc).isoformat()
