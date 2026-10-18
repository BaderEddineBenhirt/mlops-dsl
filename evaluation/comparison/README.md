# Run the first comparison pilot

This runner records observations, not a completed comparative experiment. C00 is an existing complex Azure example, not the proposed minimal CPU case. Start here to check the measurement pipeline before creating additional cases.

## 1. Produce fresh C00 artifacts

In the runtime Eclipse, open `mlops-tests/src/validation/c00_valid_azure.mlops`, make a harmless whitespace edit and save it with automatic building enabled. Use a fresh output directory, or move the old `generated/validation_c00` folder outside the generation directory first. Locate `generated/validation_c00/runtime/k8s/deployment.yaml` in the runtime project. The configured Xtext output folder may be `src-gen`; use the actual location rather than assuming it. Check generation errors before continuing. The JUnit suite generates in memory, so its successful run alone does not create this disk export.

## 2. Run the artifact checks

From PowerShell in the repository, install PyYAML in a local virtual environment and run the script. Replace `C:\actual\path\generated\validation_c00` with the folder containing `runtime`, `infra` and `mlops`.

```powershell
py -m venv .venv-evaluation
& .\.venv-evaluation\Scripts\python.exe -m pip install PyYAML
& .\.venv-evaluation\Scripts\python.exe -m pip freeze > evaluation\comparison\environment-local.txt
& .\.venv-evaluation\Scripts\python.exe tools\run_comparison.py `
  --dsl mlops-tests\src\validation\c00_valid_azure.mlops `
  --generated 'C:\actual\path\generated\validation_c00' `
  --out evaluation\comparison\runs\c00-generated-01.json
```

No result is marked passed before execution. Missing files and missing required values fail the checks. Syntax parsing is not Kubernetes schema validation. The contract deliberately requires an explicit Ingress TLS host because C00 requests public TLS. The current renderer appears to omit the TLS section: preserve that failure, investigate the deployment assumptions, and fix or document the behavior before claiming requirement preservation. An explicitly declared TLS host alone also does not prove a working certificate or live HTTPS.

## 3. Prepare the real baseline

Write the same C00 configuration directly as Terraform/YAML from the original requirements. Use ordinary variables/modules and documented validators. Store it under `evaluation/comparison/baseline/c00/` with the same relative artifact paths. Do not copy generated files and label them manually authored. If an assistant prepares this baseline, describe it as assistant-assisted direct configuration, not a human authoring experiment. Record practitioner review, the requirements covered and omissions before interpreting any size differences. The ten automated checks cover only selected runtime properties, not complete equivalence of the full infrastructure and lifecycle.

Run again with a new output filename and add:

```powershell
  --baseline evaluation\comparison\baseline\c00
```

The report retains `equivalence_status: not_reviewed` and `authoring_time: not_measured`; review evidence belongs in a separate signed/dated record. The tool computes no productivity percentage or superiority claim.

## 4. Apply downstream Terraform validation to both sides

With Terraform installed, add `--terraform`. The runner invokes `terraform init -backend=false -input=false -no-color` followed by `terraform validate -json` in each artifact directory's `infra/terraform`. It saves stdout, stderr and exit codes, and does not run plan/apply. Initialization can download providers/modules and writes Terraform local files. Missing tools and timeouts are blocked runs, not detected configuration defects. Archive `terraform version`, provider lock files, the Git commit, the Python environment file and raw logs. Terraform validation does not demonstrate cloud deployment.

## 5. Execute a paired change

Start both approaches from their valid, reviewed baseline. Change serving port 8080 to 9090 in the author-maintained sources and regenerate only the DSL side. Update a copy of the contract to expect 9090 in all port checks and run both with `--contract` pointing to it. Record the actual source diff, files edited, independently changed locations and remaining requirement failures. Both approaches may centralize ports; do not force a baseline to duplicate values. Keep generated file changes separate from author-maintained edits. Repeat with independent tasks only after this pilot works.

## Measurement boundaries

Inventories include `.tf`, `.yaml`, `.yml` and `.json` files, excluding hidden path components. They exclude Python applications, Markdown and other outputs; the entire DSL input is inventoried separately. Nonblank physical lines include comments and formatting; they are not NCLOC or tokens. Publish these descriptive counts with their scope, and do not compare them as equivalent workload sizes without reviewing coverage. Full error-detection comparisons and any human completion-time experiment remain additional work.
