#!/bin/bash

# run.sh

# Change the working directory to where the script is located
cd "$(dirname "$0")"

# Set MAVEN_OPTS environment variable
export MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.nio=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED"

# Check for the first argument and set it as the main class
mainClass="$1"

echo "Setting MAVEN_OPTS to: $MAVEN_OPTS"
echo "Main Class: $mainClass"

# Remove the first argument (mainClass) so the rest can be passed to Maven
shift

# Construct Maven argument for the main class
mavenMainClassArg="-Dexec.mainClass=$mainClass"

# Use "$@" to properly handle all arguments intended for the Java program
javaArgs="$@"

# Join the arguments into a single string with proper escaping
javaArgsStr=$(printf "%q " "$javaArgs")

# Construct argument for passing additional args to Java program
execArgsForJava="-Dexec.args=$javaArgsStr"

echo "Maven Main Class Argument: $mavenMainClassArg"
echo "Java Program Arguments: $javaArgsStr"

# Execute mvn command
mvn clean clean compile test
mvn exec:java $mavenMainClassArg $execArgsForJava
