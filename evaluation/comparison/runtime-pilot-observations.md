# Executed scoped runtime pilot

## Evidence and provenance

The author archived the corrected C00 output and second report in commit `1071c1cb98c23878d236b286aa19eab279a6b603`. Its ten selected artifact checks pass; Terraform and the direct baseline were not run in that author-recorded report. The author also supplied an Eclipse JUnit screenshot showing 20 runs, zero errors and zero failures on 2026-10-18. Preserve the exported JUnit XML/console log as additional replication evidence when available; the repository does not yet contain that XML.

The assistant subsequently fetched the 25 inventoried configuration artifacts from that exact commit, prepared the separately authored Kustomize baseline, and executed the paired pilot in Linux. The source author had access to the generated output; this is not a blind or timed human authoring comparison. Practitioner review remains outstanding.

Actual paired report and raw client/build logs: `runs/agent-c00-runtime-pair-01/`. The run used official kubectl v1.35.0, with the publisher SHA-256 verified during download, and its bundled Kustomize v5.7.1. The report records Python/PyYAML versions, input hashes and command results. The generated files fetched from GitHub have Git-normalized line endings; this run does not establish byte identity with the original Windows working-directory output.

## Observed outcomes

| Approach | Selected requirements passed | What was executed |
| --- | --- | --- |
| DSL-generated corrected C00 snapshot | 10/10 | YAML/JSON parsing and the existing runtime contract |
| Direct Kustomize runtime baseline | 10/10 | Real `kubectl kustomize`, YAML parsing and the same contract |

This establishes preservation of the selected checked properties on both sides. It does not establish complete functional equivalence, deployability, lower authoring time, fewer human errors or a comparative advantage. The baseline contains four runtime objects and excludes Terraform, lifecycle policies and the application implementation. Full DSL size and partial baseline size are not equivalent workloads, so no source-size reduction percentage is reported.

## Runner verification, separate from study results

Additional temporary checks verified that changing the baseline Service port once from 8080 to 9090 propagates to all six required port values. The generated side of that evolution task has not been performed as a paired authoring task. Deliberately incorrect expected ports make the comparison runner fail, and a missing kubectl executable produces a blocked observation. These are checks of the measurement tooling, not extra independent experimental cases.

## Repeat on the author's machine

```powershell
git pull --ff-only
kubectl version --client
& .\.venv-evaluation\Scripts\python.exe tools\run_runtime_comparison.py `
  --out evaluation\comparison\runs\author-c00-runtime-pair-01
```

If kubectl is unavailable, install it using the official Windows instructions linked from `baseline/c00-runtime/README.md`, reopen PowerShell, then rerun with a fresh output directory. The command renders locally and does not apply resources. The original generated snapshots and prior reports remain separate observations.

Next: review the scoped baseline, execute and archive both sides of the port evolution task, then extend the independently reviewed specification/baseline to Terraform and lifecycle policies and run equivalent fault pairs. Do not describe the journal's requested evaluation as complete on the basis of this pilot.
