#!/bin/bash
# Chocolatey Package Update Script

set -e

VERSION="$1"
if [ -z "$VERSION" ]; then
    echo "Usage: $0 <version>"
    echo "Example: $0 v1.0.2"
    exit 1
fi

# Remove 'v' prefix if present
VERSION_CLEAN=${VERSION#v}

echo "🍫 Updating Chocolatey package for version $VERSION_CLEAN..."

# Download the release archive to get SHA256
ARCHIVE_URL="https://github.com/yuvinraja/kando-cli/releases/download/${VERSION}/kando-${VERSION}-dist.zip"
TEMP_FILE=$(mktemp)

echo "📥 Downloading release archive..."
curl -L -o "$TEMP_FILE" "$ARCHIVE_URL"

# Calculate SHA256
SHA256=$(sha256sum "$TEMP_FILE" | cut -d' ' -f1)
echo "🔐 SHA256: $SHA256"

# Update the nuspec file
NUSPEC_FILE="distribution/chocolatey/kando-cli.nuspec"
sed -i "s|<version>.*</version>|<version>$VERSION_CLEAN</version>|" "$NUSPEC_FILE"

# Update the install script
INSTALL_SCRIPT="distribution/chocolatey/tools/chocolateyinstall.ps1"
sed -i "s|\$url = '.*'|\$url = '$ARCHIVE_URL'|" "$INSTALL_SCRIPT"
sed -i "s|\$checksum = '.*'|\$checksum = '$SHA256'|" "$INSTALL_SCRIPT"

# Clean up
rm "$TEMP_FILE"

echo "✅ Chocolatey package updated!"
echo ""
echo "📋 Next steps:"
echo "1. Test the package locally:"
echo "   cd distribution/chocolatey"
echo "   choco pack"
echo "   choco install kando-cli -s . -y"
echo ""
echo "2. Submit to Chocolatey Community Repository:"
echo "   choco push kando-cli.$VERSION_CLEAN.nupkg -s https://push.chocolatey.org/"
echo ""
echo "📖 More info: https://docs.chocolatey.org/en-us/create/create-packages"
