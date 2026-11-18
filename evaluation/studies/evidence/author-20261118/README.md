# Automated study evidence, 18 November 2026

- `correctness-report.json`: 23 tasks, comprising 16 before/after evolution
  comparisons and seven paired fault cases.
- `maintenance-report.json`: 212 finite editing scenarios, comprising 168 DSL
  inputs and 44 baseline inputs on the same 16 evolution task instances.
- `TEST-MlopsDslStudyExportTest-maintenance.xml`: author-supplied Eclipse export
  with one executed exporter method, zero failures, zero errors and zero ignored
  tests. The method exports models in a loop. The XML itself does not record the
  job manifest path or certify 168 separate JUnit tests. Its association with the
  maintenance run follows the author-supplied console and export context.

Both JSON reports were checked against the supplied raw `studies.zip` archive.
Archive verification found no mismatch in 11,296 artifact hashes. All 212
maintenance classifications and the report summaries were replayed without a
mismatch. This is replay of the recorded checks, not independent practitioner
replication or a new cloud deployment.

The minimum required source-value edits tie on all 16 tasks. Registry changes
involve one DSL source file versus two baseline source files on four task
instances. The other 12 tasks tie on source-file locality. These results do not
establish human productivity gains or a general superiority claim. No participant
experiment was executed. The full raw snapshots must accompany the updated
replication deposit. The earlier Zenodo v3 deposit predates these new studies.
