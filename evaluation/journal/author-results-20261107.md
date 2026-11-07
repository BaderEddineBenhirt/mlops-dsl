# C00 author-reported measurements, 7 November 2026

Source: the author's pasted runner JSON and Eclipse JUnit XML. Complete native
run directories remain on the author's Windows machine until collected into the
replication archive. These measurements concern one C00 Azure case, seven
matched semantic mutations, four valid controls and three controlled evolution
tasks. Registry retries are attempts of the same task.

## Recorded checks

- Eclipse suite: 23 tests started, zero failures/errors/ignored.
- Broader C00 comparison: both sides pass 79 selected requirements and native
  Terraform validation with CLI 1.14.9/provider 3.117.1.
- Matched fault comparison: DSL 7/7 detected, study-guarded baseline 7/7 detected;
  both accept 4/4 valid controls. Limited executed native checks detect 1/7.
  Native Terraform accepts S05/S07: its PASS is command success, not detection.
  Kubernetes API schema validation was not run. Five original faults have no
  equivalent emitted feature and are excluded from the paired denominator.
- Full-source inventory: DSL one file/511 line-proxy/1726 token-proxy; baseline
  15 files/286 line-proxy/1572 token-proxy. Scope equivalence is NOT_REVIEWED;
  these totals do not support a conciseness or productivity reduction claim.

## Evolution measurements

| Task | DSL source files / hunks | Baseline source files / hunks | DSL raw lines + / - | Baseline raw lines + / - | Changed DSL-generated files | Residual selected failures |
| --- | --- | --- | --- | --- | --- | --- |
| Registry v1 to v2 | 1 / 7 | 2 / 2 | 7 / 6 | 2 / 2 | 3 | 0 on both sides |
| PRIVATE/TLS false to PUBLIC/TLS true | 1 / 1 | 3 / 3 | 2 / 2 | 49 / 33 | 4 | 0 on both sides |
| API port 8080 to 9090 | 1 / 1 | 1 / 1 | 1 / 1 | 1 / 1 | 3 | 0 on both sides |

Physical diff hunks are not semantic edit locations. Registry changes six DSL
references versus two baseline references; the extra physical hunk/added line
must be distinguished from those six value changes. A whitespace edit was used
to trigger regeneration. The exact raw diff remains the source of truth.

The exposure harness creates private YAML sources with `yaml.safe_dump`, then
restores the original public sources. This changes layout/comments as well as
TLS configuration. Its raw +49/-33 lines therefore contain **harness-induced
formatting noise** and must not be used to claim effort savings. The actual
configuration postconditions and source-file counts are reported separately.

Port editing is a tie for maintained files and physical edits; both approaches
centralize the port and propagate it. Registry editing touches fewer DSL files
but requires more value references. Exposure spans fewer DSL files in this
specific baseline. These are mixed change-locality observations, with zero
remaining failures under the selected oracle. They establish neither human
productivity nor general maintainability superiority.

## Open gates

Collect the actual Ecore/GenModel dependency, JUnit export, all native input/output
snapshots and logs, environment/bundle inventory and immutable archive. Complete
the equal-scope review, disclose formatting/assistance confounds and align paper
claims with this single-case evidence. Real service deployment and ML lifecycle
execution are outside the executed checks. Reviewer access requires publication
and an unauthenticated download check after the draft package is assembled.
