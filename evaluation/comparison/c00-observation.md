# C00 pilot observation and TLS correction

The author-recorded run `runs/c00-generated-01.json` at 2026-10-18T15:59:09 UTC contains 9 passing and 1 failing selected requirement checks. The failed check is `explicit_tls_host`; the archived Ingress has no `spec.tls` section. Sixteen YAML files parse successfully. Terraform validation and the baseline are `not_run`. These are selected artifact checks, not deployment evidence or a complete baseline comparison.

Keep the original report and `generated/c00` snapshot intact. They describe the renderer before this correction. The source fix emits the configured Ingress class and, when TLS is true, a TLS host and a deterministic Secret reference `<sanitized-project-name>-serving-tls`. For C00 this is `validation-c00-serving-tls` in `vision-serving`. The generated README documents the externally provisioned certificate/Secret and controller prerequisite. This is explicit configuration, not certificate issuance or demonstrated HTTPS.

After pulling and running `tools/Sync-EclipseWorkspace.ps1`, refresh and clean/build in the development Eclipse. Run `MlopsDslValidationTest`: two new generation-content regressions bring its expected total to 20 tests. Runtime confirmation is pending. Close the old runtime Eclipse and launch a fresh one so it uses the rebuilt generator; regenerate C00 there.

Use the same runner and unchanged contract, but a new report filename:

```powershell
& .\.venv-evaluation\Scripts\python.exe tools\run_comparison.py `
  --dsl mlops-tests\src\validation\c00_valid_azure.mlops `
  --generated "C:\Users\bader\eclipse-workspace\modelling\mlops-tests\src-gen\generated\validation-c00" `
  --out evaluation\comparison\runs\c00-generated-02.json
```

Archive the new output separately under `generated/c00-after-tls` and retain both reports. Only record passing results after the real rerun. The generated folder uses a hyphen (`validation-c00`); the DSL project name uses an underscore (`validation_c00`).

Next complete the independently authored baseline and review equivalence. This defect and its correction do not themselves establish a comparative advantage.
