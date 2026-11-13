@echo off
cd /d "%~dp0\.."
echo ===============================================
echo    Compiling EduPulse Java Source Code...
echo ===============================================
javac -d . *.java

echo.
echo ===============================================
echo    Starting Server and Opening in Chrome...
echo ===============================================
start http://localhost:8080/
java elearning.Main
pause
