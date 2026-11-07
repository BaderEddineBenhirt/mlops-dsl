"""Runner QA with temporary fixtures. These are NOT real DSL study results."""
import copy
import importlib.util
import json
import re
import sys
import tempfile
import unittest
from pathlib import Path
from types import SimpleNamespace
from unittest.mock import patch

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / "tools"))
import journal_common as common
import run_fault_comparison as faults
import run_journal_comparison as broader
import run_configuration_evolution as evolution
import build_replication_package as replication
import summarize_fault_pairs as pairs


class JournalExecutionTest(unittest.TestCase):
    def setUp(self):
        self.base = ROOT / "evaluation/journal/baseline/c00-azure"

    def test_all_policy_requirements_have_explicit_expected_values(self):
        contract = json.loads((ROOT / "evaluation/journal/c00-broader-contract.json").read_text())
        ids = [c["id"] for c in contract["checks"]]
        self.assertEqual(len(ids), len(set(ids)))
        docs, errors = common.parse(self.base)
        self.assertEqual(errors, [])
        for c in contract["checks"]:
            if c["kind"] == "yaml" and c["file"].startswith("mlops/"):
                self.assertEqual(broader.select(docs[c["file"]], c["path"]), c["expected"])

    def test_archived_generator_defects_are_detected(self):
        result = broader.inspect(ROOT / "evaluation/comparison/generated/c00-after-tls",
                                 json.loads((ROOT / "evaluation/journal/c00-broader-contract.json").read_text()))
        bad = {x["id"] for x in result["requirements"] if x["status"] == "FAIL"}
        self.assertTrue({"vnet_cidr", "subnet_cidr_literals", "decision-gate:decisionGate.criteria"} <= bad)
        self.assertIn("S09", {x["rule"] for x in result["domain_oracle"]})

    def fixture(self, directory):
        import shutil
        shutil.copytree(self.base, directory)
        shutil.copytree(ROOT / "evaluation/comparison/baseline/c00-runtime/runtime", directory / "runtime")
        # Simulate the documented replacement ONLY for runner QA; never archive as generation.
        faults.set_ports(directory, 8080)
        faults.update_yaml(directory, "runtime/k8s/ingress.yaml", lambda d: d["spec"]["tls"][0].update(hosts=["vision.azure.company.com"]))
        return directory

    def test_each_applicable_mutation_hits_its_target(self):
        with tempfile.TemporaryDirectory() as td:
            for cid in ("S03", "S04", "S05", "S06", "S07", "S08", "S09"):
                root = self.fixture(Path(td) / cid)
                faults.mutate(root, cid)
                docs, errors = common.parse(root)
                self.assertFalse(errors)
                self.assertEqual({x["rule"] for x in common.domain_checks(docs)}, {cid}, cid)

    def test_all_valid_controls_have_no_false_alarm(self):
        with tempfile.TemporaryDirectory() as td:
            for cid in ("C00", "C01", "C02", "C03"):
                root = self.fixture(Path(td) / cid)
                faults.mutate(root, cid)
                docs, errors = common.parse(root)
                self.assertEqual(errors, [])
                self.assertEqual(common.domain_checks(docs), [], cid)

    def test_nonfinite_weights_are_rejected(self):
        docs, _ = common.parse(self.base)
        docs["mlops/decision-gate.yaml"]["decisionGate"]["criteria"][0]["weight"] = float("nan")
        self.assertIn("S08", {x["rule"] for x in common.domain_checks(docs)})

    def test_faults_not_represented_are_excluded_with_reasons(self):
        self.assertEqual(set(faults.INAPPLICABLE), {"S01", "S02", "S10", "S11", "S12"})
        self.assertTrue(all(faults.INAPPLICABLE.values()))

    def test_matched_recall_excludes_one_sided_and_inapplicable_cases(self):
        rows = pairs.load_matrix(ROOT / "evaluation/journal/fault-pairs.tsv")
        for r in rows:
            if r["case_id"] == "S03":
                r.update(outcome="DETECTED", evidence_path="real-log", detection_stage="validator")
            if r["case_id"] == "S01":
                r.update(outcome="DETECTED", evidence_path="real-log", detection_stage="validator")
                if r["approach"] == "baseline":
                    r.update(outcome="NOT_APPLICABLE", n_a_reason="Not represented")
        result = pairs.summarize(rows)
        self.assertFalse(result["errors"])
        for r in result["matched_semantic_and_control_comparison"].values():
            self.assertEqual(r["semantic_recall_denominator"], 1)
            self.assertEqual(r["detected"], 1)

    def test_evolution_detects_changed_files_and_separate_hunks(self):
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            a, b = root / "before", root / "after"
            a.mkdir(); b.mkdir()
            (a / "x.mlops").write_text("modelName = v1\n" + "\n" * 10 + "ref = v1\n")
            (b / "x.mlops").write_text("modelName = v2\n" + "\n" * 10 + "ref = v2\n")
            result = evolution.changes(a, b, root / "diffs")
            self.assertEqual(result["files_changed"], 1)
            self.assertEqual(result["files"][0]["hunks"], 2)

    def test_evolution_contract_updates_registry_references(self):
        before, after = evolution.contract_for("registry", "before"), evolution.contract_for("registry", "after")
        self.assertIn("vision_defects_model_v1", json.dumps(before))
        self.assertNotIn("vision_defects_model_v1", json.dumps(after))
        self.assertIn("vision_defects_model_v2", json.dumps(after))
        self.assertEqual(len(before["checks"]), len(after["checks"]))

    def test_evolution_retains_failures_from_all_stages(self):
        result = {"parsing_errors": [{"file": "bad.yaml", "error": "invalid YAML"}],
                  "domain_oracle": [{"rule": "S09", "detail": "Contradictory bounds"}],
                  "requirements": [{"id": "registry", "status": "FAIL", "expected": ["v2"], "actual": ["v1"]},
                                   {"id": "region", "status": "PASS", "expected": "westeurope", "actual": "westeurope"}]}
        observed = evolution.failed(result)
        self.assertEqual({r["stage"] for r in observed}, {"parsing", "domain", "requirement"})
        registry = next(r for r in observed if r.get("id") == "registry")
        self.assertEqual(registry["actual"], ["v1"])
        self.assertEqual(registry["expected"], ["v2"])
        self.assertFalse(any(r.get("id") == "region" for r in observed))
        self.assertEqual(evolution.failed({"parsing_errors": [], "domain_oracle": [], "requirements": []}), [])

    def test_retry_preserves_failed_attempt_and_excludes_its_output(self):
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            original, fresh = root / "failed", root / "retry"
            original.mkdir()
            live = root / "live.mlops"
            live.write_text("modelName = v2\n")
            directories = ["baseline-after", "baseline-before", "baseline-source-after", "baseline-source-before",
                           "dsl-before", "dsl-expected-after", "generated-before", "logs-after", "logs-before"]
            for name in directories:
                (original / name).mkdir()
            (original / "dsl-expected-after/c00.mlops").write_text(live.read_text())
            (original / "generated-before/frozen.yaml").write_text("modelName: v1\n")
            for name in ("before-observations.json", "contract-after.json", "contract-before.json"):
                (original / name).write_text("{}")
            (original / "task.json").write_text(json.dumps({"starting_controls_valid": True, "dsl_source": str(live)}))
            summary = original / "summary.json"
            summary.write_text('{"status":"FAIL"}')
            old_hash = common.digest(summary)
            (original / "generated-after").mkdir()
            (original / "generated-after/stale.yaml").write_text("stale: true\n")
            with patch.object(evolution, "evaluate", return_value=0) as evaluate:
                self.assertEqual(evolution.retry(SimpleNamespace(run=original, out=fresh, generated=root / "live-output")), 0)
                self.assertEqual(evaluate.call_args.args[0].run, fresh)
            self.assertEqual(common.digest(summary), old_hash)
            self.assertFalse((fresh / "summary.json").exists())
            self.assertFalse((fresh / "generated-after").exists())
            self.assertEqual((fresh / "generated-before/frozen.yaml").read_text(), "modelName: v1\n")
            self.assertEqual(json.loads((fresh / "task.json").read_text())["previous_summary_sha256"], old_hash)

    def test_retry_refuses_an_existing_destination(self):
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            with self.assertRaisesRegex(ValueError, "destination exists"):
                evolution.retry(SimpleNamespace(run=root / "old", out=root, generated=root / "output"))

    def test_replication_requires_real_external_metamodel(self):
        with tempfile.TemporaryDirectory() as td:
            r = replication.inspect_metamodel(Path(td))
            self.assertEqual(r["status"], "FAIL")
            self.assertEqual(len(r["errors"]), 3)

    def test_replication_rejects_incomplete_junit_xml(self):
        with tempfile.TemporaryDirectory() as td:
            xml = Path(td) / "synthetic-unit-fixture.xml"
            xml.write_text('<testsuite><testcase classname="org.xtext.ucd.mlopsdsl.tests.MlopsDslValidationTest" name="validAzure"/></testsuite>')
            result = replication.inspect_junit(xml)
            self.assertEqual(result["status"], "FAIL")
            self.assertEqual(result["expected_test_count"], 23)
            self.assertEqual(len(result["missing_tests"]), 22)

    def test_replication_skips_state_credentials_and_build_products(self):
        root = Path("/example")
        for name in (".env", "token.key", "terraform.tfstate.backup", "bin/Foo.class", "src-gen/Parser.java", ".terraform/plugin"):
            self.assertFalse(replication.safe_file(root / name, root), name)
        self.assertTrue(replication.safe_file(root / "model/mlops.genmodel", root))
        self.assertTrue(replication.safe_file(root / "META-INF/MANIFEST.MF", root))


if __name__ == "__main__":
    unittest.main()
