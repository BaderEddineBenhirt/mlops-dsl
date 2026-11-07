param(
    [Parameter(Mandatory=$true)][string]$ObservationsFolder,
    [string]$JunitXml = "",
    [string]$WorkspacePath = (Join-Path $env:USERPROFILE "eclipse-workspace\modelling"),
    [string]$BundlesInfo = ""
)
$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $PSScriptRoot
$python = Join-Path $root ".venv-evaluation\Scripts\python.exe"
$metamodel = Join-Path $WorkspacePath "meta-model"
if (-not (Test-Path -LiteralPath $python -PathType Leaf)) { throw "Evaluation Python missing: $python" }
if (-not (Test-Path -LiteralPath $metamodel -PathType Container)) { throw "Actual metamodel project missing: $metamodel" }
$observations = (Resolve-Path -LiteralPath $ObservationsFolder).Path

# These are author-run trials, not new experiments performed by this collector.
$runRoot = Join-Path $root "evaluation\journal\runs"
foreach ($name in @("registry-author-01-retry-02", "exposure-author-01", "port-author-01")) {
    $summary = Join-Path $runRoot "$name\summary.json"
    if (-not (Test-Path -LiteralPath $summary -PathType Leaf)) { throw "Missing actual trial: $summary" }
    $trial = Get-Content -Raw -LiteralPath $summary | ConvertFrom-Json
    if ($trial.status -ne "PASS_SELECTED_EVOLUTION") { throw "Trial is not a selected-evolution pass: $name" }
}

if (-not $JunitXml) {
    Add-Type -AssemblyName System.Windows.Forms
    $dialog = New-Object System.Windows.Forms.OpenFileDialog
    $dialog.Title = "Select the actual exported MlopsDslValidationTest JUnit XML"
    $dialog.Filter = "JUnit XML (*.xml)|*.xml"
    $dialog.CheckFileExists = $true
    try {
        if ($dialog.ShowDialog() -ne [System.Windows.Forms.DialogResult]::OK) {
            throw "No JUnit XML selected. Run again when the actual export is available."
        }
        $JunitXml = $dialog.FileName
    } finally { $dialog.Dispose() }
}
$junit = (Resolve-Path -LiteralPath $JunitXml).Path

if (-not $BundlesInfo) {
    $candidates = @(Get-Process -Name eclipse -ErrorAction SilentlyContinue |
        ForEach-Object {
            if ($_.Path) {
                $homeDirectory = Split-Path -Parent $_.Path
                $candidate = Join-Path $homeDirectory "configuration\org.eclipse.equinox.simpleconfigurator\bundles.info"
                if (Test-Path -LiteralPath $candidate -PathType Leaf) { $candidate }
            }
        } | Sort-Object -Unique)
    if ($candidates.Count -eq 1) { $BundlesInfo = $candidates[0] }
}

$destination = Join-Path (Split-Path -Parent $root) ("mlops-replication-" + (Get-Date -Format "yyyyMMdd-HHmmss-fff"))
$arguments = @("tools\build_replication_package.py", "--metamodel", $metamodel,
               "--junit", $junit, "--observations", $observations, "--out", $destination)
if ($BundlesInfo) { $arguments += @("--bundles-info", (Resolve-Path -LiteralPath $BundlesInfo).Path) }

# The Python collector copies evaluation/ in full, including all actual run
# snapshots, source diffs, successful attempts and previous failed attempts.
Push-Location -LiteralPath $root
try {
    & $python @arguments
    if ($LASTEXITCODE -ne 0) {
        throw "Replication collection did not pass preflight. Inspect the messages and $destination\preflight.json"
    }
    Write-Host "Draft package collected under: $destination"
    Write-Host "Full author-run evidence is included from evaluation/journal/runs."
    Write-Host "The archive is not published; reviewer access and equivalence review remain open."
} finally { Pop-Location }
