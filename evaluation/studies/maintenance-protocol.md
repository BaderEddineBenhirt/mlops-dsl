# Automated alternative: edit locality and incomplete-change resilience

This alternative requires no participants and makes no claim about human speed,
usability, cognitive load or actual rates of human error. The optional human
study remains available for future work. Its power calculation and participant
statistics do not apply to this deterministic benchmark.

## Question and contribution

For equivalent selected target requirements, how many explicit source-value
substitutions are involved in a configuration change, how many files are
affected, and what happens when only part of those substitutions is applied?

The comparison retains Kustomize replacements and domain guards. Source-edit
counts are structural measures. They do not establish time savings. This
experiment extends the successful before/after checks by inspecting intermediate
states and omissions, including cases where the baseline centralises a setting
as effectively as the DSL.

## Fixed design and scope

Use the 16 prepared change instances from C00 and C10. They cover port, registry,
replica and domain updates with two target variants. These remain two constructed
configuration contexts. Extra subsets do not become independent applications,
fault categories or participants. Model validation, selected Terraform/YAML
requirements and preservation of runtime/lifecycle YAML define the evaluated
boundary. This does not establish that every item of ML intent is preserved.

For each task and representation, derive explicit non-comment token substitutions
between maintained before/after sources. A changed quoted scalar/name or numeric
token is one edit unit. Layout, comments and CRLF differences do not create units.
Reject additions/removals and unsupported transformations rather than silently
inventing a grouping. Publish every unit with its source path and before/after
value. The full applied set must reproduce the target token stream.

Enumerate the entire power set of those edits, including zero and full controls.
The existing materials yield 168 DSL inputs and 44 baseline inputs. These are
212 editing scenarios, not 212 independent cases. Derive the workload before
executing the new partial DSL models. Freeze source hashes, contracts, original
control exports, the actual generator class and scoring code.

## Execution

1. Verify that the actual original Xtext exports match their input bytes, loaded
   generator hash and artifact manifests, and satisfy both endpoint contracts.
2. Prepare fresh sources for every subset. No DSL output is generated in Python.
3. Run the 168 new model inputs through the existing Java/Xtext exporter.
4. Execute real Kustomize builds for baseline endpoints and all 44 subsets.
5. For accepted outputs, evaluate the frozen after-contract, the domain guard
   and semantic preservation relative to that representation's starting output.
6. Record every outcome, including rejections, incomplete targets, unintended
   changes, blocked commands and unattributed failures.

Record the study contract as an external checking stage. It must not be credited
to native tools or to the source validator. A rejected partial DSL model prevents
generation but does not fulfil the requested change. An accepted incomplete
target is also not a successful maintenance operation. Neither is automatically
a bug in the source validator, because some partial states can be admissible
configurations for a different intended change.

## Measures and exact analysis

- Full-set source substitutions and affected files.
- Smallest passing subset found within the enumerated edit set, and smallest
  passing file count. This is conditional on the selected requirements and edit
  model, not an optimal repair over all possible source programs.
- Outcome counts by task, approach and number of omitted substitutions.
- Paired differences in the smallest passing edit count, with median, range
  and explicit counts of DSL advantages, baseline advantages and ties.

Kustomize can render a source occurrence non-authoritative by overwriting it
from another field. Enumeration discovers this when a one-edit subset passes
despite a two-edit prepared target. Do not inflate baseline effort by treating
every changed occurrence as necessary. Apply the same principle to DSL values
outside the selected generated-output scope.

The zero-edit control must not satisfy the new target. The full-edit control
must satisfy it. Control failures block interpretation. Infrastructure errors
are retained as blocked/unattributed results, never counted as detected faults.

There is no random sampling of developers or application projects. Counts are
exact over a finite, deliberately selected set. No population p-values or
confidence intervals are attached to them. A fraction of enumerated subsets
is an exact property of this editing model, not a probability that a real user
will make an error. Target-value variants and shared task families remain
visible in reporting so they cannot inflate claims of generality.

## Reproduction in PowerShell

Reuse the successfully exported materials. Keep the earlier comparison intact.

```powershell
$materials = "evaluation\studies\local\materials-20261118-163553"
$maintenance = "evaluation\studies\local\maintenance-" + (Get-Date -Format "yyyyMMdd-HHmmss")
python tools\maintenance_study.py prepare --materials $materials --out $maintenance
if ($LASTEXITCODE -ne 0) { throw "Maintenance preparation failed." }
$jobs = (Resolve-Path "$maintenance\jobs.tsv").Path
Write-Host "-Dmlops.study.jobs=$jobs"
```

In the existing JUnit configuration for `MlopsDslStudyExportTest`, replace the
previous VM `mlops.study.jobs` value with the new printed path and run. No source
generator edit, MWE2 regeneration or additional cloud deployment is required.
Retain the JUnit XML. Each new input must have a real receipt. Reusing the same
export destination is refused, preserving previous observations.

After the exports complete:

```powershell
$results = $maintenance + "-results"
python tools\maintenance_study.py evaluate --study $maintenance --out $results
```

Keep PowerShell variables in the same window, or set `$maintenance` explicitly
to the actual directory. The report includes every scenario and a concise paired
summary. The runner does not execute Terraform init/validate or cloud apply.

Methodological basis: ACM SIGSOFT Engineering Research and Benchmarking standards,
https://github.com/acmsigsoft/EmpiricalStandards. These standards support clearly
defined, comparative, repeatable technical evaluation. They do not make this
constructed benchmark a substitute for evidence of human productivity.
