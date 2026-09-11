$historyFile = ".history/executed-classes.json"

$history = Get-Content $historyFile -Raw | ConvertFrom-Json

$tests = Get-Content tests-to-run.txt

foreach ($test in $tests) {

    if ($history.executedClasses -notcontains $test) {

        $history.executedClasses += $test
    }
}

$history | ConvertTo-Json -Depth 10 | Set-Content $historyFile

git config user.name github-actions
git config user.email github-actions@github.com

git add .history/executed-classes.json

git diff --cached --quiet

if ($LASTEXITCODE -ne 0) {
    git commit -m "Update execution history"
    git push
}
