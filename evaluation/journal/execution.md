# Execute the five evaluation workstreams

These tools collect evidence; merging them does not complete the experiments.
No authoring times, acceptance probabilities or human-study observations are
invented. The Azure pilot is one case, with equivalence review still pending.

## 1. Synchronize Eclipse and export real DSL observations

In the repository PowerShell:

```powershell
& .\tools\Sync-EclipseWorkspace.ps1
& .\.venv-evaluation\Scripts\python.exe -m pip install -r evaluation\journal\requirements.txt
```

Refresh the three Eclipse projects, regenerate the MWE2 workflow, clean/build,
and run `MlopsDslValidationTest` with JUnit 5. This class now has **23 methods**:
the previous 20, two renderer regressions, and one actual-observation exporter.
The exporter executes all 16 fixtures through the parser, editor validator and
generator. It prints `JOURNAL OBSERVATIONS: <absolute directory>`, containing
`dsl-observations.json` and fresh `generated-c00/` artifacts. Its default folder
is under the test project's `target/`; it does not replace the runtime workspace
output. An optional JVM property `-Dmlops.evaluation.output=<new absolute path>`
sets a destination. Existing destinations are rejected.

Export JUnit XML from the **JUnit view's Export Test Run** action. Console text
or screenshots do not substitute for the XML. Keep the XML even when tests fail.
The replication preflight checks all 23 named tests, failures/errors/skips,
all 16 actual fixture observations and their source hashes.

The actual external `meta-model` bundle is still required. It must supply
`mlops.MlopsPackage`, Ecore namespace `http://ucd.ac.ma/mlops`, and
`model/mlops.genmodel`. Do not replace it with the generated `MlopsDsl.ecore`.

## 2. Broader comparison, faults and source size

Use the actual directory printed by the new test exporter:

```powershell
& .\tools\Run-JournalEvaluation.ps1 -ObservationsFolder "C:\FULL\PATH\journal-observations-..."
```

This preserves a new `evaluation/journal/runs/author-<timestamp>/` with:

| Evidence | Output | Meaning |
| --- | --- | --- |
| Broader comparison | `broader/report.json` and raw CLI logs | 79 selected requirements across runtime, Azure infrastructure and nine lifecycle policies |
| Fault detection | `faults/fault-pairs.tsv`, `report.json`, source mutations, diagnostics and CLI output | Baseline faults executed, actual DSL observations hash-checked and joined |
| Specification size | `source-size.json` plus `authoritative-baseline/` | File counts, physical-line and lexical-token proxies over maintained inputs |

`terraform init -backend=false` and `terraform validate -json` are attempted.
No plan/apply or cloud deployment runs. Provider download/start failures are
BLOCKED rather than configuration detections. Missing executables are recorded.
The Terraform `.terraform.lock.hcl` pins Azure provider 3.117.1. Python packages
are pinned in `requirements.txt`; reference/actual environments are distinct.
The baseline accepts Terraform CLI `>= 1.13.0, < 1.15.0`, including the author's
observed 1.14.9. Run both approaches with the same exact CLI version and retain
its version log. Earlier runs blocked by `= 1.13.0` must be preserved; pull the
fix and run the evaluation wrapper again to create a fresh observation directory.
Eclipse regeneration is not required for this evaluation-baseline change.

Faults start from a fresh valid baseline control each time, mutate maintained
Terraform/YAML sources, then rerender with real Kustomize. S04 can be detected
by a missing replacement-source field. The study's Python domain guard checks
other selected rules and is **not** labelled as native Terraform/Kubernetes
validation. Native Kustomize/parsing observations and optional native Terraform
results are separate in `native_validation`. Kubernetes API schema checks are
not implemented by a YAML parser; report them as unexecuted.

Five original faults (S01/S02/S10/S11/S12) have no equivalent emitted feature
in this baseline. They are NOT_APPLICABLE with explicit reasons, never misses.
Seven semantic faults and four valid controls remain eligible for a matched
comparison. Use `matched_semantic_and_control_comparison` for equal-denominator
recall and false-alarm rates; do not compare unequal all-DSL/all-baseline rates.
Linking results stay separate from semantic recall. A run without real DSL
JSON honestly reports zero executed pairs.

Source size excludes generated artifacts and counts **one DSL file versus the
maintained Terraform/policies plus five Kustomize source files**. The DSL also
contains additional features. Until a practitioner approves an equivalent-scope
mapping, the report is descriptive and does not calculate a reduction ratio.
Physical-line counts depend on formatting; the regex token proxy is not an AST
metric. Neither is a measure of editing time or maintainability.

## 3. Configuration evolution: three independent changes

The unified `tools/run_configuration_evolution.py` now supports all three tasks.
The original `tools/run_port_evolution.py` and
`evaluation/comparison/port-evolution.md` remain historical runtime-pilot tooling.
Each task starts from its own valid model and **fresh actual generated output**.
Restore the original model and regenerate between tasks; do not chain them.

For E01, start from original C00 PUBLIC/tls=true, registry v1 and port 8080.
Use `prepare --task port` with the same `--dsl`, `--generated` and a new `--run`
shown below. Once starting controls are valid, change only ServingSpec.port to
9090, save/regenerate and run `evaluate`. Both source representations change one
authoritative port declaration; Kustomize is allowed to propagate its value.
All six dependent ports and the other broader postconditions are checked. This
task freezes the actual current output rather than using the old TLS snapshot.

For E02 (registry name v1 to v2), use the original C00 model:

```powershell
$dsl = "C:\Users\bader\eclipse-workspace\modelling\mlops-tests\src\validation\c00_valid_azure.mlops"
$generated = "C:\Users\bader\eclipse-workspace\modelling\mlops-tests\src-gen\generated\validation-c00"
$trial = "evaluation\journal\runs\registry-" + (Get-Date -Format "yyyyMMdd-HHmmss-fff")
& .\.venv-evaluation\Scripts\python.exe tools\run_configuration_evolution.py prepare --task registry --dsl $dsl --generated $generated --run $trial
```

The prepared directory includes `dsl-expected-after/c00.mlops`. Make exactly
that change in runtime Eclipse (all six explicit v1 model-name references to
v2), save, and allow regeneration. Then:

```powershell
& .\.venv-evaluation\Scripts\python.exe tools\run_configuration_evolution.py evaluate --run $trial --generated $generated
```

E03 uses `prepare --task exposure` with the same arguments but a new trial
directory. First restore C00 and set **exposure=PRIVATE, tls=false**, save and
regenerate that valid starting control. Preparation checks it and prescribes
the change to **PUBLIC, tls=true**, preserving the domain. Apply only that
prepared change in Eclipse, regenerate, and run `evaluate`.

The six DSL references include environment/workflow declarations outside the
selected emitted-policy checks; this limits equal-scope editing comparisons.
Outputs include before/after raw artifacts, exact source diffs and hunk/line
counts, author-controlled files touched, mechanically changed output files and
remaining failed postconditions. Baseline edits are mechanical and disclosed;
they are not participant timing data. Invalid starting controls block a trial.
Stale generation must fail the postconditions. Preserve failed trials.

When a failed trial checked stale output, keep the evolved source unchanged and
regenerate with a fresh runtime Eclipse launched from the rebuilt DSL. Verify
the runtime project's Location is the source workspace expected by the task;
refresh it and enable automatic building before saving the model. Then retry:

```powershell
& .\.venv-evaluation\Scripts\python.exe tools\run_configuration_evolution.py retry --run evaluation\journal\runs\registry-author-01 --generated $generated --out evaluation\journal\runs\registry-author-01-retry-01
```

The new directory reuses the recorded valid starting models, artifacts and
contracts, records the previous result's hash, and evaluates only the new output.
The failed attempt is retained. A retry is another attempt of the same task,
not another independent experiment, and must not increase the case count.
Failure summaries now retain parsing errors, domain issues and every failed
requirement together rather than hiding later categories.

## 4. Collect the real replication dependency and draft archive

After the actual Eclipse tests, collect outside the Git repository:

```powershell
& .\.venv-evaluation\Scripts\python.exe tools\build_replication_package.py `
  --metamodel "C:\Users\bader\eclipse-workspace\modelling\meta-model" `
  --junit "C:\FULL\PATH\exported-junit.xml" `
  --observations "C:\FULL\PATH\journal-observations-..." `
  --evidence "C:\FULL\PATH\author-comparison-run" `
  --out ("C:\Users\bader\projects\mlops-replication-" + (Get-Date -Format "yyyyMMdd-HHmmss-fff"))
```

Repeat `--evidence` for each real evolution trial. If available, add
`--bundles-info "<Eclipse installation>\configuration\org.eclipse.equinox.simpleconfigurator\bundles.info"`
to freeze the actual Eclipse/EMF/OCL bundle inventory. Source, Ecore/GenModel,
JUnit XML, raw observations, dependency locks, environment command logs and
SHA-256 inventory are collected. State, keys, environment secrets and build
products are excluded. Generate the EMF model Java sources from the included
GenModel before MWE2 when restoring a clean workspace.

Missing dependency/XML/observations produce `preflight.json` and **no ZIP**.
A collected ZIP remains **DRAFT_NOT_PUBLISHED**, with reviewer access unverified.
Upload an immutable versioned archive through an approved public release or
repository and verify its download without signing in before calling the
replication package reviewer-accessible. A private GitHub branch is insufficient.

## 5. Claims and open gates

Use actual archived sample sizes and report failures, ties and exclusions.
The old corrected-TLS snapshot still fails the new policy/network requirements;
renderer regressions must pass and fresh artifacts must be exported before
claiming those defects fixed in an executed generation run. Python runner QA
does not establish that the Eclipse suite passes. Multi-cloud generalization,
real cloud deployment, executed ML lifecycle and human effort savings require
additional evidence if claimed in the paper. See `scope-mapping.tsv` and
`equivalence-review.md` for independent review.
