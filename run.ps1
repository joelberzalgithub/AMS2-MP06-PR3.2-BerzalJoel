# run.ps1

# Change to the directory where the script is located
Set-Location $PSScriptRoot

# Set MAVEN_OPTS environment variable
$env:MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.nio=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED"

# Check for the first argument and set it as the main class
$mainClass = $args[0]

Write-Host "Setting MAVEN_OPTS to: $env:MAVEN_OPTS"
Write-Host "Main Class: $mainClass"

# Construct Maven argument for the main class
$mavenMainClassArg = "-Dexec.mainClass=$mainClass"

# Get the rest of the arguments (excluding the first one for mainClass)
$javaArgsArray = $args[1..($args.Length - 1)]

# Escape any double quotes in the arguments
$javaArgsArray = $javaArgsArray -replace '"', '\"'

# Join the arguments with a space, preserving them as separate arguments
$javaArgsStr = $javaArgsArray -join ' '

# Create the exec.args property, enclosing all arguments in double quotes
$execArgsForJava = "-Dexec.args=`"$javaArgsStr`""

Write-Host "Maven Main Class Argument: $mavenMainClassArg"
Write-Host "Java Program Arguments: $javaArgsStr"

# Execute the Maven commands
mvn clean clean compile test
mvn exec:java -PrunMain $mavenMainClassArg $execArgsForJava
