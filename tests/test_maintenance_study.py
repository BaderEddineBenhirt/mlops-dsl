"""Instrument checks. Synthetic fixtures are not actual generator observations."""
import json
import os
from pathlib import Path
import shutil
import sys
import tempfile
from types import SimpleNamespace
import unittest
from unittest.mock import patch

sys.path.insert(0, str(Path(__file__).resolve().parents[1] / "tools"))
import maintenance_study as study
from study_materials import materialise
from controlled_studies import build_baseline, inspect, passes


class EditModelTests(unittest.TestCase):
    def test_repeated_references_are_separate_units(self):
        a = 'model = "name_v1"\nref = "name_v1"\n'
        b = a.replace("name_v1", "name_v2")
        units = study.edit_units(a, b)
        self.assertEqual(len(units), 2)
        self.assertEqual(study.apply_units(a, units), b)
        partial = study.apply_units(a, units[:1])
        self.assertEqual(partial.count("name_v1"), 1)
        self.assertEqual(partial.count("name_v2"), 1)

    def test_noop_comments_and_layout_are_not_edit_units(self):
        a = 'port = 8080 // explanation\nurl = "https://example.org/#anchor"\n'
        b = 'port=8080 // another explanation\n\nurl="https://example.org/#anchor"\n'
        self.assertEqual(study.edit_units(a, b), [])
        self.assertEqual(len(study.edit_units(a, a.replace("8080", "9090"))), 1)

    def test_exact_enumeration_includes_each_endpoint_once(self):
        values = list(study.subsets(6))
        self.assertEqual(len(values), 64)
        self.assertEqual(len(set(values)), 64)
        self.assertEqual(values[0], ())
        self.assertEqual(values[-1], tuple(range(6)))

    def test_stale_edit_and_unsupported_insert_are_rejected(self):
        units = study.edit_units("port = 8080", "port = 9090")
        with self.assertRaises(ValueError): study.apply_units("port = 7070", units)
        with self.assertRaises(ValueError): study.edit_units("port = 8080", "port = 8080 replicas = 3")

    def test_selection_does_not_mutate_the_control(self):
        with tempfile.TemporaryDirectory() as d:
            root = Path(d); before = root / "before"; before.mkdir()
            text = 'name="x_v1"\nother="x_v1"\n'
            (before / "model.mlops").write_bytes(text.encode())
            units = [{"file": "model.mlops", **u} for u in study.edit_units(text, text.replace("v1", "v2"))]
            study.source_for_subset(before, root / "partial", "dsl", units, (1,))
            self.assertEqual((before / "model.mlops").read_bytes(), text.encode())
            self.assertEqual((root / "partial/model.mlops").read_text().count("x_v2"), 1)

    def test_finite_summary_does_not_invent_a_human_effect(self):
        task = {"id": "case-port-A", "family": "port"}
        rows = [{"task_id": task["id"], "approach": a, "project_id": "C00", "family": "port",
                 "unit_count": 1, "selected": selection, "files_edited": len(selection),
                 "phase": "zero_control" if not selection else "full_control",
                 "state": "ACCEPTED_INCOMPLETE_TARGET" if not selection else "ACCEPTED_CORRECT_TARGET"}
                for a in ("dsl", "baseline") for selection in ([], [0])]
        summary = study.summarise({"tasks": [task]}, rows)
        self.assertEqual(summary["status"], "COMPLETE_FINITE_BENCHMARK")
        self.assertEqual(summary["paired_contrasts"][0]["baseline_minus_dsl"], 0)
        self.assertIn("No p-values", summary["inference"])
        rows[-1]["state"] = "ACCEPTED_INCOMPLETE_TARGET"
        self.assertEqual(study.summarise({"tasks": [task]}, rows)["status"], "CONTROL_FAILURE_REQUIRES_INVESTIGATION")


class MaintenanceMaterialTests(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.temp = tempfile.TemporaryDirectory()
        cls.root = Path(cls.temp.name)
        cls.materials = cls.root / "materials"
        materialise(cls.materials)

    @classmethod
    def tearDownClass(cls):
        cls.temp.cleanup()

    def test_actual_workload_sizes_and_scalar_reconstruction(self):
        manifest = json.loads((self.materials / "manifest.json").read_text())
        counts = {"dsl": 0, "baseline": 0}
        for item in manifest["items"]:
            if item["kind"] != "evolution": continue
            f = self.materials / item["directory"]
            for approach in counts:
                units = study.units_in_sources(f / "before", f / "after", approach)
                counts[approach] += 2 ** len(units)
        self.assertEqual(counts, {"dsl": 168, "baseline": 44})

    def test_prepare_refuses_missing_real_generator_exports(self):
        with self.assertRaises(OSError):
            study.prepare(SimpleNamespace(materials=self.materials, out=self.root / "missing", max_units=8))
        self.assertFalse((self.root / "missing").exists())

    def test_exact_fixture_enumeration_and_lock_integrity(self):
        # Test only orchestration with a labelled fake exporter. No DSL output is
        # produced or reported as an observed result by this fixture.
        original = json.loads((self.materials / "manifest.json").read_text())
        one = {**original, "items": [r for r in original["items"] if r["id"] == "gcp-registry-A"]}
        with tempfile.TemporaryDirectory() as d:
            root = Path(d); src = root / "materials"; src.mkdir()
            study.save(src / "manifest.json", one)
            task = one["items"][0]
            shutil.copytree(self.materials / task["directory"], src / task["directory"])
            for phase in ("before", "after"):
                folder = src / task["directory"] / phase / "dsl-export/artifacts"
                folder.mkdir(parents=True)
                (folder / "qa-only.txt").write_text("Synthetic instrument fixture, not DSL generation.")
            with patch.object(study, "verify_export", return_value={"generator": "ACCEPT", "diagnostics": [], "generator_class_sha256": "a" * 64}), \
                 patch.object(study, "inspect", return_value={"parsing_errors": [], "domain_issues": [], "failed_requirements": []}):
                prepared = study.prepare(SimpleNamespace(materials=src, out=root / "study", max_units=8))
            self.assertEqual(prepared["xtext_exports_required"], 8)
            self.assertEqual(prepared["scenarios"], 12)
            study.verify_lock(root / "study")
            target = root / "study/cases/gcp-registry-A-dsl-none/model.mlops"
            target.write_bytes(target.read_bytes() + b"\n// changed after freeze\n")
            with self.assertRaises(ValueError): study.verify_lock(root / "study")

    def test_real_kustomize_can_make_a_redundant_edit_unnecessary(self):
        kubectl = os.environ.get("MLOPS_TEST_KUBECTL") or shutil.which("kubectl")
        if not kubectl: self.skipTest("Actual Kustomize required")
        task = self.materials / "gcp-domain-A"
        units = study.units_in_sources(task / "before", task / "after", "baseline")
        self.assertEqual(len(units), 2)
        correct = []
        with tempfile.TemporaryDirectory() as d:
            root = Path(d)
            for index in range(2):
                partial = root / f"partial-{index}"
                study.source_for_subset(task / "before", partial, "baseline", units, (index,))
                out = root / f"build-{index}"
                self.assertEqual(build_baseline(partial / "baseline", out, kubectl), "PASS")
                correct.append(passes(inspect("gcp", out / "artifacts", study.read(task / "after/contract.json"))))
        self.assertEqual(sorted(correct), [False, True])


if __name__ == "__main__":
    unittest.main()
