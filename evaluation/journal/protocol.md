# Scientific evaluation protocol (pre-specified)

## Research questions

RQ1: Compared with independently authored configuration using available native
validators, which *equivalent seeded faults* can be detected by the DSL,
at what stage, and with what false alarms on valid controls?

RQ2: Under equivalent requirements, how many source files and authoritative
edit locations must be changed, and which required postconditions remain
inconsistent, for an independent evolution task?

RQ3: How do size and duplication of **author-maintained** specifications differ
at equal scope? Report physical nonblank noncomment lines, explicit lexical
token-proxy counts, and separately reviewed duplicated declarations.
Specification conciseness is **not** authoring time or maintainability.

RQ4: Do both generated and direct artifacts pass real YAML/JSON, Kubernetes and
Terraform verification? Separate parsing, schema validation, terraform
validate, terraform plan, apply, and real service behavior.

RQ5 (only if claimed): Can a deployed implementation serve real inference and
execute the stated registration, monitoring, gate or retraining behaviors?

## Case sampling and equivalence

Freeze independent requirements before writing either representation. Existing
C00 is complex Azure; design small CPU and medium lifecycle cases as additional
proposals, not completed or randomly sampled experiments. A small purposive
study does not show superiority across all clouds or domains. Require a
practitioner to review requirement-to-DSL and requirement-to-baseline mapping.
Do not copy generated artifacts and call them a hand-authored baseline.
Disclose assistant assistance and technology expertise. Preserve mismatches,
unsupported features and review disagreements.

## Faults

Start each mutation from a *fresh valid pair*. Precisely state equivalent
fault injection in each layer and whether each approach is applicable.
Compare early DSL diagnostics with baseline parser/schema/terraform tools,
but do not pretend the baseline has identical domain knowledge. Archiving
a successful runner unit test is not an experimental case.

Outcomes: DETECTED (target defect caught), MISSED (validators ran and did not
catch it), PASS (valid control accepted), FALSE_ALARM (valid control rejected),
BLOCKED (unable to run), NOT_RUN (not executed), NOT_APPLICABLE (paired
equivalence impossible, with explicit reason). False alarms belong only to
valid controls; blocked and not-applicable must not enter recall denominators.
Linking and parser defects must not inflate semantic recall. Include tool
version, detection stage and raw log reference for executed outcomes.

Publish numerator, denominator, excluded outcomes and per-case paired tables.
No tests are pre-marked as passed. Include null/tie/negative results.

## Evolutions

Snapshot two equivalent valid starting points, edit the maintained source
only, regenerate the DSL output and run the identical postcondition oracle.
Independent tasks include API port, model registry name and public exposure
with TLS/domain. Count author-controlled edit files and locations separately
from mechanically generated changed files. Ordinary baselines may centralize
values via variables or Kustomize; do not engineer a weak baseline. A tie is
valid. Timing and human productivity need separate observed participant trials;
they are not estimated from source diffs.

## Submission gate

The manuscript once planned twelve valid scenarios, forty mutations and
twelve evolutions. Those are plans, not observed sample sizes. Change the
manuscript to match **executed archived evidence**, or run the larger study
before reporting its numbers. Report every failure, incomplete task and
limitation. Acceptance by a journal is never guaranteed.
