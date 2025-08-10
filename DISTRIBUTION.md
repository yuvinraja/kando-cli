# Kando CLI Distribution Guide

This document outlines the distribution methods for Kando CLI across different platforms.

## Quick Install

### Homebrew (macOS/Linux) - Recommended
```bash
# Add tap
brew tap yuvinraja/kando

# Install
brew install kando
```

### Direct Download
```bash
# Download latest release
curl -L https://github.com/yuvinraja/kando-cli/releases/latest/download/kando-1.0.0-dist.tar.gz | tar -xz

# Move to PATH
sudo mv kando-1.0.0/bin/kando /usr/local/bin/
```

### Java JAR (All Platforms)
Requires Java 17+:
```bash
# Download JAR
curl -L -O https://github.com/yuvinraja/kando-cli/releases/latest/download/kando.jar

# Run
java -jar kando.jar --help

# Create alias (optional)
echo 'alias kando="java -jar /path/to/kando.jar"' >> ~/.bashrc
```

## Platform-Specific Installation

### Windows

#### Chocolatey
```powershell
choco install kando-cli
```

#### Manual Installation
1. Download `kando-1.0.0-dist.zip` from releases
2. Extract to `C:\Tools\kando`
3. Add `C:\Tools\kando\bin` to your PATH

### Linux

#### Snap
```bash
sudo snap install kando-cli
```

#### AppImage (Coming Soon)
```bash
# Download and run
chmod +x kando-1.0.0-x86_64.AppImage
./kando-1.0.0-x86_64.AppImage --help
```

#### Flatpak (Coming Soon)
```bash
flatpak install flathub com.kando.CLI
flatpak run com.kando.CLI
```

### Docker

#### Run directly
```bash
docker run --rm yuvinraja/kando-cli:latest --help
```

#### With data persistence
```bash
# Create data directory
mkdir -p ~/kando-data

# Run with mounted data
docker run --rm -v ~/kando-data:/app/data yuvinraja/kando-cli:latest new myproject
```

#### Docker Compose
```bash
git clone https://github.com/yuvinraja/kando-cli.git
cd kando-cli
docker-compose run --rm kando --help
```

## Development Installation

### From Source
```bash
# Clone repository
git clone https://github.com/yuvinraja/kando-cli.git
cd kando-cli

# Build
mvn clean package

# Run
java -jar target/kando.jar --help
```

### SDKMAN! (For Developers)
```bash
# Install SDKMAN!
curl -s "https://get.sdkman.io" | bash

# Install Kando (when available)
sdk install kando
```

## Package Manager Status

| Platform | Status | Command |
|----------|--------|---------|
| Homebrew | ✅ Ready | `brew install yuvinraja/kando/kando` |
| Chocolatey | 🚧 In Progress | `choco install kando-cli` |
| Snap | 🚧 In Progress | `snap install kando-cli` |
| Flatpak | 📋 Planned | `flatpak install kando-cli` |
| AUR | 📋 Planned | `yay -S kando-cli` |
| npm | 📋 Planned | `npm install -g kando-cli` |
| SDKMAN! | 📋 Planned | `sdk install kando` |

## Building Distribution Packages

### Prerequisites
- Java 17+
- Maven 3.6+
- Git

### Build All Artifacts
```bash
# Clean and package
mvn clean package

# This creates:
# - target/kando.jar (executable JAR)
# - target/kando-1.0.0-dist.tar.gz (Unix distribution)
# - target/kando-1.0.0-dist.zip (Windows distribution)
```

### Native Images (GraalVM)
```bash
# Install GraalVM
sdk install java 17.0.7-graal

# Build native image
native-image -jar target/kando.jar kando
```

### Docker Image
```bash
# Build image
docker build -t kando-cli:latest .

# Push to registry
docker tag kando-cli:latest yuvinraja/kando-cli:latest
docker push yuvinraja/kando-cli:latest
```

## Release Process

1. **Update Version**: Update version in `pom.xml`
2. **Tag Release**: `git tag v1.0.0 && git push origin v1.0.0`
3. **GitHub Actions**: Automatically builds and creates release
4. **Update Package Managers**: Update formulas/specs with new checksums
5. **Announce**: Update documentation and notify users

## Verification

After installation, verify with:
```bash
kando --version
kando --help
kando new test-project
kando show
```

## Support

- **Issues**: [GitHub Issues](https://github.com/yuvinraja/kando-cli/issues)
- **Discussions**: [GitHub Discussions](https://github.com/yuvinraja/kando-cli/discussions)
- **Documentation**: [README.md](https://github.com/yuvinraja/kando-cli/blob/main/README.md)
