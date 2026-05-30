@echo off
setlocal EnableDelayedExpansion

for %%I in ("%~dp0.") do set "ROOTDIR=%%~fI"

set BLITZPATH=%ROOTDIR%
set FAILED=0
set SAMPLE=%ROOTDIR%\tests\NumberTest.bb

call :expect_isolated_cli "isolated -h works without DLLs" "Usage:" -h
call :expect_isolated_cli "isolated -v works without DLLs" "Compiler version:" -v
call :expect_isolated_cli "isolated bare invocation works without DLLs" ""
call :expect_success "host alias compiles NumberTest" -c +q -target host "%SAMPLE%"
call :expect_success "native Windows target compiles NumberTest" -c +q -target windows-x86 "%SAMPLE%"
call :expect_failure "foreign macOS target is rejected" "Unsupported -target" -c +q -target macos-arm64 "%SAMPLE%"

rem --- compiler diagnostic contract (human terminal output vs IDE machine format) ---
rem Generate a broken fixture OUTSIDE tests\ so the -t loop and corpus sweep ignore it.
set "DIAG_BB=%TEMP%\blitzforge-diag-%RANDOM%-%RANDOM%.bb"
> "!DIAG_BB!" echo For i = 1 To 10
call :expect_failure "terminal diagnostic shows human error: prefix" "error:" -c +q "!DIAG_BB!"
call :expect_failure "EOF error names end of file, not a control byte" "end of file" -c +q "!DIAG_BB!"
call :expect_ide_format "IDE machine format preserved under blitzide" "!DIAG_BB!"
del /q "!DIAG_BB!" >nul 2>&1

rem --- runtime crash diagnostic: native faults are labeled by type, not all "Stack overflow!" ---
rem The fixture panics and exits non-zero by design, so it lives outside tests\.
call :expect_failure "native divide-by-zero is labeled correctly" "Integer divide by zero" -t "%ROOTDIR%\scripts\fixtures\divzero.bb"

cd /d "%ROOTDIR%\tests"

for /R %%f in (*.bb) do (
    "%BLITZPATH%\bin\blitzcc.exe" -t "%%f" || (echo "%%f failed at least one test" && SET FAILED=1)
    echo.
)

cd /d "%ROOTDIR%"

if !FAILED! == 1 (
    echo "Tests failed"
    endlocal
    exit /b 1
)

echo "Tests passed"

endlocal
exit /b 0

:expect_success
set "LABEL=%~1"
shift /1
"%BLITZPATH%\bin\blitzcc.exe" %1 %2 %3 %4 %5 %6 %7 %8 %9 >nul 2>&1
if errorlevel 1 (
    echo target contract FAILED: !LABEL!
    set FAILED=1
)
exit /b 0

:expect_failure
set "LABEL=%~1"
set "EXPECTED=%~2"
shift /1
shift /1
set "TARGET_LOG=%TEMP%\blitzforge-target-%RANDOM%-%RANDOM%.log"
"%BLITZPATH%\bin\blitzcc.exe" %1 %2 %3 %4 %5 %6 %7 %8 %9 >"!TARGET_LOG!" 2>&1
set "TARGET_RC=!ERRORLEVEL!"
if !TARGET_RC! EQU 0 (
    echo target contract FAILED: !LABEL! unexpectedly succeeded
    set FAILED=1
) else (
    findstr /C:"!EXPECTED!" "!TARGET_LOG!" >nul
    if errorlevel 1 (
        echo target contract FAILED: !LABEL! did not report the unsupported-target error
        type "!TARGET_LOG!"
        set FAILED=1
    )
)
del /q "!TARGET_LOG!" >nul 2>&1
exit /b 0

:expect_isolated_cli
set "LABEL=%~1"
set "EXPECTED=%~2"
shift /1
shift /1
set "CLI_DIR=%TEMP%\blitzforge-cli-%RANDOM%-%RANDOM%"
set "CLI_LOG=%TEMP%\blitzforge-cli-%RANDOM%-%RANDOM%.log"
mkdir "!CLI_DIR!" >nul 2>&1
copy /y "%BLITZPATH%\bin\blitzcc.exe" "!CLI_DIR!\blitzcc.exe" >nul
if errorlevel 1 (
    echo CLI contract FAILED: !LABEL! could not stage blitzcc.exe
    set FAILED=1
    rmdir /s /q "!CLI_DIR!" >nul 2>&1
    exit /b 0
)
pushd "!CLI_DIR!" >nul
"!CLI_DIR!\blitzcc.exe" %1 %2 %3 %4 %5 %6 %7 %8 %9 >"!CLI_LOG!" 2>&1
set "CLI_RC=!ERRORLEVEL!"
popd >nul
if !CLI_RC! NEQ 0 (
    echo CLI contract FAILED: !LABEL! returned !CLI_RC!
    type "!CLI_LOG!"
    set FAILED=1
) else if defined EXPECTED (
    findstr /C:"!EXPECTED!" "!CLI_LOG!" >nul
    if errorlevel 1 (
        echo CLI contract FAILED: !LABEL! did not print !EXPECTED!
        type "!CLI_LOG!"
        set FAILED=1
    )
)
del /q "!CLI_LOG!" >nul 2>&1
rmdir /s /q "!CLI_DIR!" >nul 2>&1
exit /b 0

:expect_ide_format
rem With blitzide set, the compile-error output must stay the machine-readable
rem format the IDE parses (no human "error:" prefix). The message ("Expecting...")
rem appears in both modes; the "error:" prefix only in terminal mode.
set "LABEL=%~1"
set "IDE_BB=%~2"
set "IDE_LOG=%TEMP%\blitzforge-ide-%RANDOM%-%RANDOM%.log"
set "blitzide=1"
"%BLITZPATH%\bin\blitzcc.exe" -c +q "!IDE_BB!" >"!IDE_LOG!" 2>&1
set "blitzide="
findstr /C:"Expecting" "!IDE_LOG!" >nul
if errorlevel 1 (
    echo IDE contract FAILED: !LABEL! produced no diagnostic
    type "!IDE_LOG!"
    set FAILED=1
)
findstr /C:"error:" "!IDE_LOG!" >nul
if not errorlevel 1 (
    echo IDE contract FAILED: !LABEL! leaked the human "error:" prefix into IDE output
    type "!IDE_LOG!"
    set FAILED=1
)
del /q "!IDE_LOG!" >nul 2>&1
exit /b 0
