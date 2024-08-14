@echo off
setlocal

set ROOTDIR=%CD%

cd %ROOTDIR%\tests

set BLITZPATH=%ROOTDIR%
set FAILED=0

for /R %%f in (*.bb) do (
    "%BLITZPATH%\bin\blitzcc.exe" -t "%%f" || (echo "%%f failed at least one test" && SET FAILED=1)
)

cd %ROOTDIR%

if %FAILED% == 1 (
    echo "Tests failed"
    endlocal
    exit /b 1
)

echo "Tests passed"

endlocal