$ErrorActionPreference = "Stop"

$historyFile = ".history/executed-classes.json"

# Create history file if it doesn't exist
if (!(Test-Path $historyFile)) {

    @{
        executedClasses = @()
    } | ConvertTo-Json | Set-Content $historyFile
}

$history = Get-Content $historyFile -Raw | ConvertFrom-Json

# Clean old output
if (Test-Path "tests-to-run.txt") {
    Remove-Item "tests-to-run.txt"
}

# Verify matching classes exist
if (!(Test-Path "matching-classes.txt")) {
    throw "matching-classes.txt not found"
}

$matchingClasses = Get-Content "matching-classes.txt"

Write-Host ""
Write-Host "================================="
Write-Host "MATCHING WORKITEM CLASSES"
Write-Host "================================="
$matchingClasses | ForEach-Object { Write-Host $_ }

# Ensure previous commit exists
git rev-parse HEAD~1 *> $null

if ($LASTEXITCODE -ne 0) {

    Write-Host ""
    Write-Host "No previous commit found."
    Write-Host "Skipping new test detection."

    exit 0
}

# Get changed files
$changedFiles = git diff HEAD~1 HEAD --name-only

if (-not $changedFiles) {

    Write-Host ""
    Write-Host "No changed files found."

    exit 0
}

Write-Host ""
Write-Host "================================="
Write-Host "CHANGED FILES"
Write-Host "================================="
$changedFiles | ForEach-Object { Write-Host $_ }

foreach ($file in $changedFiles) {

    if ($file -notmatch '^src/test/java/.*\.java$') {
        continue
    }

    $className = [System.IO.Path]::GetFileNameWithoutExtension($file)

    # Only classes mapped to Jira WorkItem
    if ($matchingClasses -notcontains $className) {

        Write-Host "Skipping $className (not mapped to current WorkItem)"

        continue
    }

    Write-Host ""
    Write-Host "Inspecting: $className"

    $diff = git diff HEAD~1 HEAD -- $file

    # Look for added @Test annotations
    if ($diff -match '(?m)^\+.*@Test') {

        Write-Host "Found new @Test in $className"

        if ($history.executedClasses -notcontains $className) {

            Add-Content "tests-to-run.txt" $className

            Write-Host "Added $className to execution list"
        }
        else {

            Write-Host "$className already exists in execution history"
        }
    }
    else {

        Write-Host "No new @Test detected in $className"
    }
}

Write-Host ""
Write-Host "================================="
Write-Host "FINAL EXECUTION LIST"
Write-Host "================================="

if (Test-Path "tests-to-run.txt") {

    Get-Content "tests-to-run.txt"
}
else {

    Write-Host "No new tests detected."
}
