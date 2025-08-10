#!/bin/bash
# Build script for Kando CLI

set -e  # Exit on any error

echo "🚀 Building Kando CLI..."

# Clean and build
echo "📦 Building with Maven..."
mvn clean package -q -DskipTests

# Check if build was successful
if [ -f "target/kando.jar" ]; then
    echo "✅ Build successful!"
    echo "📁 Artifacts created:"
    echo "   - target/kando.jar (executable JAR)"
    
    if [ -f "target/kando-1.0.0-dist.tar.gz" ]; then
        echo "   - target/kando-1.0.0-dist.tar.gz (distribution)"
    fi
    
    if [ -f "target/kando-1.0.0-dist.zip" ]; then
        echo "   - target/kando-1.0.0-dist.zip (distribution)"
    fi
    
    echo ""
    echo "🧪 Testing build..."
    java -jar target/kando.jar --version
    
    echo ""
    echo "✨ Ready to use! Try:"
    echo "   java -jar target/kando.jar --help"
    
else
    echo "❌ Build failed!"
    exit 1
fi
