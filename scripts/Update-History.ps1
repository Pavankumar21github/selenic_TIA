Write-Host "===== Update-History.ps1 START ====="

$historyFile = ".history/executed-classes.json"

Write-Host "History file: $historyFile"

if (!(Test-Path "tests-to-run.txt")) {

    Write-Host "tests-to-run.txt not found."
    Write-Host "Skipping history update."

    exit 0
}

Write-Host "Reading history..."

$history = Get-Content $historyFile -Raw | ConvertFrom-Json

Write-Host "Reading executed tests..."

$tests = Get-Content "tests-to-run.txt"

foreach ($test in $tests) {

    Write-Host "Processing: $test"

    if ($history.executedClasses -notcontains $test) {

        $history.executedClasses += $test

        Write-Host "Added to history: $test"
    }
}

Write-Host "Writing history file..."

$history | ConvertTo-Json -Depth 10 | Set-Content $historyFile

Write-Host "===== Update-History.ps1 END ====="

exit 0
