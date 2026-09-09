$ErrorActionPreference = "Stop"

Remove-Item tests-to-run.txt -ErrorAction SilentlyContinue

$changedFiles = git diff --name-only HEAD~1 HEAD

foreach ($file in $changedFiles) {

    if ($file -notmatch '^src/test/java/.*\.java$') {
        continue
    }

    Write-Host "Checking file: $file"

    $className = [System.IO.Path]::GetFileNameWithoutExtension($file)

    $diff = git diff HEAD~1 HEAD -- $file

    $lines = $diff -split "`n"

    for ($i = 0; $i -lt $lines.Count; $i++) {

        if ($lines[$i].Trim() -match '^\+\s*@Test') {

            for ($j = $i + 1; $j -lt $lines.Count; $j++) {

                if ($lines[$j] -match '^\+.*public\s+void\s+([A-Za-z0-9_]+)\s*\(') {

                    $methodName = $Matches[1]

                    $testIdentifier = "${className}#${methodName}"

                    Write-Host "Detected: $testIdentifier"

                    Add-Content tests-to-run.txt $testIdentifier

                    break
                }
            }
        }
    }
}

if (Test-Path tests-to-run.txt) {
    Write-Host ""
    Write-Host "Detected Tests:"
    Get-Content tests-to-run.txt
}
else {
    Write-Host "No new test methods detected."
}
