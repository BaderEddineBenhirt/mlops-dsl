"""Tests for evidence schema; NOT experiments on the Xtext language."""
import importlib.util
import json
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]


def load(name):
    path = ROOT / "tools" / (name + ".py")
    spec = importlib.util.spec_from_file_location(name, path)
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


audit = load("audit_journal_evidence")
faults = load("summarize_fault_pairs")
measure = load("measure_authoring_sources")


class JournalEvidenceTest(unittest.TestCase):
    def test_committed_plan_is_well_formed_and_incomplete(self):
        plan = json.loads((ROOT / "evaluation/journal/work-packages.json").read_text(encoding="utf-8"))
        errors, packages = audit.audit(plan)
        self.assertEqual(errors, [])
        self.assertEqual(len(packages), 7)
        self.assertTrue(all(not p["completed"] for p in packages))

    def test_completion_without_evidence_is_invalid(self):
        plan = json.loads((ROOT / "evaluation/journal/work-packages.json").read_text(encoding="utf-8"))
        plan["work_packages"][0]["completed"] = True
        plan["work_packages"][0]["status"] = "complete"
        plan["work_packages"][0]["evidence_records"] = []
        errors, _ = audit.audit(plan)
        self.assertTrue(any("requires status complete and evidence" in e for e in errors))

    def test_strict_gate_fails_when_tasks_pending(self):
        result = subprocess.run([sys.executable, str(ROOT / "tools/audit_journal_evidence.py"),
                                 "--strict"], cwd=ROOT, capture_output=True, text=True)
        self.assertEqual(result.returncode, 2)

    def test_fault_matrix_is_valid_but_unexecuted(self):
        rows = faults.load_matrix(ROOT / "evaluation/journal/fault-pairs.tsv")
        result = faults.summarize(rows)
        self.assertEqual(result["errors"], [])
        self.assertEqual(result["case_count"], 16)
        self.assertEqual(result["executed_paired_cases"], 0)
        self.assertIsNone(result["approaches"]["dsl"]["semantic_recall"])
        self.assertIsNone(result["approaches"]["baseline"]["false_alarm_rate"])

    def test_paired_semantic_detection_does_not_count_linking(self):
        rows = faults.load_matrix(ROOT / "evaluation/journal/fault-pairs.tsv")
        for r in rows:
            if r["case_id"] == "S01":
                r.update(outcome="DETECTED" if r["approach"] == "dsl" else "MISSED",
                         evidence_path="runs/real-log.txt", detection_stage="xtext" if r["approach"] == "dsl" else "")
            if r["case_id"] == "S10":
                r.update(outcome="DETECTED", evidence_path="runs/linking-log.txt",
                         detection_stage="linking")
        r = faults.summarize(rows)
        self.assertEqual(r["errors"], [])
        self.assertEqual(r["approaches"]["dsl"]["semantic_recall_denominator"], 1)
        self.assertEqual(r["approaches"]["dsl"]["linking_faults"]["DETECTED"], 1)
        self.assertEqual(r["approaches"]["baseline"]["semantic_recall"], 0.0)
        self.assertEqual(r["executed_paired_cases"], 2)

    def test_missing_evidence_is_rejected(self):
        rows = faults.load_matrix(ROOT / "evaluation/journal/fault-pairs.tsv")
        rows[0]["outcome"] = "DETECTED"
        r = faults.summarize(rows)
        self.assertTrue(any("missing evidence_path" in x for x in r["errors"]))
        self.assertTrue(any("missing detection_stage" in x for x in r["errors"]))

    def test_not_applicable_requires_reason(self):
        rows = faults.load_matrix(ROOT / "evaluation/journal/fault-pairs.tsv")
        rows[0]["outcome"] = "NOT_APPLICABLE"
        self.assertTrue(any("requires a reason" in e for e in faults.summarize(rows)["errors"]))

    def test_distinguish_source_from_generated(self):
        with tempfile.TemporaryDirectory() as td:
            path = Path(td)
            (path / "source.yaml").write_text("# comment\nport: 8080\n\n", encoding="utf-8")
            (path / "generated").mkdir()
            (path / "generated" / "rendered.yaml").write_text("port: 9000\n", encoding="utf-8")
            inventory = measure.inventory(path)
            self.assertEqual(inventory["file_count"], 1)
            self.assertEqual(inventory["line_proxy_total"], 1)
            self.assertGreater(inventory["token_proxy_total"], 0)

    def test_source_report_never_claims_superiority(self):
        with tempfile.TemporaryDirectory() as td:
            p = Path(td)
            f = p / "x.mlops"
            b = p / "baseline"
            f.write_text("foo = 1\n", encoding="utf-8")
            b.mkdir()
            (b / "a.yaml").write_text("a: 1\n", encoding="utf-8")
            report = p / "measurement.json"
            r = subprocess.run([sys.executable, str(ROOT / "tools/measure_authoring_sources.py"),
                                "--dsl", str(f), "--baseline", str(b), "--out", str(report)],
                               capture_output=True, text=True)
            self.assertEqual(r.returncode, 0, r.stderr)
            result = json.loads(report.read_text(encoding="utf-8"))
            self.assertEqual(result["scope_equivalence"], "NOT_REVIEWED")
            self.assertIsNone(result["claimed_productivity_or_maintainability"])
            duplicate = subprocess.run([sys.executable, str(ROOT / "tools/measure_authoring_sources.py"),
                                       "--dsl", str(f), "--baseline", str(b), "--out", str(report)],
                                      capture_output=True, text=True)
            self.assertNotEqual(duplicate.returncode, 0)


if __name__ == "__main__":
    unittest.main()

