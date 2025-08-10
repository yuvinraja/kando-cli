@echo off
REM Build script for Kando CLI (Windows)

echo 🚀 Building Kando CLI...

REM Clean and build
echo 📦 Building with Maven...
call mvn clean package -q -DskipTests

REM Check if build was successful
if exist "target\kando.jar" (
    echo ✅ Build successful!
    echo 📁 Artifacts created:
    echo    - target\kando.jar ^(executable JAR^)
    
    if exist "target\kando-1.0.0-dist.tar.gz" (
        echo    - target\kando-1.0.0-dist.tar.gz ^(distribution^)
    )
    
    if exist "target\kando-1.0.0-dist.zip" (
        echo    - target\kando-1.0.0-dist.zip ^(distribution^)
    )
    
    echo.
    echo 🧪 Testing build...
    java -jar target\kando.jar --version
    
    echo.
    echo ✨ Ready to use! Try:
    echo    java -jar target\kando.jar --help
    
) else (
    echo ❌ Build failed!
    exit /b 1
)
