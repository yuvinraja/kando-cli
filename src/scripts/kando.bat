@echo off
REM Kando CLI - Windows launcher script

REM Find the directory where this script is located
set SCRIPT_DIR=%~dp0
set JAR_PATH=%SCRIPT_DIR%..\lib\kando.jar

REM Check if Java is available
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 17 or later
    exit /b 1
)

REM Check if JAR exists
if not exist "%JAR_PATH%" (
    echo Error: kando.jar not found at %JAR_PATH%
    exit /b 1
)

REM Execute the application
java -jar "%JAR_PATH%" %*
