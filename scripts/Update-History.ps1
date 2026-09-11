Write-Host "===== Update-History.ps1 START ====="

$historyFile = ".history/executed-classes.json"

if (!(Test-Path "tests-to-run.txt")) {
    Write-Host "No tests-to-run.txt found."
    exit 0
}

$tests = Get-Content "tests-to-run.txt"

Write-Host "Tests found:"
$tests

$json = @{
    executedClasses = @($tests)
} | ConvertTo-Json

Write-Host "JSON generated:"
Write-Host $json

Set-Content -Path $historyFile -Value $json

Write-Host "History file written."

Write-Host "===== Update-History.ps1 END ====="

exit 0
