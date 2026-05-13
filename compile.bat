@echo off
setlocal EnableDelayedExpansion

for %%I in ("%~dp0.") do set "ROOTDIR=%%~fI"

echo Compiling BlitzForge Toolchain...
call "%ROOTDIR%\scripts\msbuild_init.bat"

cd /d "%ROOTDIR%"

call "%ROOTDIR%\scripts\msbuild_blitzforge.bat"

cd /d "%ROOTDIR%"
endlocal
