@echo off
echo ========================================
echo HOSPITAL MANAGEMENT SYSTEM
echo ========================================
echo.

REM Set Java environment
set JAVA_HOME=C:\Program Files\Java\jdk-25
set PATH=%JAVA_HOME%\bin;%PATH%

echo Starting Hospital Management System...
echo.
echo Login credentials:
echo   Username: admin
echo   Password: admin123
echo.

REM Compile sources (output to current directory to create hospital/*.class)
javac -cp ".;lib\mysql-connector-java-8.1.0.jar" -d . *.java

java -cp ".;lib\mysql-connector-java-8.1.0.jar" hospital.LoginPage

pause
