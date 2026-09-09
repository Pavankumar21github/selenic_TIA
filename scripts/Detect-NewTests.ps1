$ErrorActionPreference = "Stop"

Remove-Item testclasses.txt -ErrorAction SilentlyContinue

$changedFiles = git diff --name-only HEAD~1 HEAD

foreach ($file in $changedFiles) {

    if ($file -notmatch '^src/test/java/.*\.java$') {
        continue
    }

    $className = [System.IO.Path]::GetFileNameWithoutExtension($file)

    $diff = git diff HEAD~1 HEAD -- $file

    if ($diff -match '^\+.*@Test') {

        Write-Host "Detected new @Test in $className"

        Add-Content testclasses.txt $className
    }
}

if (Test-Path testclasses.txt) {
    Get-Content testclasses.txt
}
else {
    Write-Host "No new @Test annotations found."
}
