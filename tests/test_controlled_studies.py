"""Instrument verification only. Test data are not empirical study results."""
import json
import os
import shutil
import sys
import tempfile
import unittest
from pathlib import Path
from types import SimpleNamespace

sys.path.insert(0, str(Path(__file__).resolve().parents[1] / "tools"))
from controlled_studies import (allocate, block_values, close_result, freeze, inside,
                                locked, save, sha, tree, unintended_changes, verify_export,
                                build_baseline, inspect, passes)
from study_materials import materialise
from study_statistics import bootstrap_mean, paired_summary, plan_power, randomisation_test


class StatisticalDesignTests(unittest.TestCase):
    def test_exact_assignment_probability_uses_blocks(self):
        # Six blocks, two extreme assignments among 64, not 48 tasks.
        r = randomisation_test([1.] * 6)
        self.assertEqual(r["assignments"], 64)
        self.assertEqual(r["p_two_sided"], 2 / 64)

    def test_null_and_direction_are_not_forced_to_favour_dsl(self):
        self.assertEqual(randomisation_test([0., 0., 0.])["p_two_sided"], 1.)
        self.assertLess(paired_summary([-1., -2., -1.])["mean_difference"], 0)
        self.assertEqual(randomisation_test([1., 2., 3.])["p_two_sided"],
                         randomisation_test([-1., -2., -3.])["p_two_sided"])

    def test_bootstrap_is_reproducible(self):
        self.assertEqual(bootstrap_mean([-.1, .2, .3], repetitions=1000),
                         bootstrap_mean([-.1, .2, .3], repetitions=1000))

    def test_repeated_tasks_remain_clustered(self):
        rows = [{"participant_id": p, "block_id": "B001", "approach": a, "value": v}
                for p in ("P001", "P002") for a, v in (("dsl", .2), ("baseline", .5)) for _ in range(4)]
        self.assertAlmostEqual(block_values(rows, "value")[0], .3)
        self.assertEqual(len(block_values(rows, "value")), 1)
        with self.assertRaises(ValueError):
            block_values(rows[:-1], "value")

    def test_power_inputs_are_not_results(self):
        r = plan_power(.1, .2, simulations=20)
        self.assertEqual(r["status"], "ASSUMPTION_BASED_PLANNING_ONLY")
        self.assertTrue(all(row["participants"] == 2 * row["blocks"] for row in r["grid"]))
        with self.assertRaises(ValueError):
            plan_power(0, .2, simulations=20)


class ProvenanceAndScoringTests(unittest.TestCase):
    def setUp(self):
        self.tmp = tempfile.TemporaryDirectory()
        self.root = Path(self.tmp.name)

    def tearDown(self):
        self.tmp.cleanup()

    def test_path_traversal_is_rejected(self):
        with self.assertRaises(ValueError):
            inside(self.root, "../outside")

    def test_export_detects_stale_model_and_changed_artifact(self):
        model = self.root / "model.mlops"; model.write_text("real model placeholder for instrument test")
        out = self.root / "export"; (out / "artifacts").mkdir(parents=True)
        artifact = out / "artifacts/file.yaml"; artifact.write_text("name: test")
        save(out / "receipt.json", {"producer": "MlopsDslStudyExportTest", "source_sha256": sha(model),
             "generator_class_sha256": "a" * 64, "generator": "ACCEPT", "diagnostics": [],
             "artifacts": tree(out / "artifacts")})
        self.assertEqual(verify_export(out, model, "a" * 64)["generator"], "ACCEPT")
        artifact.write_text("name: altered")
        with self.assertRaises(ValueError): verify_export(out, model)
        model.write_text("another model")
        with self.assertRaises(ValueError): verify_export(out, model)

    def test_wrong_generator_cannot_enter_frozen_experiment(self):
        model = self.root / "model.mlops"; model.write_text("instrument test")
        out = self.root / "export"; out.mkdir()
        save(out / "receipt.json", {"producer": "MlopsDslStudyExportTest", "source_sha256": sha(model),
             "generator_class_sha256": "b" * 64, "generator": "REJECT", "diagnostics": ["fault"], "artifacts": {}})
        with self.assertRaises(ValueError): verify_export(out, model, "a" * 64)

    def test_semantic_preservation_ignores_yaml_formatting(self):
        before, after = self.root / "before", self.root / "after"
        for root in (before, after): (root / "runtime/k8s").mkdir(parents=True)
        name = "runtime/k8s/deployment.yaml"
        (before / name).write_text("spec: {replicas: 2, preserved: yes}\n")
        (after / name).write_text("spec:\n  preserved: true\n  replicas: 5\n")
        a = {"checks": [{"id": "replicas", "kind": "yaml", "file": name, "path": ["spec", "replicas"], "expected": [2]}]}
        b = json.loads(json.dumps(a)); b["checks"][0]["expected"] = [5]
        self.assertEqual(unintended_changes(before, after, a, b), [])
        (after / name).write_text("spec: {replicas: 5, preserved: false}\n")
        self.assertEqual(unintended_changes(before, after, a, b)[0]["path"], ["spec", "preserved"])

    def test_absent_and_explicit_null_are_distinguished(self):
        before, after = self.root / "before", self.root / "after"
        for root in (before, after): (root / "mlops").mkdir(parents=True)
        (before / "mlops/test.yaml").write_text("a: null\n")
        (after / "mlops/test.yaml").write_text("{}\n")
        self.assertTrue(unintended_changes(before, after, {"checks": []}, {"checks": []}))

    def test_abandonment_receives_full_cap_and_is_preserved(self):
        save(self.root / "session.json", {"limit_seconds": 900})
        close_result(self.root, "abandoned", 30)
        r = json.loads((self.root / "result.json").read_text())
        self.assertEqual(r["normalised_capped_time"], 1)
        self.assertFalse(r["excluded"])
        with self.assertRaises(ValueError): close_result(self.root, "abandoned", 40)


class MaterialAndAllocationTests(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.tmp = tempfile.TemporaryDirectory()
        cls.root = Path(cls.tmp.name)
        cls.materials = cls.root / "materials"
        cls.preparation = materialise(cls.materials)

    @classmethod
    def tearDownClass(cls):
        cls.tmp.cleanup()

    def test_materials_do_not_fake_generation_or_independent_cases(self):
        self.assertEqual(self.preparation["xtext_jobs"], 39)
        manifest = json.loads((self.materials / "manifest.json").read_text())
        self.assertTrue(all(i["development"] for i in manifest["items"]))
        self.assertFalse(list(self.materials.rglob("receipt.json")))

    def test_replica_and_port_contracts_actually_change(self):
        for provider in ("azure", "gcp"):
            for family in ("port", "replicas"):
                folder = self.materials / f"{provider}-{family}-A"
                a = json.loads((folder / "before/contract.json").read_text())
                b = json.loads((folder / "after/contract.json").read_text())
                differences = sum(x != y for x, y in zip(a["checks"], b["checks"]))
                self.assertEqual(differences, 6 if family == "port" else 1)

    def test_frozen_plan_and_balanced_allocation(self):
        config = {"mode": "pilot", "participants": 8, "task_limit_seconds": 900,
                  "seed": 1847, "generator_class_sha256": "a" * 64}
        save(self.root / "config.json", config)
        plan = self.root / "plan"
        freeze(SimpleNamespace(config=self.root / "config.json", materials=self.materials, out=plan))
        roster = self.root / "roster.csv"
        roster.write_text("participant_id,stratum\n" + "".join(f"P{i:03},intermediate\n" for i in range(1, 9)))
        allocation = self.root / "allocation"
        allocate(SimpleNamespace(plan=plan, roster=roster, out=allocation))
        rows = json.loads((allocation / "allocation.json").read_text())["rows"]
        self.assertEqual(len(rows), 64)
        for block in {r["block_id"] for r in rows}:
            starts = [r["approach"] for r in rows if r["block_id"] == block and r["slot"] == 1]
            self.assertEqual(sorted(starts), ["baseline", "dsl"])
        config["seed"] = 0; save(plan / "config.json", config)
        with self.assertRaises(ValueError): locked(plan)

    def test_unreviewed_confirmatory_freeze_is_blocked(self):
        config = {"mode": "confirmatory", "participants": 8, "task_limit_seconds": 900,
                  "seed": 1847, "generator_class_sha256": "a" * 64}
        save(self.root / "confirmatory.json", config)
        with self.assertRaises(ValueError):
            freeze(SimpleNamespace(config=self.root / "confirmatory.json", materials=self.materials,
                                   out=self.root / "unreviewed"))

    def test_actual_kustomize_all_new_evolution_materials(self):
        kubectl = os.environ.get("MLOPS_TEST_KUBECTL") or shutil.which("kubectl")
        if not kubectl:
            self.skipTest("kubectl required for real task-material integration")
        with tempfile.TemporaryDirectory() as temp:
            manifest = json.loads((self.materials / "manifest.json").read_text())
            for item in manifest["items"]:
                if item["kind"] != "evolution":
                    continue
                folder = self.materials / item["directory"]
                for phase in ("before", "after"):
                    dest = Path(temp) / item["id"] / phase
                    self.assertEqual(build_baseline(folder / phase / "baseline", dest, kubectl), "PASS")
                    result = inspect(item["provider"], dest / "artifacts",
                                     json.loads((folder / phase / "contract.json").read_text()))
                    self.assertTrue(passes(result), (item["id"], phase, result))
                self.assertEqual(unintended_changes(Path(temp) / item["id"] / "before/artifacts",
                    Path(temp) / item["id"] / "after/artifacts",
                    json.loads((folder / "before/contract.json").read_text()),
                    json.loads((folder / "after/contract.json").read_text())), [], item["id"])


if __name__ == "__main__":
    unittest.main()
