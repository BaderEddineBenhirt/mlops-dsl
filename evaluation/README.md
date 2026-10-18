# Evidence required for the journal revision

The decision letter asks for measurable benefits against relevant authoring baselines, not only correct generated files. The validation suite is one component of that evaluation, not a completed answer to the decision letter.

## 1. Correctness regression

Execute `validation/README.md`. Archive all positive/negative outcomes and the parser/scoping regressions. Separate syntax/linking errors, domain-semantic errors and generator failures. Preserve before/after results for the TLS defect when available; do not reconstruct missing measurements.

## 2. Paired authoring benchmark (not yet executed)

Choose representative valid configurations of different sizes within the demonstrated Azure scope. Freeze the written requirements before preparing both representations. For each configuration, produce (a) the authoritative DSL and generated artifacts, and (b) directly authored Terraform/YAML/JSON satisfying the same requirements. Do not call generated output a manually authored baseline. Have a second practitioner review functional equivalence where possible.

Document all validators and versions for both approaches. Apply the same downstream YAML/JSON parsing and Terraform checks; record when a domain-aware baseline tool is available. For each seeded requirement violation, document its equivalent expression in both representations and whether the baseline can represent it. Mark non-equivalent or unsupported pairs not applicable instead of counting them as baseline failures. Preserve all outcomes, including faults both approaches detect or miss.

Measure and report: detection stage, correct detection, false negatives, false alarms, and whether invalid artifacts were emitted. Report NCLOC, explicitly defined token counts and duplicated authoritative declarations for equivalent valid cases. Exclude generated output from DSL authoring effort, while reporting its size separately. Do not treat LOC as measured authoring time or assume conciseness implies maintainability.

## 3. Evolution benchmark (not yet executed)

Start each task from a fresh equivalent valid pair. Suitable changes are API port 8080 to 9090, registered model name migration, and private exposure to public exposure with TLS/domain requirements. Before editing, identify every artifact that should change and the required postcondition. Count author-maintained edit locations, files touched and residual inconsistencies after validation/regeneration. Count handwritten source edits separately from generated-file changes. Use observed completion times only if an authoring experiment is actually recorded.

## 4. Submission integrity

The current PDF describes 12 valid scenarios, 40 mutations and 12 evolution tasks, while some results are marked PENDING. Those counts are plans until backed by executed cases and archived raw data. Adjust the manuscript to the actual executed design. Do not replace placeholders with estimates or claim that this regression suite alone establishes superiority. Correctness, comparison results, cloud deployment evidence and end-to-end ML execution are distinct claims.

Before submission, provide a stable replication release that includes the metamodel dependency, grammar, validators, generator, pinned environment, scripts, fixtures, actual baseline artifacts, raw observations and execution logs. The current private GitHub repository alone does not satisfy the editor's request for direct reviewer access to the replication package. This work does not guarantee acceptance by a journal.
