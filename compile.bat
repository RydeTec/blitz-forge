@echo off
setlocal EnableDelayedExpansion

set ROOTDIR=%CD%

echo Compiling BlitzForge Toolchain...
call .\scripts\msbuild_init.bat

cd %ROOTDIR%

call .\scripts\msbuild_blitzforge.bat

cd %ROOTDIR%
endlocal