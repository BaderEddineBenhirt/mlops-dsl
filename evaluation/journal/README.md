# Journal-grade scientific validation: work remaining

This directory contains the protocol, runnable experiments and evidence ledger.
Start with [execution.md](execution.md) for the Windows/Eclipse commands. Tools
being implemented is distinct from experiments being completed.

## Added execution material (7 November 2026)

| Work | Implementation | Evidence still needed |
| --- | --- | --- |
| Configuration evolution | Unified port/registry/exposure prepare/evaluate runner with fresh snapshots | Full native trial archives and equivalence review; all three task passes author-reported |
| Comparative fault detection | Source mutations, Kustomize/native logs, domain guard, hash-checked DSL JSON join, matched denominators | Actual 16-case Eclipse JSON; review of seven applicable pairs and five exclusions |
| Broader comparison | Direct Azure Terraform baseline, runtime Kustomize, nine lifecycle policies, 79 selected checks | Fresh artifacts from fixed renderer; native Terraform validation and practitioner equivalence review |
| Specification size | Maintained-source inventory and declared line/token proxies | Equal-scope review; descriptive counts do not establish authoring effort savings |
| Replication package | Actual metamodel/JUnit preflight, source/observations collector, dependency locks, environment logs and draft ZIP | Actual dependency and XML, full Eclipse bundle inventory, public immutable archive with download verified |

Agent-executed observations are under `runs/agent-20261107/`. They include
negative findings in the historical generated snapshot and provider startup
blocking native Terraform validation. Python runner QA is separate from the
unexecuted expanded Eclipse suite. Nothing in these tools guarantees acceptance.

## Current archived observations (18 October 2026)

- Xtext suite includes four valid and twelve invalid fixtures (eleven semantic,
  one linking), and two parser/scoping regressions. Author showed 20 successful
  Eclipse JUnit tests, but exported JUnit XML is not yet present in the repository.
- Corrected C00 snapshot passes 10/10 selected runtime manifest requirements.
- Assistant-assisted Kustomize baseline independently authors four runtime
  objects and passes the same 10/10 selected checks on both sides; full
  functional equivalence has **not** been reviewed.
- Initial TLS regression was observed 9/10, then corrected and observed 10/10.
- Merged PR #10 introduces a real port-evolution experiment harness. It does
  **not** yet archive the actual paired DSL source edit/regeneration.
- At that stage, no full Terraform-plus-lifecycle baseline, comparative fault detection,
  end-to-end deployment or practitioner-reviewed authoring effort study exists.

Sources: evaluation/comparison/runtime-pilot-observations.md,
evaluation/comparison/runs/, evaluation/validation/README.md,
evaluation/comparison/port-evolution.md.

## Required work packages

| Workstream | Status | Required completion evidence |
| --- | --- | --- |
| WP1: DSL correctness/diagnostics | PARTIAL | Exported JUnit XML, validator/generator diagnostics, hashes, fresh run logs |
| WP2: full direct-authoring comparison | PARTIAL | Independently authored Terraform, K8s and lifecycle-policy baseline; reviewer-approved mapping; validator logs |
| WP3: paired fault-injection | PARTIAL (baseline only) | Real DSL JSON, equivalent pairs, missed/false-alarm cases and stages |
| WP4: configuration evolution | PARTIAL (three task passes author-reported) | Full native trial archives and equivalence review |
| WP5: specification/maintenance measurements | PARTIAL (descriptive) | Equal-scope review, duplicated authoritative values, real evolution files touched |
| WP6: real deployment / ML lifecycle if claimed | NOT RUN | Cloud rollout, inference and relevant policy events, sanitized raw logs and teardown |
| WP7: reproducibility & manuscript integrity | PARTIAL | External metamodel, pinned environment, exported results, reviewer-accessible archive, claims traced to data |

All ongoing tasks remain open after this pull request is merged. This PR defines
what it would take to *close* them; CI tests the measurement tools only.

## Protocol and execution material

- protocol.md: experimental design, estimands, controls, bias, limitations.
- work-packages.json: explicit work packages, statuses and acceptance criteria.
- case-matrix.tsv: C00 and two *proposed* additional independent case sizes.
- fault-pairs.tsv: 12 negative mutations and four valid controls, both approaches,
  initially NOT_RUN. Includes the linking case without treating it as semantic.
- evolution-tasks.tsv: three proposed independent evolutions and postconditions.
- equivalence-review.md: independent review record template.
- deployment-evidence.md: safe deployment, inference and teardown protocol.
- replication-release.md: complete versioned reviewer replication checklist.

## Commands for measurement tooling (do NOT execute a cloud deployment)

    python -m unittest discover -s tests -p "test_journal_*.py" -v
    python tools/summarize_fault_pairs.py --matrix evaluation/journal/fault-pairs.tsv
    python tools/audit_journal_evidence.py --plan evaluation/journal/work-packages.json
    python tools/measure_authoring_sources.py --dsl mlops-tests/src/validation/c00_valid_azure.mlops --baseline evaluation/comparison/baseline/c00-runtime --out unique-report.json

Original runner remains the source for actual paired port changes:
evaluation/comparison/port-evolution.md. Follow its prepare and evaluate stages
in runtime Eclipse, archive before/after raw outputs, *then* proceed to other
tasks. Do not report a percentage complete from this checklist.

