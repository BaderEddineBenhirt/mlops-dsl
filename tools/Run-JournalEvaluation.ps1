param(
    [Parameter(Mandatory=$true)][string]$ObservationsFolder,
    [string]$Python = "",
    [string]$Kubectl = "kubectl",
    [string]$Terraform = "terraform"
)
$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $PSScriptRoot
if (-not $Python) { $Python = Join-Path $root ".venv-evaluation\Scripts\python.exe" }
$observations = (Resolve-Path -LiteralPath $ObservationsFolder).Path
if (-not (Test-Path -LiteralPath (Join-Path $observations "dsl-observations.json"))) {
    throw "Missing actual Eclipse dsl-observations.json in $observations"
}
$generated = Join-Path $observations "generated-c00"
if (-not (Test-Path -LiteralPath $generated -PathType Container)) { throw "Generated C00 export missing" }
$run = Join-Path $root ("evaluation\journal\runs\author-" + (Get-Date -Format "yyyyMMdd-HHmmss-fff"))
New-Item -ItemType Directory -Path $run | Out-Null
Push-Location -LiteralPath $root
try {
    & $Python tools\run_journal_comparison.py --generated $generated --kubectl $Kubectl --terraform $Terraform --out (Join-Path $run "broader")
    $broaderCode = $LASTEXITCODE
    & $Python tools\run_fault_comparison.py --dsl-observations (Join-Path $observations "dsl-observations.json") --kubectl $Kubectl --terraform $Terraform --out (Join-Path $run "faults")
    $faultCode = $LASTEXITCODE
    $baseline = Join-Path $run "authoritative-baseline"
    Copy-Item -LiteralPath "evaluation\journal\baseline\c00-azure" -Destination $baseline -Recurse
    Copy-Item -LiteralPath "evaluation\comparison\baseline\c00-runtime\runtime" -Destination (Join-Path $baseline "runtime") -Recurse
    Copy-Item -LiteralPath "evaluation\comparison\baseline\c00-runtime\kustomization.yaml" -Destination $baseline
    & $Python tools\measure_authoring_sources.py --dsl mlops-tests\src\validation\c00_valid_azure.mlops --baseline $baseline --out (Join-Path $run "source-size.json")
    $sizeCode = $LASTEXITCODE
    @{broader_exit_code=$broaderCode; faults_exit_code=$faultCode; size_exit_code=$sizeCode;
      evolution="NOT_RUN by this script; use the independent prepare/evaluate tasks";
      replication="NOT_RUN; use build_replication_package.py with actual meta-model and JUnit XML"} |
        ConvertTo-Json | Set-Content -LiteralPath (Join-Path $run "command-status.json") -Encoding utf8
    Write-Host "Preserved observations: $run"
    Write-Host "Inspect each report. A successful runner does not imply all scientific evidence is complete."
} finally { Pop-Location }
