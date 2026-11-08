# Author Windows GCP evaluation, 8 November 2026

Recorded run: `runs/author-20261108-164641-995/report.json`.
Overall status: **PASS_SELECTED_REQUIREMENTS**.

The author ran `MlopsDslGcpEvaluationTest` in Eclipse and exported two JUnit
methods with zero failures, errors or ignored tests. The console records four
accepted controls, seven detected/rejected semantic faults and three accepted
evolution variants. All 14 source observations were imported after hash checks.

| Observation | DSL/generated | Study-authored baseline |
| --- | --- | --- |
| GC00 selected requirements | 96/96 | 96/96 |
| Private control GC01 | 95/95 | 95/95 |
| Port boundaries GC02/GC03 | 96/96 each | 96/96 each |
| Evolution GE01/GE02/GE03 | 96/96 each | 96/96 each |
| Matched semantic faults | 7/7 detected | 7/7 detected with the study guard |
| Valid controls | 4/4 accepted | 4/4 accepted |
| Native Terraform init/validate | PASS on all seven non-fault configurations | PASS on all seven non-fault configurations |

The 14 native validation JSON files for the non-fault configurations report
valid=true, zero errors and zero warnings. Native fault checks detect one of
seven selected faults and miss six, with no blocked or unattributed outcomes.
The missing API port is detected by Kustomize. Blank region and incoherent
initial/minimum node counts pass native Terraform validation but violate the
study's domain rules. Kubernetes API schema and native lifecycle policy-engine
validation were not executed. This is not a general ranking of validators.

| Independent change from GC00 | DSL source files | Baseline source files | Generated configuration files | Remaining selected inconsistencies |
| --- | ---: | ---: | ---: | --- |
| Registry v1 to v2 | 1 | 2 | 2 | Zero on both |
| API port 9091 to 9092 | 1 | 1 | 3 | Zero on both |
| Serving replicas 3 to 5 | 1 | 1 | 1 | Zero on both |

The registry task changes three DSL value occurrences and two baseline value
occurrences. All changes are mechanical. The file counts do not establish
lower editing effort, productivity or general maintainability advantages.

Terraform 1.14.9, Google provider 5.45.2, kubectl 1.35.4 and Kustomize 5.7.1
were used on Windows. No plan/apply, cloud provisioning, model training or live
inference is claimed. Baseline equivalence still has no independent review.
The GCP application is synthetic and adds one configuration case, not 14
independent applications or an independently authored industrial study.

## Byte-preserving evidence

The initial Git push normalized CRLF to LF in text snapshots. The observed
requirements and native validation results are unaffected, but normalized files
cannot reproduce every original byte hash directly.

`runs/author-20261108-164641-995.zip` preserves the evidence with an internal
archive manifest and provenance record. Exactly 474 files were restored to CRLF
only where that restoration matched the original recorded SHA-256. All 511
recorded source/output/inventory hashes were verified against the recovered
bytes. No configuration values, outcomes or original recorded hashes changed.

Terraform init adds platform-specific checksums to dependency lock files after
the runner records its initial inventory. Nine exact pre-init locks are retained
under `pre-native-snapshots/`, recovered from the unchanged baseline source only
when the original SHA-256 matches. The final locks remain at their original
paths. The provenance record maps both stages. Unhashed files retain the Git
representation. The uploaded author JUnit XML is included as
`environment/gcp-junit.xml` in both the run and the archive.

Archive SHA-256:
`5638f74f990f8c6c7eb72ab2bbc1b0fb43fed4bc457fe7ea97c67ab691d80be7`.

Use the ZIP to verify the original byte hashes. `.gitattributes` disables text
normalization for future GCP run records. No experiment rerun is required to
correct the transport issue. The earlier Linux BLOCKED run remains preserved
separately. These Windows results are not yet part of the published Zenodo v2
archive and should be included in a subsequent archived version.
