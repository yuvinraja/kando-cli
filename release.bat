@echo off
REM Release preparation script for Kando CLI (Windows)

set VERSION=1.0.0
set RELEASE_DIR=release

echo 🚀 Preparing Kando CLI v%VERSION% for release...

REM Clean previous release
if exist "%RELEASE_DIR%" rmdir /s /q "%RELEASE_DIR%"
mkdir "%RELEASE_DIR%"

REM Build the project
echo 📦 Building project...
call mvn clean package -DskipTests

REM Copy artifacts
echo 📋 Copying release artifacts...
copy target\kando.jar "%RELEASE_DIR%\"
copy target\kando-dist.tar.gz "%RELEASE_DIR%\kando-%VERSION%-dist.tar.gz"
copy target\kando-dist.zip "%RELEASE_DIR%\kando-%VERSION%-dist.zip"

REM Test the JAR
echo 🧪 Testing artifacts...
java -jar "%RELEASE_DIR%\kando.jar" --version

echo ✅ Release preparation complete!
echo.
echo 📁 Release artifacts in .\%RELEASE_DIR%\:
dir "%RELEASE_DIR%"
echo.
echo 🚀 Ready to create GitHub release with these files:
echo    - kando.jar ^(executable JAR^)
echo    - kando-%VERSION%-dist.tar.gz ^(Unix/Linux/macOS distribution^)
echo    - kando-%VERSION%-dist.zip ^(Windows distribution^)
echo.
echo 📋 Next steps:
echo    1. Create git tag: git tag v%VERSION%
echo    2. Push tag: git push origin v%VERSION%
echo    3. GitHub Actions will automatically create the release
echo    4. Update package manager formulas with new checksums
