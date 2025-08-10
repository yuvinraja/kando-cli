#!/bin/bash
# Master Package Manager Update Script
# Updates all package manager configurations for a new release

set -e

VERSION="$1"
if [ -z "$VERSION" ]; then
    echo "Usage: $0 <version>"
    echo "Example: $0 v1.0.2"
    exit 1
fi

# Remove 'v' prefix if present
VERSION_CLEAN=${VERSION#v}

echo "🚀 Updating all package managers for version $VERSION_CLEAN..."
echo ""

# Create scripts directory if it doesn't exist
mkdir -p scripts

# Make scripts executable
chmod +x scripts/update-homebrew.sh 2>/dev/null || true
chmod +x scripts/update-chocolatey.sh 2>/dev/null || true

# Update Homebrew
echo "🍺 Updating Homebrew..."
if [ -f "scripts/update-homebrew.sh" ]; then
    ./scripts/update-homebrew.sh "$VERSION"
else
    echo "❌ Homebrew update script not found"
fi

echo ""

# Update Chocolatey
echo "🍫 Updating Chocolatey..."
if [ -f "scripts/update-chocolatey.sh" ]; then
    ./scripts/update-chocolatey.sh "$VERSION"
else
    echo "❌ Chocolatey update script not found"
fi

echo ""

# Update Snap
echo "📦 Updating Snap..."
SNAP_FILE="snap/snapcraft.yaml"
if [ -f "$SNAP_FILE" ]; then
    sed -i "s/version: '.*'/version: '$VERSION_CLEAN'/" "$SNAP_FILE"
    echo "✅ Snap configuration updated!"
else
    echo "❌ Snap configuration file not found"
fi

echo ""

# Update Winget
echo "🪟 Updating Winget..."
WINGET_FILE="distribution/winget/kando-cli.yaml"
if [ -f "$WINGET_FILE" ]; then
    # Download the release archive to get SHA256 for Windows
    ARCHIVE_URL="https://github.com/yuvinraja/kando-cli/releases/download/${VERSION}/kando-${VERSION}-dist.zip"
    TEMP_FILE=$(mktemp)
    
    echo "📥 Downloading Windows release archive..."
    curl -L -o "$TEMP_FILE" "$ARCHIVE_URL"
    
    # Calculate SHA256
    SHA256=$(sha256sum "$TEMP_FILE" | cut -d' ' -f1)
    
    # Update Winget manifest
    sed -i "s/PackageVersion: .*/PackageVersion: $VERSION_CLEAN/" "$WINGET_FILE"
    sed -i "s|InstallerUrl: .*|InstallerUrl: $ARCHIVE_URL|" "$WINGET_FILE"
    sed -i "s/InstallerSha256: .*/InstallerSha256: $SHA256/" "$WINGET_FILE"
    
    rm "$TEMP_FILE"
    echo "✅ Winget manifest updated!"
else
    echo "❌ Winget manifest file not found"
fi

echo ""
echo "🎉 All package managers updated!"
echo ""
echo "📋 Next Steps:"
echo ""
echo "🍺 Homebrew:"
echo "   1. Create repository: yuvinraja/homebrew-kando"
echo "   2. Copy Formula: cp distribution/homebrew/kando.rb /path/to/homebrew-kando/Formula/"
echo "   3. Commit and push to homebrew-kando repository"
echo ""
echo "🍫 Chocolatey:"
echo "   1. cd distribution/chocolatey && choco pack"
echo "   2. choco push kando-cli.$VERSION_CLEAN.nupkg -s https://push.chocolatey.org/"
echo ""
echo "🪟 Winget:"
echo "   1. Fork https://github.com/microsoft/winget-pkgs"
echo "   2. Add manifests to manifests/y/yuvinraja/kando-cli/$VERSION_CLEAN/"
echo "   3. Create pull request"
echo ""
echo "📦 Snap:"
echo "   1. snapcraft login"
echo "   2. snapcraft"
echo "   3. snapcraft upload kando-cli_$VERSION_CLEAN_amd64.snap"
echo "   4. snapcraft release kando-cli <revision> stable"
echo ""
echo "📚 Documentation: See DISTRIBUTION.md for detailed instructions"
