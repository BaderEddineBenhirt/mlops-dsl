# Audit of the uploaded author replication draft

Input: `mlops-dsl-replication-draft.zip`, 5,477,077 bytes, SHA-256
`8ffaced045525762300f8f579c662a1f94a3ddfcf8555573e7a58c76bf8952b5`.
The original archive was inspected without modifying it. A later collected
archive has a different identity and must retain its own checksum.

## Verified contents and evidence

- 2,547 archive entries. All 2,545 files listed in the manifest exist and match
  their SHA-256 hashes. The two unlisted control files are the manifest itself
  and collection preflight, written after the inventory.
- The actual `meta-model` bundle includes Ecore/GenModel and Java sources.
  The Ecore namespace is `http://ucd.ac.ma/mlops`; it contains 63 classifiers.
  All six requested DSL/editor/test projects are present.
- The original JUnit XML contains 23 test cases and zero failures/errors/skips.
  All 16 observed fixture hashes match both packaged fixture copies.
- The recorded broad/fault runner hashes match the packaged Python sources.
- Independent static inspection reproduced every recorded requirement and domain
  observation for both archived author comparison runs and all five evolution
  attempts. The successful broad comparison is 79/79 on each side. The successful
  three tasks have no residual selected failures; both failed registry attempts
  reproduce their recorded stale-output problems.
- Native Terraform JSON confirms valid configurations, zero errors, on the
  successful broad pair. S05/S07 negative mutations are also accepted by native
  Terraform validation: those are missed domain faults at that stage.
- The 27 packaged Python tooling tests were independently rerun and passed.
  These tests are QA, not additional DSL experimental cases. No cloud deployment
  or fresh independent Eclipse/Xtext build was performed during this audit.

Machine-readable observations are in `replication-audit-20261107.json`.

## Corrections to interpretation

Registry's raw changed-output count of three includes deletion of a
Terraform-created `.terraform.lock.hcl`. Two **generated configuration files**
changed; the removed dependency lock is an auxiliary snapshot difference.
Exposure changes three configuration outputs plus one README. Port changes
three Kubernetes configuration files.

Java versions are distinct: the JUnit exporter records **21.0.8**, while the
captured command-line Java is **21.0.5**. The actual bundle inventory confirms
Eclipse 4.37, Xtext/Xtend 2.40, EMF Common 2.44, Ecore 2.41, OCL Pivot 1.22 and
OCL codegen 2.22. Keep both Java versions in reproduction metadata.

The archive contains the native trial snapshots and logs that were previously
only author-reported. This improves evidence traceability; it does not turn the
single C00 case into multiple independent cases or make the full-scope size
comparison equivalent.

## Scope review

| Area | What the evidence establishes | Limit |
| --- | --- | --- |
| Runtime | Selected namespace, replicas, six ports, host and TLS references | Certificate/controller availability, serving command/environment/resources and live inference were not established |
| Terraform | Provider validation and selected region/CIDR/resource declarations | No evaluated plan/apply or complete infrastructure equivalence; some flags/metadata are only declarations |
| Lifecycle policies | Nine policy documents satisfy the selected shared values | Policy execution is not established; generated CI jobs contain echo placeholders and deployment uses `|| true` |
| Faults | Seven matched semantic faults detected by DSL and guarded baseline; four controls accepted | Native checks are narrower; five original faults lack an equivalent emitted feature |
| Maintenance | Recorded changes and zero residual selected failures | One case, mechanical baseline edits, no timing study; registry and exposure formatting confounds remain |
| Specification size | Actual maintained-source inventories | DSL includes additional declarations, so no fair reduction ratio is supported |

The technical review approves **only the observations under the explicit shared
contract**, not full functional equivalence of the entire DSL and baseline.
Independent practitioner review remains open.

One additional policy distinction should be documented or reconciled: Ecore's
`SplitsSumToOne` accepts `0.99 < total < 1.01`, whereas editor and Java generator
checks require `abs(total - 1) <= 0.0001`. Thus the Java checks are tighter than
that OCL invariant. The tested gross split violation fails both; boundary
equivalence between these validation layers was not evaluated. Do not attribute
all Java-enforced rules to OCL or claim the layers enforce identical policies.

## Reviewer usability and release

The uploaded draft lacks a root quickstart and contains an author-specific
runtime workspace path in its launch configuration. The follow-up packaging
change adds a root README and makes the runtime workspace relative. Neither
change rewrites the recorded experiments. Installing the exact Eclipse target
remains a manual step; the bundle inventory is not an automated installer.

A versioned public/reviewer-accessible artifact and unauthenticated download
check are still required. The archive contains no root LICENSE or CITATION.cff;
final citation/release metadata is an author decision. The manuscript itself
was not supplied for this audit, so its claims have not yet been reconciled
line by line with these observations.
