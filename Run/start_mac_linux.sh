#!/usr/bin/env bash
# Script to compile, start the platform, and automatically open in Google Chrome
cd "$(dirname "$0")/.."

# Check if JDK 17 is available on macOS default path
if [ -d "/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home" ]; then
    export JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home"
    export PATH="$JAVA_HOME/bin:$PATH"
fi

echo "==============================================="
echo "   Compiling EduPulse Java Source Code...     "
echo "==============================================="
javac -d . *.java

echo ""
echo "==============================================="
echo "   Starting Server & Opening in Chrome...     "
echo "==============================================="

# Automatically open Chrome to localhost:8080 after 1 second in background
(
    sleep 1
    if [[ "$OSTYPE" == "darwin"* ]]; then
        open -a "Google Chrome" "http://localhost:8080/" 2>/dev/null || open "http://localhost:8080/"
    elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
        google-chrome "http://localhost:8080/" 2>/dev/null || xdg-open "http://localhost:8080/"
    fi
) &

# Run the Java application
java elearning.Main
