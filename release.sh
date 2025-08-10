#!/bin/bash
# Release preparation script for Kando CLI

set -e

VERSION="1.0.0"
RELEASE_DIR="release"

echo "🚀 Preparing Kando CLI v${VERSION} for release..."

# Clean previous release
rm -rf "$RELEASE_DIR"
mkdir -p "$RELEASE_DIR"

# Build the project
echo "📦 Building project..."
mvn clean package -DskipTests

# Copy artifacts
echo "📋 Copying release artifacts..."
cp target/kando.jar "$RELEASE_DIR/"
cp target/kando-dist.tar.gz "$RELEASE_DIR/kando-${VERSION}-dist.tar.gz"
cp target/kando-dist.zip "$RELEASE_DIR/kando-${VERSION}-dist.zip"

# Generate checksums
echo "🔐 Generating checksums..."
cd "$RELEASE_DIR"
sha256sum *.jar *.tar.gz *.zip > checksums.txt
cd ..

# Test the JAR
echo "🧪 Testing artifacts..."
java -jar "$RELEASE_DIR/kando.jar" --version

echo "✅ Release preparation complete!"
echo ""
echo "📁 Release artifacts in ./$RELEASE_DIR/:"
ls -la "$RELEASE_DIR/"
echo ""
echo "🚀 Ready to create GitHub release with these files:"
echo "   - kando.jar (executable JAR)"
echo "   - kando-${VERSION}-dist.tar.gz (Unix/Linux/macOS distribution)"
echo "   - kando-${VERSION}-dist.zip (Windows distribution)"
echo "   - checksums.txt (SHA256 checksums)"
echo ""
echo "📋 Next steps:"
echo "   1. Create git tag: git tag v${VERSION}"
echo "   2. Push tag: git push origin v${VERSION}"
echo "   3. GitHub Actions will automatically create the release"
echo "   4. Update package manager formulas with new checksums"
