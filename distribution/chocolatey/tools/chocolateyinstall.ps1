$ErrorActionPreference = 'Stop'

$packageName = 'kando-cli'
$url = 'https://github.com/yuvinraja/kando-cli/releases/download/v1.0.2/kando-v1.0.2-dist.zip'
$checksum = '0fca49834377a21c7eab78b040d59e0cfe79e2038ee7d1c23c9bbe0f4144419b'
$checksumType = 'sha256'
$toolsDir = "$(Split-Path -parent $MyInvocation.MyCommand.Definition)"
$installDir = Join-Path $toolsDir 'kando'

# Download and extract
Install-ChocolateyZipPackage `
  -PackageName $packageName `
  -Url $url `
  -UnzipLocation $toolsDir `
  -Checksum $checksum `
  -ChecksumType $checksumType

# Create batch file wrapper
$batchContent = @"
@echo off
java -jar "$installDir\lib\kando.jar" %*
"@

$batchFile = Join-Path $toolsDir 'kando.bat'
Set-Content -Path $batchFile -Value $batchContent

# Add to PATH
Install-ChocolateyPath -PathToInstall $toolsDir
