# Validation suite: expectations, not completed experimental results

This suite contains 12 negative cases (11 semantic and one linking case), four valid controls, and two additional parser/scoping regression tests. The number was selected to cover the implemented requirements and boundary behavior; it is not a journal acceptance threshold.

## Files

- `mlops-tests/src/validation/`: canonical standalone DSL cases for the runtime editor.
- `scenarios.tsv`: expected outcome, fault category, intended diagnostic fragment, mutation description and SHA-256.
- `org.xtext.ucd.mlopsdsl.tests/src/validation/`: identical classpath fixtures for the JUnit tests, including the manifest.
- `MlopsDslValidationTest.java`: 16 case tests plus explicit-false and resource-load-order tests. These use the real parser, Xtext resource validator and direct `MlopsGenerator`; they do not mock DSL model values.
- `tools/prepare_validation_suite.py`: reproduces fixtures from the Azure example. `--check` only audits fixture consistency; it does not run Xtext.
- `results-template.csv`: empty actual observations. Nothing is marked passed in advance.

S01-S11 cover split total, GPU resources, TLS, API port, cloud region, public domain, node counts, decision weights, criterion bounds, reference linking and execution command. S12 isolates a zero split ratio while keeping the split total equal to one. C00 is the valid Azure control; C01 permits private exposure without TLS; C02 and C03 exercise valid port boundaries 1 and 65535.

Each negative case is derived independently from the valid baseline. No mutation is applied on top of another negative case. Case-specific project names prevent output-directory collisions. Local scoping is required because fixtures deliberately reuse declaration names such as `defects_ds_v1`.

## Pull and synchronize with the original Eclipse workspace

Save all Eclipse editor changes and close the running runtime Eclipse before synchronizing. In PowerShell, from this repository:

```powershell
git fetch origin
git switch --track origin/evaluation/validation-suite
& .\tools\Sync-EclipseWorkspace.ps1 -WorkspacePath "C:\Users\bader\eclipse-workspace\modelling"
```

If already on that branch, use `git pull --ff-only`. The sync script backs up overwritten source files in the Windows temporary directory. It updates only the listed source files and new fixture files, not generated parser files or existing examples.

In the main development Eclipse:

1. Refresh `org.xtext.ucd.mlopsdsl`, `org.xtext.ucd.mlopsdsl.tests` and `mlops-tests` with F5.
2. Run `GenerateMlopsDsl.mwe2` as MWE2 Workflow. Require successful completion.
3. Clean/build the projects and resolve compilation errors before interpreting results.
4. Run `MlopsDslValidationTest.java` as JUnit 5 (or JUnit Plug-in Test if required by the target platform).
5. Export the JUnit report and save console output, including each `VALIDATION` line. A test failure or build failure must not be recorded as a successful fault detection.
6. Launch a fresh runtime Eclipse and inspect files under `mlops-tests/src/validation`. Valid controls should have no error markers; each negative should show the intended error. Existing unrelated examples may retain their own errors: the assertions scope validation to each fixture resource.

For invalid cases, the automated test requires the intended editor error, rejects unrelated error diagnostics, then requires an exception from direct generation with an empty in-memory output. Valid controls must validate and generate nonempty output. The wrapper `MlopsDslGenerator.xtend` catches exceptions and writes an error report; these tests deliberately exercise the direct generator. Old files in `src-gen` are not evidence of a new generation success.

## Environment and limitations

The repository declares JavaSE-21 and Xtext 2.40.0. It also requires the external `meta-model` bundle and references `platform:/resource/meta-model/model/mlops.genmodel`. That dependency is currently not included among the six repository projects. Keep it available in the original Eclipse workspace. A standalone replication package must supply that dependency or a reproducible artifact for it, including its OCL registration.

These tests are authored but have not been compiled or run in the author's Eclipse target platform by this change. Do not convert fixture audits into reported runtime results. Passing this suite verifies selected configuration rules only; it does not demonstrate deployment feasibility, full ML pipeline execution, generalized productivity gains or journal acceptance.

## Record actual results

Copy `results-template.csv` to a dated raw-data file and fill it from logs. Preserve `not_run`, errors and false negatives. Record the commit, target platform, Java version, OS, execution date and log filenames. Keep semantic and linking outcomes separate. The linking case must not inflate claims about added semantic validation.

Report semantic detection recall using only the 11 seeded semantic cases and report false alarms over the four valid controls. State that this is a small, purposive suite; do not generalize the rate beyond the tested cases. Report generation blocking separately from editor diagnostics.
