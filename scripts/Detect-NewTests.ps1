$historyFile = ".history/executed-tests.json"

if (!(Test-Path $historyFile)) {
    '{"executedTests":{}}' | Out-File $historyFile
}

$history = Get-Content $historyFile -Raw | ConvertFrom-Json

if (Test-Path tests-to-run.txt) {
    Remove-Item tests-to-run.txt
}

$changedFiles = git diff HEAD~1 HEAD --name-only

foreach ($file in $changedFiles) {

    if ($file -notmatch '^src/test/java/.*\.java$') {
        continue
    }

    $className = [System.IO.Path]::GetFileNameWithoutExtension($file)

    $diff = git diff HEAD~1 HEAD -- $file

    if ($diff -match '^\+.*@Test') {

        $alreadyExecuted = $false

        if ($history.executedTests.PSObject.Properties.Name -contains $className) {
            $alreadyExecuted = $true
        }

        if (-not $alreadyExecuted) {

            Write-Host "New test detected in $className"

            Add-Content tests-to-run.txt $className
        }
    }
}
