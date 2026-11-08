param(
    [Parameter(Mandatory=$true)][string]$ObservationsFolder,
    [string]$PythonPath = "",
    [string]$TerraformPath = "terraform",
    [string]$KubectlPath = "kubectl"
)
$ErrorActionPreference = "Stop"
$repoRoot = Split-Path -Parent $PSScriptRoot
if ([string]::IsNullOrWhiteSpace($PythonPath)) {
    $PythonPath = Join-Path $repoRoot ".venv-evaluation\Scripts\python.exe"
}
if (-not (Test-Path -LiteralPath $PythonPath -PathType Leaf)) {
    throw "Missing evaluation Python: $PythonPath. Install evaluation/journal/requirements.txt first."
}
if (-not (Test-Path -LiteralPath (Join-Path $ObservationsFolder "dsl-observations.json") -PathType Leaf)) {
    throw "Select the GCP OBSERVATIONS folder printed by MlopsDslGcpEvaluationTest."
}
$run = Join-Path $repoRoot ("evaluation\journal\gcp\runs\author-" + (Get-Date -Format "yyyyMMdd-HHmmss-fff"))
Push-Location -LiteralPath $repoRoot
try {
    & $PythonPath tools\run_gcp_comparison.py --observations $ObservationsFolder --out $run --terraform $TerraformPath --kubectl $KubectlPath
    $code = $LASTEXITCODE
    Write-Host "Preserved GCP observations and logs: $run"
    if ($code -ne 0) { throw "GCP comparison did not pass. Inspect report.json and logs in $run. No previous run was overwritten." }
} finally {
    Pop-Location
}
