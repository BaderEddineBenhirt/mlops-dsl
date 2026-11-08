# Recorded GCP pilot, 8 November 2026

This record describes an actual headless Xtext/JUnit run and a paired artifact
comparison. It is not the author's Eclipse execution and is not included in the
already published Zenodo v2 archive.

## Actual observations

- Existing Azure suite: 23 JUnit tests, zero failures/errors/skips after MWE2
  regeneration. Separate GCP suite: two methods, zero failures/errors/skips.
- GCP configurations: four valid controls, seven semantic fault variants and
  three evolution variants. These derive from one synthetic GCP fraud-service
  configuration. They are not 14 independent applications.
- Both generated output and the Kustomize baseline satisfy 96 selected checks
  for GC00, both port boundaries and each evolution variant. The private control
  satisfies 95 checks because two public TLS checks are replaced by an absence
  check. These are structural/selected-value checks, not complete equivalence.
- DSL validation and the study-guarded baseline detect all seven matched faults
  and accept all four controls. The DSL rejects invalid inputs before producing
  artifact files.
- Native-stage observations: one detected fault (missing Kustomize replacement
  source), four missed faults, two infrastructure-fault checks blocked. No
  complete-set native recall is reported.

| Evolution from GC00 | DSL source files | Baseline source files | Generated configuration files | Remaining selected failures |
| --- | ---: | ---: | ---: | --- |
| Registry v1 to v2 | 1 | 2 | 2 | Zero on both |
| Port 9091 to 9092 | 1 | 1 | 3 | Zero on both |
| Serving replicas 3 to 5 | 1 | 1 | 1 | Zero on both |

Registry renaming changes three DSL value occurrences and two baseline value
occurrences. File counts therefore do not establish fewer edits, lower effort or
general maintainability benefits. Changes were mechanical, not timed human work.

## Native validation remains blocked

The report's overall status is **BLOCKED**, not PASS. Terraform initialization
and Google provider installation succeed, but this execution environment blocks
the provider's Unix socket listener. The native diagnostic is a plugin-schema
startup failure. Native Terraform validation must be rerun on an environment
that permits provider execution, such as the author's Windows installation.
No Terraform plan/apply, GCP authentication, provisioning or live inference was
performed. A completed Windows run must be recorded separately rather than
overwriting this run.

## Evidence

`runs/agent-linux-20261108-02.zip` preserves 935 files: the actual parsed sources,
generated output and manifests, baseline sources and Kustomize outputs,
requirements, fault observations, evolution diffs, native logs, JUnit XML,
tooling-test output, implementation snapshot and environment provenance.

Archive SHA-256:
`1491ba7a08b26096df3095fd2d605aae2e412f0ca4f7dd87973e2f0f2d6ee133`.

The internal `manifest.json` hashes all other archived files. The run used Java
17, Xtext 2.40.0, OCL Pivot 1.22.0, JUnit 5.13.4, Terraform 1.14.9, Google provider
5.45.2, kubectl 1.35.4 and the metamodel dependency archived in Zenodo v2. It used
an assembled Maven classpath, not a fresh Eclipse installation. The included
headless POM records that classpath and accepts `mlops.repo`, `mlops.metamodel`
and `ocl.jars` path properties. It is not an automatic dependency installer.
The supported author workflow remains MWE2, Eclipse JUnit and the comparison
runner described in README.md.

All 34 Python tooling tests pass, including seven new GCP tooling checks and an
actual Kustomize integration check. Temporary integrity-test fixtures are
explicitly labeled as runner QA and are not counted as experimental results.

## Implementation findings

The GCP compute renderer previously returned only a local cluster-name value.
It now emits a GKE cluster and CPU node pools. Subnet literals and initial/
minimum/maximum node counts are preserved, and child modules declare and receive
the GCP project identifier used by logging. The Google provider is pinned.

The new case also exposed reference resolution tied to configuration values
instead of declaration identifiers. Local declared-name aliases now resolve
independently of dataset locations, framework names and cluster names. Existing
value-derived aliases remain available for older fixtures. Existing Azure
regression tests still pass.

Checked-in generated parser files are stale relative to the grammar's Boolean
value rule. This run regenerated them with MWE2. Regenerated implementation
files are not part of this source change. The author must regenerate and rebuild
before running the new suite.
