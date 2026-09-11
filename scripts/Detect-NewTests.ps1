$historyFile = ".history/executed-classes.json"

if (!(Test-Path $historyFile)) {
    '{"executedClasses":[]}' | Set-Content $historyFile
}

$history = Get-Content $historyFile -Raw | ConvertFrom-Json

if (Test-Path "tests-to-run.txt") {
    Remove-Item "tests-to-run.txt"
}

if (!(Test-Path "matching-classes.txt")) {
    throw "matching-classes.txt not found"
}

$matchingClasses = Get-Content "matching-classes.txt"

$changedFiles = git diff HEAD~1 HEAD --name-only

foreach ($file in $changedFiles) {

    if ($file -notmatch '^src/test/java/.*\.java$') {
        continue
    }

    $className = [System.IO.Path]::GetFileNameWithoutExtension($file)

    # Only consider classes already mapped to the Jira WorkItem
    if ($matchingClasses -notcontains $className) {
        continue
    }

    $diff = git diff HEAD~1 HEAD -- $file

    # Look for newly-added @Test annotation
    if ($diff -match '(?m)^\+.*@Test') {

        if ($history.executedClasses -notcontains $className) {

            Write-Host "New test detected in $className"

            Add-Content "tests-to-run.txt" $className
        }
        else {

            Write-Host "$className already executed. Skipping."
        }
    }
}

if (Test-Path "tests-to-run.txt") {

    Write-Host ""
    Write-Host "================================="
    Write-Host "TESTS TO RUN"
    Write-Host "================================="

    Get-Content "tests-to-run.txt"

} else {

    Write-Host ""
    Write-Host "================================="
    Write-Host "NO NEW TESTS DETECTED"
    Write-Host "================================="
}
