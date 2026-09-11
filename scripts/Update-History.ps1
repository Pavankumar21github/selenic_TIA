$historyFile = ".history/executed-classes.json"

if (!(Test-Path "tests-to-run.txt")) {

    Write-Host "No tests executed."
    exit 0
}

$history = Get-Content $historyFile -Raw | ConvertFrom-Json

$tests = Get-Content "tests-to-run.txt"

foreach ($test in $tests) {

    if ($history.executedClasses -notcontains $test) {

        $history.executedClasses += $test
    }
}

$history | ConvertTo-Json -Depth 10 | Set-Content $historyFile

Write-Host "Execution history updated."
