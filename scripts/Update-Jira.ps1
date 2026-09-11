$issueKey = (Get-Content issue.txt).Trim()

if (Test-Path execution-status.txt) {
    $results = Get-Content execution-status.txt | Out-String
}
else {
    $results = "No execution results generated."
}

$buildUrl = $env:GITHUB_RUN_URL

$commentText = "Automated Test Execution Results`n`nIssue: $issueKey`n`n$results`nBuild:`n$buildUrl"

$pair = "$env:JIRA_EMAIL`:$env:JIRA_TOKEN"
$bytes = [System.Text.Encoding]::ASCII.GetBytes($pair)
$encoded = [System.Convert]::ToBase64String($bytes)

$headers = @{
    Authorization = "Basic $encoded"
    Accept = "application/json"
    "Content-Type" = "application/json"
}

$body = @{
    body = @{
        type = "doc"
        version = 1
        content = @(
            @{
                type = "paragraph"
                content = @(
                    @{
                        type = "text"
                        text = $commentText
                    }
                )
            }
        )
    }
} | ConvertTo-Json -Depth 20

$url = "$env:JIRA_URL/rest/api/3/issue/$issueKey/comment"

Write-Host "Updating Jira issue: $issueKey"

Invoke-RestMethod `
    -Method POST `
    -Uri $url `
    -Headers $headers `
    -Body $body

Write-Host "Jira updated successfully."
