"""Collect actual metamodel, exported JUnit, raw observations and source into a draft ZIP."""
import argparse
import json
import re
import shutil
import sys
import xml.etree.ElementTree as ET
import zipfile
from pathlib import Path

from journal_common import ROOT, command, digest, files, inventory, save, stamp

PROJECTS = ["org.xtext.ucd.mlopsdsl", "org.xtext.ucd.mlopsdsl.ide", "org.xtext.ucd.mlopsdsl.tests",
            "org.xtext.ucd.mlopsdsl.ui", "org.xtext.ucd.mlopsdsl.ui.tests", "mlops-tests"]
EXCLUDE = {".git", ".metadata", ".terraform", "__pycache__", "bin", "target", "src-gen", "xtend-gen",
           ".venv-evaluation", "node_modules"}
SECRET_SUFFIXES = {".pem", ".key", ".pfx", ".p12", ".tfstate", ".tfplan"}


def safe_file(path, base):
    return not any(x in EXCLUDE or x.startswith(".venv") for x in path.relative_to(base).parts) and not (
        path.name.startswith(".env") or path.suffix.lower() in SECRET_SUFFIXES or ".tfstate" in path.name)


def copy_sources(source, target):
    for path in files(source):
        if safe_file(path, source):
            destination = target / path.relative_to(source)
            destination.parent.mkdir(parents=True, exist_ok=True)
            shutil.copy2(path, destination)


def inspect_junit(path):
    root = ET.parse(path).getroot()
    cases = [e for e in root.iter() if e.tag.rsplit("}", 1)[-1] == "testcase"]
    own = [e for e in cases if e.get("classname", "").endswith(".MlopsDslValidationTest")]
    source = ROOT / "org.xtext.ucd.mlopsdsl.tests/src/org/xtext/ucd/mlopsdsl/tests/MlopsDslValidationTest.java"
    expected = set(re.findall(r"@Test\s+public void (\w+)\(", source.read_text(encoding="utf-8-sig")))
    observed = {e.get("name", "").split("(", 1)[0] for e in own}
    unsuccessful = [e.get("name") for e in own if any(c.tag.rsplit("}", 1)[-1] in {"failure", "error", "skipped"} for c in e)]
    return {"sha256": digest(path), "expected_test_count": len(expected), "observed_test_count": len(own),
            "missing_tests": sorted(expected - observed), "unsuccessful_tests": unsuccessful,
            "status": "PASS" if expected <= observed and not unsuccessful and len(own) == len(expected) else "FAIL"}


def inspect_metamodel(path):
    errors = []
    manifest = path / "META-INF/MANIFEST.MF"
    if not manifest.is_file() or not re.search(r"^Bundle-SymbolicName:\s*meta-model(?:;|\s|$)", manifest.read_text(encoding="utf-8-sig"), re.M):
        errors.append("Expected the actual meta-model Eclipse bundle (Bundle-SymbolicName: meta-model)")
    ecores = list(path.rglob("*.ecore"))
    if not any(e.get("nsURI") == "http://ucd.ac.ma/mlops" for p in ecores for e in ET.parse(p).getroot().iter()):
        errors.append("Actual mlops Ecore package nsURI http://ucd.ac.ma/mlops not found")
    if not (path / "model/mlops.genmodel").is_file():
        errors.append("MWE2 dependency model/mlops.genmodel missing")
    return {"status": "FAIL" if errors else "PASS", "errors": errors}


def main():
    p = argparse.ArgumentParser(description=__doc__)
    p.add_argument("--metamodel", type=Path, required=True)
    p.add_argument("--junit", type=Path, required=True)
    p.add_argument("--observations", type=Path, required=True, help="Directory containing real dsl-observations.json and generated-c00")
    p.add_argument("--out", type=Path, required=True)
    p.add_argument("--bundles-info", type=Path, help="Actual Eclipse configuration/org.eclipse.equinox.simpleconfigurator/bundles.info")
    p.add_argument("--evidence", type=Path, action="append", default=[], help="Additional immutable comparison/evolution run directory; repeatable")
    args = p.parse_args()
    if args.out.exists() or args.out.resolve().is_relative_to(ROOT):
        p.error("Choose a fresh destination outside this repository to avoid recursive collection")
    args.out.mkdir(parents=True)
    report = {"recorded_at_utc": stamp(), "status": "BLOCKED", "reviewer_access": "NOT_VERIFIED",
              "release_status": "DRAFT_NOT_PUBLISHED", "issues": []}
    for name, path in (("metamodel", args.metamodel), ("junit", args.junit), ("observations", args.observations)):
        if not path.exists():
            report["issues"].append("Missing " + name + ": " + str(path))
    if not report["issues"]:
        try:
            report["metamodel"] = inspect_metamodel(args.metamodel)
            report["junit"] = inspect_junit(args.junit)
            for key in ("metamodel", "junit"):
                if report[key]["status"] != "PASS":
                    report["issues"].append(key + " preflight failed")
            observations = json.loads((args.observations / "dsl-observations.json").read_text(encoding="utf-8-sig"))
            # The exporter reads classpath resources from the Xtext test project.
            # mlops-tests mirrors the DSL files but does not contain this manifest.
            fixtures = ROOT / "org.xtext.ucd.mlopsdsl.tests/src/validation"
            with (fixtures / "scenarios.tsv").open(encoding="utf-8-sig", newline="") as f:
                import csv
                scenarios = list(csv.DictReader(f, delimiter="\t"))
            actual = observations["cases"]
            if len(actual) != len(scenarios) or {x["case_id"] for x in actual} != {x["id"] for x in scenarios}:
                report["issues"].append("Incomplete or duplicate DSL case observations")
            indexed = {x["case_id"]: x for x in actual}
            for s in scenarios:
                o = indexed.get(s["id"], {})
                expected = "PASS" if s["expected"] == "ACCEPT" else "DETECTED"
                if o.get("outcome") != expected or o.get("source_sha256") != digest(fixtures / s["file"]):
                    report["issues"].append("Failed/mismatched real DSL observation: " + s["id"])
            if not (args.observations / "generated-c00/runtime/k8s/ingress.yaml").is_file():
                report["issues"].append("Real generated C00 export missing")
        except (ValueError, OSError, ET.ParseError, KeyError) as exc:
            report["issues"].append(str(exc))
    if report["issues"]:
        save(args.out / "preflight.json", report)
        print("Package BLOCKED:", report["issues"], "; see", args.out / "preflight.json")
        return 2
    stage = args.out / "package"
    for name in [*PROJECTS, "tools", "evaluation", "tests"]:
        if not (ROOT / name).is_dir():
            report["issues"].append("Missing source directory: " + name)
            continue
        copy_sources(ROOT / name, stage / name)
    copy_sources(args.metamodel, stage / "meta-model")
    copy_sources(args.observations, stage / "evidence/eclipse-observations")
    (stage / "evidence").mkdir(parents=True, exist_ok=True)
    shutil.copy2(args.junit, stage / "evidence/junit.xml")
    if args.bundles_info:
        shutil.copy2(args.bundles_info, stage / "evidence/eclipse-bundles.info")
        report["eclipse_bundles_sha256"] = digest(args.bundles_info)
    else:
        report["eclipse_bundles"] = "NOT_COLLECTED; Eclipse/EMF/OCL installation is not completely pinned"
    for i, evidence in enumerate(args.evidence):
        if not evidence.is_dir():
            report["issues"].append("Missing evidence directory: " + str(evidence))
        else:
            copy_sources(evidence, stage / "evidence" / f"additional-{i:02d}")
    environment = {}
    for name, argv in {"git": ["git", "rev-parse", "HEAD"], "java": ["java", "-version"],
                       "python": [sys.executable, "--version"], "packages": [sys.executable, "-m", "pip", "freeze"],
                       "kubectl": ["kubectl", "version", "--client", "-o", "json"], "terraform": ["terraform", "version", "-json"]}.items():
        environment[name], _ = command(argv, stage / "evidence/environment", name, ROOT)
    report["environment_capture"] = environment
    report["status"] = "DRAFT_COLLECTED" if not report["issues"] else "BLOCKED"
    report["outstanding"] = ["Practitioner equivalence review", "Windows Eclipse/Xtext version confirmation",
        "All study tasks and Terraform checks completed or explicitly limited in manuscript", "Unauthenticated reviewer download verified after publication"]
    save(stage / "evidence/manifest.json", {"files": inventory(stage)})
    save(stage / "evidence/preflight.json", report)
    if report["issues"]:
        print("Collection blocked:", report["issues"])
        return 2
    archive = args.out / "mlops-dsl-replication-draft.zip"
    with zipfile.ZipFile(archive, "w", compression=zipfile.ZIP_DEFLATED) as z:
        for path in files(stage):
            z.write(path, path.relative_to(stage).as_posix())
    save(args.out / "archive.json", {"file": archive.name, "sha256": digest(archive), "status": "DRAFT_NOT_PUBLISHED", "reviewer_access": "NOT_VERIFIED"})
    print("Draft archive:", archive, "; reviewer access remains unverified")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
