# This script will retry a given command n times (by default we use 5).
# Currently we use this to retry `git clone` during official builds.

 param (
    [Parameter(Mandatory=$true)][string]$command,
    [int]$retries = "5"
 )

$done = $false
[int]$retryCount = "0"

do {
  Invoke-Expression $command
  if ("$LASTEXITCODE" -ne 0) {
    $retryCount = $retryCount + 1
  } else {
    $done = $true
  }
  if ($retryCount -ge $retries){
    Write-Host "Could not complete command after $retries attempts"
    $done = $true
  }
}
While ($done -eq $false)