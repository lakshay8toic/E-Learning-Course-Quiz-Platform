#!/usr/bin/env bash
# Quick script to compile and run the project
export JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home"
export PATH="$JAVA_HOME/bin:$PATH"

echo "Compiling Java files..."
javac -d . *.java

echo "Starting EduPulse Platform..."
java elearning.Main
