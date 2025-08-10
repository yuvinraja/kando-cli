#!/bin/bash
# Homebrew Formula Update Script
# Run this after each release to update the Homebrew formula

set -e

VERSION="$1"
if [ -z "$VERSION" ]; then
    echo "Usage: $0 <version>"
    echo "Example: $0 v1.0.2"
    exit 1
fi

# Remove 'v' prefix if present
VERSION_CLEAN=${VERSION#v}

echo "🍺 Updating Homebrew formula for version $VERSION_CLEAN..."

# Download the release archive to get SHA256
ARCHIVE_URL="https://github.com/yuvinraja/kando-cli/releases/download/${VERSION}/kando-${VERSION}-dist.tar.gz"
TEMP_FILE=$(mktemp)

echo "📥 Downloading release archive..."
curl -L -o "$TEMP_FILE" "$ARCHIVE_URL"

# Calculate SHA256
SHA256=$(sha256sum "$TEMP_FILE" | cut -d' ' -f1)
echo "🔐 SHA256: $SHA256"

# Update the formula
FORMULA_FILE="distribution/homebrew/kando.rb"
sed -i "s|url \".*\"|url \"$ARCHIVE_URL\"|" "$FORMULA_FILE"
sed -i "s|sha256 \".*\"|sha256 \"$SHA256\"|" "$FORMULA_FILE"
sed -i "s|version \".*\"|version \"$VERSION_CLEAN\"|" "$FORMULA_FILE"

# Clean up
rm "$TEMP_FILE"

echo "✅ Formula updated!"
echo ""
echo "📋 Next steps:"
echo "1. Copy the updated formula to your homebrew-kando repository:"
echo "   cp $FORMULA_FILE /path/to/homebrew-kando/Formula/kando.rb"
echo ""
echo "2. Commit and push to homebrew-kando repository:"
echo "   cd /path/to/homebrew-kando"
echo "   git add Formula/kando.rb"
echo "   git commit -m \"Update kando to $VERSION_CLEAN\""
echo "   git push origin main"
echo ""
echo "3. Test installation:"
echo "   brew uninstall kando || true"
echo "   brew install yuvinraja/kando/kando"
echo "   kando --version"
