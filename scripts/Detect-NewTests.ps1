$historyFile = ".history/executed-classes.json"

if (!(Test-Path $historyFile)) {
    '{"executedClasses":[]}' | Set-Content $historyFile
}

$history = Get-Content $historyFile -Raw | ConvertFrom-Json

if (Test-Path tests-to-run.txt) {
    Remove-Item tests-to-run.txt
}

$matchingClasses = Get-Content matching-classes.txt

foreach ($file in $changedFiles) {

    if ($file -notmatch '^src/test/java/.*\.java$') {
        continue
    }

    $className = [System.IO.Path]::GetFileNameWithoutExtension($file)

    $diff = git diff HEAD~1 HEAD -- $file

    if ($diff -match '(?m)^\+.*@Test') {

        if ($history.executedClasses -notcontains $className) {

            Write-Host "New test detected in $className"

            Add-Content tests-to-run.txt $className
        }
    }
}
