@echo off
setlocal

set "MAVEN_PROJECTBASEDIR=%~dp0"
set "WRAPPER_DIR=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper"
set "PROPERTIES_FILE=%WRAPPER_DIR%\maven-wrapper.properties"
set "MAVEN_VERSION=3.9.11"
set "MAVEN_HOME=%WRAPPER_DIR%\apache-maven-%MAVEN_VERSION%"

if not exist "%MAVEN_HOME%\bin\mvn.cmd" (
  echo Maven Wrapper: baixando Apache Maven %MAVEN_VERSION%...
  set "MAVEN_ZIP=%WRAPPER_DIR%\apache-maven-%MAVEN_VERSION%-bin.zip"
  powershell -NoProfile -ExecutionPolicy Bypass -Command "Invoke-WebRequest -UseBasicParsing -Uri 'https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/%MAVEN_VERSION%/apache-maven-%MAVEN_VERSION%-bin.zip' -OutFile '%MAVEN_ZIP%'"
  if errorlevel 1 exit /b 1
  powershell -NoProfile -ExecutionPolicy Bypass -Command "Expand-Archive -LiteralPath '%MAVEN_ZIP%' -DestinationPath '%WRAPPER_DIR%' -Force"
  if errorlevel 1 exit /b 1
  del /q "%MAVEN_ZIP%"
)

call "%MAVEN_HOME%\bin\mvn.cmd" %*
exit /b %errorlevel%
