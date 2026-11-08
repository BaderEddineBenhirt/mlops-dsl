# C10: GCP tabular fraud-service configuration

This is a second, synthetic configuration case, specified separately from the
Azure vision fixture. It is not an independent industrial case or an independent
practitioner evaluation. No fraud dataset is supplied, no model is trained, and
the image, project ID and service addresses are illustrative.

## Fixed case and comparison scope

The case uses PARQUET/TABULAR inputs, scikit-learn CPU training intent, a
four-task workflow, MLflow Registry, a scheduled/manual retraining policy,
GitHub CI intent, two decision criteria, a regional GKE cluster, one CPU node
pool, two subnets, three serving replicas and API port 9091. This differs from
the Azure vision case in application intent, model structure and infrastructure.

`contract.json` fixes 96 selected properties before observed generation is
imported. These cover four Kubernetes resources, nine lifecycle policy files,
root Terraform defaults, resource declarations and selected node/subnet values.
Checks of HCL expressions and resource counts are structural, not evaluated
plan equivalence. The subnet check verifies declared prefix literals and the
count separately. It does not execute an HCL interpreter.

`baseline/` is a separately authored Terraform/Kustomize/policy implementation
of those selected requirements. It was prepared with assistant support, not
copied from a generated GCP snapshot or timed as human work. Kustomize
centralizes the API port and TLS host. Whole-source DSL/baseline functionality
is unequal, and no conciseness or productivity ratio is justified. Full
functional equivalence and independent baseline review remain open.

`cases.tsv` fixes 14 configurations: four valid controls, seven matched semantic
faults and three mechanical evolution variants. The evolution tasks change model
registry naming, API port and serving replicas independently from GC00. They are
three tasks on one configuration, not three additional application cases.

## Execute in Eclipse

1. Pull this branch and run `tools/Sync-EclipseWorkspace.ps1`.
2. Refresh the three Eclipse projects, run `GenerateMlopsDsl.mwe2`, then
   clean/build. Regeneration matters because the repository's old generated
   parser can still interpret explicit `false` incorrectly even when the
   checked-in grammar is correct.
3. Open `org.xtext.ucd.mlopsdsl.tests/src/org/xtext/ucd/mlopsdsl/tests/MlopsDslGcpEvaluationTest.java`.
4. Run as JUnit 5. There are two test methods. One exports 14 actual parsed,
   validated and generated configurations. The other checks GCP preservation.
5. Copy the `GCP OBSERVATIONS:` folder printed in the console and export the
   separate GCP JUnit XML. Keep the existing 23-test Azure XML unchanged.

The new standalone fixture is also under
`mlops-tests/src/validation/gcp/c10_fraud_gcp.mlops`. Saving that model in runtime
Eclipse should produce `src-gen/generated/fraud-gcp`, but the comparison uses
the JUnit exports to retain exact source/output hashes for every variant.

From Windows Command Prompt in the repository:

```bat
.\.venv-evaluation\Scripts\python.exe -m pip install -r evaluation\journal\requirements.txt
powershell -NoProfile -File ".\tools\Run-GcpEvaluation.ps1" -ObservationsFolder "PASTE_THE_GCP_OBSERVATIONS_FOLDER"
```

Or invoke `tools/run_gcp_comparison.py --observations FOLDER --out NEW_FOLDER`
directly. `--terraform` and `--kubectl` accept explicit executable paths.

## Interpretation and evidence boundaries

No expected result is prefilled as an observation. The runner rejects changed
sources, missing/duplicate cases, stale case matrices and modified generated
files. It preserves each source, output manifest, baseline source and materialized
output, command arguments, exit status, logs, requirements and edit diffs.

The seven baseline faults are checked with Kustomize/parsing and an explicitly
study-specific Python domain guard. Terraform init/validate is additionally run
on both sides for every valid/control/evolution configuration and on the two
infrastructure faults. Missing tools/plugin startup failures are BLOCKED,
not passed. Terraform faults that validate successfully are missed domain
faults. An unattributed native failure is not credited as fault detection.
Native Kubernetes API schema validation and native policy-engine checks are not
executed. The experiment never runs plan/apply, authenticates to GCP or creates
cloud resources. Provider installation still requires access to the Terraform
Registry/download servers unless a local provider mirror is configured.

The Google provider is pinned to 5.45.2. The model's `desiredNodes` becomes GKE
`initial_node_count`; autoscaling owns subsequent size changes. These counts
are per zone, not total nodes for the regional cluster. All declared subnets are
emitted; the GKE cluster uses the first. A GCP VPC has no overall CIDR field, so
the enclosing `NetworkSpec.cidr` is not claimed as a preserved GCP VPC setting.

The generated environment tfvars example remains `dev`, as in the existing
generator. `EnvironmentSpec`, backend intent, arbitrary StorageTarget names,
budget enforcement, GPU allocation, runtime resource limits, application entry
commands, ingress privacy enforcement and actual certificate operation are not
established by this selected contract. No values should be manually patched into
generated files to make a requirement pass.

Results remain separate from the already published Zenodo v2 archive. A later
Zenodo version can include the GCP run after actual execution and review.
