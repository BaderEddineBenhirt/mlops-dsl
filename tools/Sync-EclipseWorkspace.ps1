param(
    [string]$WorkspacePath = "C:\Users\bader\eclipse-workspace\modelling"
)

$ErrorActionPreference = "Stop"
$repoRoot = Split-Path -Parent $PSScriptRoot
$backupRoot = Join-Path $env:TEMP ("mlops-validation-" + (Get-Date -Format "yyyyMMdd-HHmmss-fff"))

foreach ($project in @("org.xtext.ucd.mlopsdsl", "org.xtext.ucd.mlopsdsl.tests", "mlops-tests")) {
    if (-not (Test-Path -LiteralPath (Join-Path $WorkspacePath $project) -PathType Container)) {
        throw "Missing Eclipse project: $project in $WorkspacePath"
    }
}

$relativeFiles = @(
    "org.xtext.ucd.mlopsdsl\src\org\xtext\ucd\mlopsdsl\generator\MlopsGenerator.java",
    "org.xtext.ucd.mlopsdsl\src\org\xtext\ucd\mlopsdsl\MlopsDslStandaloneSetup.java",
    "org.xtext.ucd.mlopsdsl\src\org\xtext\ucd\mlopsdsl\MlopsDsl.xtext",
    "org.xtext.ucd.mlopsdsl\src\org\xtext\ucd\mlopsdsl\validation\MlopsDslValidator.java",
    "org.xtext.ucd.mlopsdsl\src\org\xtext\ucd\mlopsdsl\scoping\MlopsDslScopeProvider.java",
    "org.xtext.ucd.mlopsdsl.tests\META-INF\MANIFEST.MF",
    "org.xtext.ucd.mlopsdsl.tests\src\org\xtext\ucd\mlopsdsl\tests\MlopsDslParsingTest.xtend",
    "org.xtext.ucd.mlopsdsl.tests\src\org\xtext\ucd\mlopsdsl\tests\MlopsDslValidationTest.java"
)

foreach ($folder in @("mlops-tests\src\validation", "org.xtext.ucd.mlopsdsl.tests\src\validation")) {
    $relativeFiles += Get-ChildItem -LiteralPath (Join-Path $repoRoot $folder) -Recurse -File |
        ForEach-Object { $_.FullName.Substring($repoRoot.Length).TrimStart('\', '/') }
}

foreach ($relative in $relativeFiles) {
    if (-not (Test-Path -LiteralPath (Join-Path $repoRoot $relative) -PathType Leaf)) {
        throw "Missing repository file: $relative"
    }
}

foreach ($relative in $relativeFiles) {
    $from = Join-Path $repoRoot $relative
    $to = Join-Path $WorkspacePath $relative
    if (Test-Path -LiteralPath $to) {
        $backup = Join-Path $backupRoot $relative
        New-Item -ItemType Directory -Path (Split-Path -Parent $backup) -Force | Out-Null
        Copy-Item -LiteralPath $to -Destination $backup
    }
    New-Item -ItemType Directory -Path (Split-Path -Parent $to) -Force | Out-Null
    Copy-Item -LiteralPath $from -Destination $to
}

Write-Host "Copied $($relativeFiles.Count) source/fixture files to $WorkspacePath"
Write-Host "Previous files backed up under $backupRoot"
Write-Host "Next: refresh the three Eclipse projects; run MWE2; clean/build; run MlopsDslValidationTest with JUnit 5."
Write-Host "Regeneration and test execution have NOT been performed by this script."
Write-Host "The suite now includes journal exports and renderer regressions (23 @Test methods)."
Write-Host "Copy the JOURNAL OBSERVATIONS path printed by JUnit; export JUnit XML separately from the JUnit view."
