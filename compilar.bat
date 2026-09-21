@echo off
setlocal
cd /d "%~dp0"

set "JAVAC_CMD="
if defined JAVA_HOME if exist "%JAVA_HOME%\bin\javac.exe" set "JAVAC_CMD=%JAVA_HOME%\bin\javac.exe"

if not defined JAVAC_CMD (
    where javac >nul 2>nul && set "JAVAC_CMD=javac"
)

if not defined JAVAC_CMD (
    echo [!] JDK nao encontrado. Instale o JDK 11+ ou defina a variavel JAVA_HOME.
    exit /b 1
)

if not exist bin mkdir bin

echo [*] Compilando o projeto...
"%JAVAC_CMD%" -encoding UTF-8 -d bin src\colecao\*.java src\dominio\*.java src\app\*.java src\testes\*.java
if %ERRORLEVEL% equ 0 (
    echo [+] Compilacao concluida com sucesso em bin/!
) else (
    echo [!] Erro na compilacao.
)
