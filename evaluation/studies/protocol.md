# Two complementary evaluation studies

Status: implemented prospective protocol. New participant results do not exist.
Existing Azure/GCP evidence remains retrospective technical evidence.

## Claims and study boundaries

Study A asks whether a model and generator preserve required configuration
properties during evolution, and whether domain validation rejects applicable
faults. Study B asks whether the DSL changes the effort required to reach a
correct configuration compared with direct authoring supported by Kustomize
and explicit domain checks. Azure remains the infrastructure deployment
demonstration. Neither study requires additional cloud provisioning.

The task suite contains four change families: API port, model registry name,
serving replicas and routing domain. Each has two target-value variants. Both
representations start from equivalent requirements. Native tools remain
available in the baseline. A baseline with unnecessary duplicated edits is not
an acceptable comparator. The existing exposure-change results are retained
separately, including their formatting confounds and unsuccessful retries.

## Study A: correctness and propagation benchmark

### Materials and selection

The builder prepares 16 evolution tasks, using two variants of each family
on Azure C00 and GCP C10, and seven existing GCP fault mutations. Before/after
models, baseline sources, contracts and source hashes are recorded. The 39
model inputs must be processed by the actual Xtext generator. Python never
fabricates DSL outputs. The Java exporter records model bytes, loaded generator
class hash, diagnostics and output hashes. Rejected inputs write no artifacts.

These are development materials from two previously studied configurations.
They are neither new independent projects nor an external fault sample.
Repeating the same rule with another number does not create a new fault type.
For broader claims, select additional configurations from independently
specified application requirements. Record provenance, selection criteria,
representable scope and exclusions before running the framework. A reviewer
other than the implementer must check baseline equivalence and expected answers.
Do not describe review as independent until it actually occurs.

New project entries can be added to the manifest. Evolution entries need
before/after models, baseline directories and contracts. Fault entries need
a model, baseline, diagnostic expectation, matching domain rule and an explicit
applicability justification. Mark `development: false` only for separately
selected and documented assessment cases. Unsupported faults remain explicit
scope exclusions, never counted as detections or misses.

### Execution and measurements

1. Freeze reviewed requirements, model/source hashes, code and generator class.
2. Export every input into a fresh directory through `MlopsDslStudyExportTest`.
3. Build baseline outputs through actual Kustomize commands and retain logs.
4. For each valid phase, evaluate the full selected output contract and guard.
5. Compare every YAML leaf within `runtime/k8s/*.yaml` and `mlops/*.yaml`
   before/after. Only paths whose expected values change in the frozen contract
   may change. Record every unexpected addition, removal or value change.
6. Count maintained source files changed. This is a locality metric, not time
   or cognitive effort. YAML serialisation does not inflate semantic comparisons.
7. For faults, separate a relevant DSL rejection, a guard detection, an
   unrelated diagnostic, an unattributed build failure and a blocked command.

The current runner does not execute Terraform validation or cloud apply.
Existing native Terraform evidence must remain separately attributed.
Preservation checks cover selected runtime and lifecycle YAML, not every
Terraform expression or the entire generated package.

### Analysis and interpretation

Report task-level correctness, unintended changes, source locality, applicable
fault recall and valid-input false alarms separately by project and tool.
Do not use contract assertion counts as independent observations. C00 and C10
form a fixed descriptive benchmark. No population p-value is attached to it.
An expanded inferential benchmark would need a justified sampling frame and
project-level analysis. This implementation deliberately does not infer
generalisability merely from a larger number of variants.

## Study B: matched-block crossover authoring experiment

### Primary outcome

For task j with frozen time limit L_j, define a normalised capped time:

    Y_j = elapsed_seconds / L_j, when all postconditions pass within L_j
    Y_j = 1, for an incorrect, abandoned or timed-out task

The score is in [0,1]. It is an efficiency endpoint that incorporates failure,
not an estimate of the time an unsuccessful task would eventually take.
Time includes validation and correction, including generation and build waits.
Baseline minus DSL is positive when the DSL has a lower score. Report successful
completion separately so that equal capped times cannot conceal unequal success.

H0: the authoring approach has no effect on the primary outcome under the
randomised assignment. The primary test is two-sided at alpha = 0.05.
The primary effect estimate is the mean within-participant difference, averaged
within independent allocation blocks. No direction is assumed from pilot results.

### Participants, training and allocation

Define the intended population and recruitment source before recruitment.
Record anonymous IDs and predeclared experience strata. Use the same training
duration and practice requirements for both tools. Practice tasks must differ
from assessment tasks. Record training completion before starting the timer.
Participation is voluntary. When students participate, participation and
performance must have no bearing on assessment. Keep identities separately
from the released research data.

Each participant completes eight fresh tasks: four families with one approach,
then four equivalent variants with the other approach. Two participants with
the same experience stratum and variant order form an allocation block. One
is randomly assigned DSL first, the other baseline first. Within every four
participants, one block uses variant A first and another variant B first.
Task order within each period is randomised and recorded. Both approaches use
the Azure context to avoid confounding tool assignment with provider.

Every task restarts from its own valid starting configuration. A change from
an earlier task must not leak into the next. Participant workspaces contain
only starting sources and a goal statement. Expected after-models, mutation
scripts and scoring contracts are examiner-only. During each task allow the
same documentation and predefined assistance policy. Log deviations.

Counterbalancing reduces order and learning effects but does not eliminate
carryover. Report results by period and perform a descriptive first-period
check. If carryover is material, do not interpret the crossover effect as a
clean tool advantage. A new between-participant design may then be needed.

### Timing, failures and exclusions

The timer starts after copying and checking the starting state and ends only
when the scoring tool verifies the required state. Save source and artifact
snapshots for every attempt. Validation errors are participant outcomes.
Hardware failures, unavailable external tools and a generator implementation
mismatch are infrastructure interruptions, recorded separately. Participants
can abandon a task, which receives the full time-cap score. Timeouts also
receive the full score and are never silently omitted from a time analysis.

Withdrawal or an infrastructure interruption excludes the whole two-person
block from paired primary analysis. Publish reasons and report recruitment
attrition. Missing sessions block analysis. No imputation, automatic replacement
or optional stopping is performed. If exclusions reduce the achieved sample
below its justified target, report the resulting limitation and uncertainty.

### Sample-size planning

Specify the smallest useful improvement on the normalised score and justify
it with the supervisors/domain stakeholders before confirmatory data collection.
Use a separate pilot to estimate the variability of BLOCK contrasts and assess
task equivalence and the time cap. Pilot participants are excluded from the
confirmatory sample. Revisions after the pilot require a new frozen plan.

`power` performs a sensitivity simulation using the primary sign-randomisation
rejection rule under explicitly supplied Gaussian block-contrast assumptions.
It reports the assumptions, random seed, simulation uncertainty and a candidate
sample size. This is a planning approximation, not evidence of an observed
benefit. Examine several plausible variability assumptions and use a feasible,
justified recruitment target before freezing confirmatory execution. The pilot
template's eight participants and 900-second cap are operational starting
choices, not a power justification or a universal recommended sample size.

### Statistical analysis

Each participant contributes a mean score per approach. Average the two paired
participant contrasts within each allocation block. Swap tool-order assignment
within each block for the two-sided randomisation test. Enumerate all assignments
up to 18 blocks, otherwise use 19,999 random assignments with the plus-one
correction. The primary estimate is the mean block contrast.

The 95% percentile-bootstrap interval resamples whole two-person blocks 10,000
times. Participants' repeated tasks remain together. It quantifies uncertainty
under exchangeability of recruited blocks, conditional on these fixed tasks.
It does not repair convenience sampling or generalise to all MLOps projects.
Report participant and period distributions alongside the estimate, interval
and primary p-value. A secondary failure-rate difference has a block-bootstrap
interval and no additional confirmatory significance claim. Other metrics
and subgroup comparisons are exploratory.

A non-significant result is not equivalence. An interval containing practically
important benefit and harm is inconclusive. An interval and useful effect,
rather than p < 0.05 alone, determine the practical interpretation.

## Freezing, independence and reporting

Freeze implementation, contracts, task assignment policy, endpoint, cap,
recruitment target, exclusion rules and analysis before confirmatory collection.
The lock file includes hashes and refuses changes. Post-freeze defects require
an amended version and transparent reporting. Keep all failed runs.

The software enforces checksums and records provenance, but it cannot certify
that a named reviewer is independent or that collection followed the protocol.
Those are research responsibilities. Publish the review record and deviations.
No result from a unit test, fake receipt or planning simulation may enter the
manuscript as a participant observation or a generator execution result.

## Methodological references

- ACM SIGSOFT. Empirical Standards, Engineering Research and Experiments.
  https://github.com/acmsigsoft/EmpiricalStandards
- Vasileiou et al. (2026). A knowledge-based approach for guided development
  of Infrastructure as Code. Software and Systems Modeling 25, 515-548.
  https://doi.org/10.1007/s10270-025-01294-1

The first defines methodological expectations. The second provides a related
IaC evaluation with industrial applications and participant studies, rather
than a numerical sample-size prescription for the present experiment.
