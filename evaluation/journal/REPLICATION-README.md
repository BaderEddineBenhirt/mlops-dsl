# MLOps DSL replication material

## Research scope

This archive contains two distinct configuration evaluations:

1. **C00 — Azure vision configuration:** selected configuration validation/generation, paired fault detection and controlled configuration evolution.
2. **C10 — GCP tabular fraud-service configuration:** a separate evaluation of selected configuration requirements, fault detection and controlled evolution.

These are separate configuration cases, not a cross-cloud equivalence experiment. The GCP case is synthetic and does not constitute an independent industrial application or practitioner evaluation.

The recorded experiments do not establish cloud deployment, live inference, ML lifecycle execution, human productivity superiority or general maintainability superiority.

## Start here

### Azure C00 evidence

- `evidence/junit.xml`: original author-exported 23-test JUnit result.
- `evidence/eclipse-observations/`: 16 real fixture observations, source hashes and generated C00 output from the author run.
- `evaluation/journal/runs/author-20261107-144731-354/`: broad comparison, paired fault evaluation, maintained-source inventory and native Terraform logs.
- `evaluation/journal/runs/registry-author-01-retry-02/`, `exposure-author-01/`, `port-author-01/`: successful frozen evolution trials. Earlier failed registry attempts are retained; they are not independent cases.
- `evaluation/journal/author-results-20261107.md`: Azure interpretation and limitations.
- `evaluation/journal/replication-audit-20261107.md`: independent static replay findings for the first uploaded draft. Its ZIP hash identifies that draft, not a later rebuilt archive.

### GCP C10 evidence

- `evaluation/journal/gcp/runs/author-20261108-164641-995/`: complete Windows author-run evidence.
- `evaluation/journal/gcp/runs/author-20261108-164641-995/report.json`: recorded GCP evaluation results and summary.
- `evaluation/journal/gcp/runs/author-20261108-164641-995/eclipse-observations/`: hash-checked source observations and generated outputs.
- `evaluation/journal/gcp/runs/author-20261108-164641-995/environment/gcp-junit.xml`: separate GCP JUnit export.
- `evaluation/journal/gcp/runs/author-20261108-164641-995.zip`: byte-preserving author-run evidence archive. SHA-256: `5638f74f990f8c6c7eb72ab2bbc1b0fb43fed4bc457fe7ea97c67ab691d80be7`.
- `evaluation/journal/gcp/README.md`: GCP case definition, execution instructions, comparison scope and limitations.
- `evaluation/journal/gcp/author-results-20261108.md`: Windows author-run results and evidence provenance.
- `evaluation/journal/gcp/agent-results-20261108.md`: separate headless Linux pilot. Its native validation was blocked by provider startup restrictions; it must not be confused with the successful Windows author run.

### Package integrity

- `evidence/manifest.json`: SHA-256 file inventory.
- `evidence/preflight.json`: collection status.
- `evidence/environment/`: recorded CLI version logs.
- `evidence/eclipse-bundles.info`: author Eclipse bundle inventory.

## Recorded environment

| Component | Recorded version |
|---|---|
| Python | 3.12.0 |
| Eclipse platform | 4.37.0.v20250905-0730 |
| Xtext / Xtend | 2.40.0.v20250825-0355 |
| EMF Common / Ecore | 2.44.0.v20251025-0946 / 2.41.0.v20251025-0946 |
| OCL Pivot / examples.codegen | 1.22.0.v20240902-1518 / 2.22.0.v20240902-1518 |
| Java running the exported Azure JUnit suite | 21.0.8 |
| Java on the author's command-line PATH | 21.0.5 |
| Terraform | 1.14.9 |
| AzureRM provider (C00) | 3.117.1 |
| Google provider (C10) | 5.45.2 |
| kubectl / bundled Kustomize | 1.35.4 / 5.7.1 |

The Eclipse and Java information describes the author's recorded installation. Use `evidence/eclipse-bundles.info` for the full installed bundle inventory.

The GCP execution also records its Windows environment and CLI logs in its own run directory.

The bundle inventory is not an automated target-platform installer. Python dependencies and provider locks are included. Exact Eclipse setup remains manual; a fresh independent Eclipse build was not run by the archive auditor.

The author-exported JUnit results and static replays are distinct forms of evidence.

## Recheck the archived configuration observations

Unzip the archive into a working directory. Create a Python 3.12 virtual environment and activate it using your operating system's activation command.

Install the dependencies:

```text
python -m pip install -r evaluation/journal/requirements.txt
```

Run the tooling tests:

```text
python -m unittest discover -s tests -p "test_journal_*.py" -v
```

### Azure C00 replay

```text
python tools/run_journal_comparison.py --generated evidence/eclipse-observations/generated-c00 --out reviewer-runs/c00-01 --terraform terraform

python tools/run_fault_comparison.py --dsl-observations evidence/eclipse-observations/dsl-observations.json --out reviewer-runs/faults-01 --terraform terraform
```

### GCP C10 replay

Use the preserved GCP Eclipse observations:

```text
python tools/run_gcp_comparison.py --observations evaluation/journal/gcp/runs/author-20261108-164641-995/eclipse-observations --out reviewer-runs/gcp-01 --terraform terraform --kubectl kubectl
```

These commands use the recorded source and generated-output observations. They do not independently rerun the Xtext parser or reproduce the original Eclipse generation process.

Install the recorded Terraform and kubectl clients before native checks; both must be available on PATH. Terraform initialization may download its pinned provider. For GCP, the Google provider is pinned to 5.45.2.

These commands do not perform Terraform plan/apply, authenticate to GCP or provision cloud resources.

Use fresh output directories for retries. Python tests include synthetic collector fixtures; these are tooling QA, not additional DSL experiments.

## Regenerate with Eclipse

Use an installation matching the recorded bundles and Java 21.0.8 for the IDE launch.

Import `meta-model` plus the six `org.xtext.ucd.mlopsdsl*` / `mlops-tests` projects from this working copy.

The metamodel includes Ecore, GenModel and generated Java sources. If regenerating that Java code, generate it from `meta-model/model/mlops.genmodel` first.

Then run:

```text
org.xtext.ucd.mlopsdsl/src/org/xtext/ucd/mlopsdsl/GenerateMlopsDsl.mwe2
```

Clean/build the projects before running the tests.

### Azure test

Run `MlopsDslValidationTest` with JUnit 5 and retain the original Azure JUnit XML separately.

### GCP test

Run:

```text
org.xtext.ucd.mlopsdsl.tests/src/org/xtext/ucd/mlopsdsl/tests/MlopsDslGcpEvaluationTest.java
```

Use JUnit 5.

The GCP suite contains two test methods and exports 14 actual configuration observations:

- Four valid control/boundary configurations.
- Seven semantic fault configurations.
- Three configuration evolution variants.

Copy the `GCP OBSERVATIONS:` folder printed in the Eclipse console. Export the GCP JUnit XML separately; do not replace the original Azure JUnit result.

Run the GCP comparison using:

```text
powershell -NoProfile -File ".\tools\Run-GcpEvaluation.ps1" -ObservationsFolder "PATH_TO_GCP_OBSERVATIONS"
```

For editor-driven generation, launch a fresh runtime Eclipse and import the working `mlops-tests` project there. Verify its project Location, enable automatic building and save the model.

Check output freshness before evaluating an evolution. Operate on a working copy; the archived trial snapshots remain the observations.

Detailed preparation and retry instructions are available in:

- `evaluation/journal/execution.md`
- `evaluation/journal/gcp/README.md`

## Interpretation and limitations

### Azure C00

The Azure common contract contains 79 selected checks.

Native validators and the study-specific domain guard are reported separately.

Dataset splits, training GPU allocation, workflow-link identities and serving commands are outside the paired emitted baseline; inapplicable cases are not misses.

Whole-DSL and baseline source-size totals have unequal scope and do not justify a reduction ratio.

Registry's raw output difference includes a dependency-lock removal; exposure source-line counts include formatting by the preparation harness.

### GCP C10

The GCP contract contains 96 selected properties for the primary configuration. The private control has 95 applicable checks because public TLS-specific checks are replaced by a private-exposure check.

The recorded Windows author-run results are:

| Evaluation | DSL | Study-authored baseline |
|---|---:|---:|
| GC00 selected requirements | 96/96 | 96/96 |
| GC01 private control | 95/95 | 95/95 |
| GC02 port boundary | 96/96 | 96/96 |
| GC03 port boundary | 96/96 | 96/96 |
| GE01 registry evolution | 96/96 | 96/96 |
| GE02 port evolution | 96/96 | 96/96 |
| GE03 replica evolution | 96/96 | 96/96 |
| Matched semantic faults | 7/7 detected | 7/7 detected |
| Valid controls | 4/4 accepted | 4/4 accepted |

Both approaches recorded zero false alarms on the valid control cases.

The native-stage checks detected one of the seven selected faults and missed six. The missing API port was detected by Kustomize. Blank region and incoherent node-count configurations passed native Terraform validation despite violating study-specific domain rules.

Kubernetes API schema validation and native lifecycle policy-engine validation were not executed. These results must not be interpreted as a general ranking of validators.

The three evolution tasks concern registry naming, API port and serving replicas. They were mechanical paired changes, not timed human authoring tasks. File counts do not establish lower editing effort or general maintainability advantages.

The GCP baseline was study-authored with assistant support, not independently authored by a practitioner. Its scope equivalence has not been independently reviewed.

The GCP case is synthetic and does not include a real fraud dataset, model training, live inference, cloud provisioning or production deployment. The 14 configurations derive from one application case; they are not 14 independent applications.

### Cross-case interpretation

The Azure and GCP evaluations have different application contexts, configuration structures and selected contracts.

Their results must therefore remain separate. They do not establish cross-cloud functional equivalence, provider-independent behavior or general superiority over conventional configuration authoring.

No productivity, maintainability or deployment-performance conclusions should be inferred from the recorded results.

## Publication and reviewer access

Reviewer access remains unverified until a versioned archive is published and downloaded without signing in.

The final publication must identify the exact replication ZIP, its SHA-256 checksum and the corresponding release/version.

Citation and release metadata must refer to that final artifact. Any claim of functional equivalence must remain conditional on the scope and independent review limitations documented above.