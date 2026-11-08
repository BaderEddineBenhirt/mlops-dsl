# MLOps DSL replication material

Research scope: selected configuration validation/generation and controlled
configuration evolution for the C00 Azure case. This research archive contains the implementation and recorded evidence. The recorded experiments do not establish deployment, inference, ML
lifecycle execution, human productivity or general maintainability superiority.

## Start here

- `evidence/junit.xml`: original author-exported 23-test JUnit result.
- `evidence/eclipse-observations/`: 16 real fixture observations, source hashes
  and generated C00 output from the author run.
- `evaluation/journal/runs/author-20261107-144731-354/`: successful broad comparison,
  paired faults, maintained-source inventory and native Terraform logs.
- `evaluation/journal/runs/registry-author-01-retry-02/`, `exposure-author-01/`,
  `port-author-01/`: successful frozen evolution trials. Earlier failed registry
  attempts are retained; they are not independent cases.
- `evaluation/journal/author-results-20261107.md`: interpretation and limitations.
- `evaluation/journal/replication-audit-20261107.md`: independent static replay
  findings for the first uploaded draft. Its ZIP hash identifies that draft,
  not a later rebuilt archive.
- `evidence/manifest.json`: SHA-256 file inventory; `evidence/preflight.json`:
  collection status; `evidence/environment/`: actual CLI version logs.

## Recorded environment

| Component | Recorded version |
| --- | --- |
| Python | 3.12.0 |
| Eclipse platform | 4.37.0.v20250905-0730 |
| Xtext / Xtend | 2.40.0.v20250825-0355 |
| EMF Common / Ecore | 2.44.0.v20251025-0946 / 2.41.0.v20251025-0946 |
| OCL Pivot / examples.codegen | 1.22.0.v20240902-1518 / 2.22.0.v20240902-1518 |
| Java running the exported JUnit suite | 21.0.8 |
| Java on the author's command-line PATH | 21.0.5 |
| Terraform / AzureRM provider | 1.14.9 / 3.117.1 |
| kubectl / bundled Kustomize | 1.35.4 / 5.7.1 |

Use `evidence/eclipse-bundles.info` for the full installed bundle inventory.
It records the author installation; it is not an automated target-platform
installer. Python dependencies and the provider lock are included. Exact
Eclipse setup remains manual; a fresh independent Eclipse build was not run by
the archive auditor. The author-exported JUnit result and static replays are
distinct evidence.

## Recheck the archived configuration observations

Unzip into a working directory. Create a Python 3.12 virtual environment and
activate it using your operating system's activation command, then:

```text
python -m pip install -r evaluation/journal/requirements.txt
python -m unittest discover -s tests -p "test_journal_*.py" -v
python tools/run_journal_comparison.py --generated evidence/eclipse-observations/generated-c00 --out reviewer-runs/c00-01 --terraform terraform
python tools/run_fault_comparison.py --dsl-observations evidence/eclipse-observations/dsl-observations.json --out reviewer-runs/faults-01 --terraform terraform
```

Install the recorded Terraform and kubectl clients before native checks; both
must be on PATH. Terraform init may download its pinned provider. These commands
do not plan/apply cloud resources. Use fresh output directories for retries.
The Python tests include synthetic collector fixtures; they are tool QA, not
additional DSL experiments. Replaying observations does not rerun the DSL parser.

## Regenerate with Eclipse

Use an installation matching the recorded bundles and Java 21.0.8 for the IDE
launch. Import `meta-model` plus the six `org.xtext.ucd.mlopsdsl*` / `mlops-tests`
projects from this working copy. The metamodel includes Ecore, GenModel and
generated Java sources. If regenerating that Java code, generate it from
`meta-model/model/mlops.genmodel` first. Then run
`org.xtext.ucd.mlopsdsl/src/org/xtext/ucd/mlopsdsl/GenerateMlopsDsl.mwe2`, clean/build,
and run `MlopsDslValidationTest` with JUnit 5.

For editor-driven generation, launch a fresh runtime Eclipse and import the
working `mlops-tests` project there. Verify its project Location, enable
automatic building and save the model. Check output freshness before evaluating
an evolution. Operate on a working copy; the archived trial snapshots remain
the observations. Detailed prepare/evaluate/retry instructions are in
`evaluation/journal/execution.md`.

## Interpretation

The common contract contains 79 selected checks. Native validators and the
study-specific domain guard are reported separately. Dataset splits, training
GPU allocation, workflow-link identities and serving commands are outside the
paired emitted baseline; inapplicable cases are not misses. Whole-DSL and
baseline source-size totals have unequal scope and do not justify a reduction
ratio. Registry's raw output difference includes a dependency-lock removal;
exposure source-line counts include formatting by the preparation harness.

Reviewer access remains unverified until a versioned archive is published and
downloaded without signing in. Citation/release metadata and any claimed
functional-equivalence approval must refer to that final artifact.
