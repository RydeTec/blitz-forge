@echo off
setlocal EnableDelayedExpansion

for %%I in ("%~dp0.") do set "ROOTDIR=%%~fI"

set BLITZPATH=%ROOTDIR%
set FAILED=0
set SAMPLE=%ROOTDIR%\tests\NumberTest.bb

call :expect_success "host alias compiles NumberTest" -c +q -target host "%SAMPLE%"
call :expect_success "native Windows target compiles NumberTest" -c +q -target windows-x86 "%SAMPLE%"
call :expect_failure "foreign macOS target is rejected" "Unsupported -target" -c +q -target macos-arm64 "%SAMPLE%"

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
