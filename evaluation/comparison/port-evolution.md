# Execute the paired API-port change

This controlled artifact task tests source edits and change propagation. It is not a timed human productivity experiment. The direct Kustomize baseline already centralizes its port, so a tie is a legitimate result. The task measures the selected runtime layer, not the complete MLOps lifecycle.

## 1. Freeze the starting sources

In PowerShell at the repository root:

```powershell
$portRun = "evaluation\comparison\runs\port-evolution-" + (Get-Date -Format "yyyyMMdd-HHmmss-fff")
& .\.venv-evaluation\Scripts\python.exe tools\run_port_evolution.py prepare `
  --dsl "C:\Users\bader\eclipse-workspace\modelling\mlops-tests\src\validation\c00_valid_azure.mlops" `
  --run $portRun
```

Preparation snapshots the actual workspace DSL source, baseline before/after sources and unchanged/correspondingly updated contracts. It applies the baseline's one-field port change mechanically and records its diff. It does not edit the Eclipse DSL or generator outputs. A missing/nonunique port or existing run directory stops preparation.

## 2. Perform the DSL change

Launch a fresh runtime Eclipse using the corrected generator. Open `mlops-tests/src/validation/c00_valid_azure.mlops`. In `ServingSpec azure`, change `port = 8080` to `port = 9090` and save. This regenerates the live `src-gen/generated/validation-c00` output directory. The original port-8080 snapshot remains in Git under `evaluation/comparison/generated/c00-after-tls`.

If the runtime project uses another location, prepare using that actual DSL file. Make only the port edit for this task. Save this C00 file last, as other examples sharing the same output project name could overwrite live files. The evaluator checks the actual resulting port values rather than treating an existing folder as evidence of a fresh run.

## 3. Evaluate both before/after pairs

In the same PowerShell session, where `$portRun` is still set:

```powershell
& .\.venv-evaluation\Scripts\python.exe tools\run_port_evolution.py evaluate `
  --run $portRun `
  --generated "C:\Users\bader\eclipse-workspace\modelling\mlops-tests\src-gen\generated\validation-c00"
```

The evaluator first requires the actual DSL source to equal the prepared one-field change, allowing leading/trailing blank-line differences. It archives the source and actual diff. The real Kustomize renderer and the same artifact checker evaluate the old and new requirements for both approaches. The updated contract requires port 9090 in the container, readiness probe, liveness probe, Service port, Service target and Ingress backend. Four other selected requirements must still pass.

The fresh generated directory is archived under `generated-after/` before checking it. `summary.json` reports the actual source-file/diff counts, six observed port changes per approach when successful, raw phase command outcomes and provenance. Source edit counts are structural observations, not measured authoring effort. Blocked runs and failures remain archived. Report a tie if both approaches change one source field and preserve all selected properties. Do not turn generated file changes into manual edit counts or claim observed time savings.

## 4. Preserve the observations

After inspecting the phase reports and summary:

```powershell
git add -- "$portRun"
git commit -m "test(evaluation): record paired port evolution observations"
git push origin evaluation/port-evolution
```

The tool does not restore the workspace DSL source. After preserving this task, returning the workspace example to port 8080 is a separate explicit edit and regeneration; the run snapshots retain both states. Existing summaries cannot be overwritten: prepare another run for retries.
