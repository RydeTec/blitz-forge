@echo off
setlocal

set originalDir=%CD%
set scriptDir=%~dp0

cd %scriptDir%

call .\msbuild_init.bat

REM Navigate to the solution directory
cd ..\src\blitzrc

REM Clean the solution
msbuild blitzrc.sln /t:Clean /p:Configuration=BlitzRCDeploy /p:Platform=Win32

REM Rebuild the solution
msbuild blitzrc.sln /t:Rebuild /p:Configuration=BlitzRCDeploy /p:Platform=Win32

cd %originalDir%

endlocal