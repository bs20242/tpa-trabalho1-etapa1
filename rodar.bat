@echo off
setlocal
cd /d "%~dp0"

set "JAVA_CMD="
if defined JAVA_HOME if exist "%JAVA_HOME%\bin\java.exe" set "JAVA_CMD=%JAVA_HOME%\bin\java.exe"

if not defined JAVA_CMD (
    where java >nul 2>nul && set "JAVA_CMD=java"
)

if not defined JAVA_CMD (
    echo [!] Java nao encontrado. Instale o JDK/JRE 11+ ou defina a variavel JAVA_HOME.
    exit /b 1
)

if not exist "bin\app\ProgramaContatos.class" (
    echo [!] Projeto nao compilado. Execute compilar.bat primeiro.
    exit /b 1
)

"%JAVA_CMD%" -cp bin app.ProgramaContatos %*
