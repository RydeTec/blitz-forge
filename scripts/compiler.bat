@echo off
setlocal

set ROOTDIR=%1
set BLITZPATH=%ROOTDIR%
set SRCPATH=%2
set SRCFILE=%3

cd %SRCPATH%

:: Shift the arguments so %1 now refers to what %2 was originally, etc.
shift
shift
shift
shift

:: Initialize an empty string to collect the remaining arguments
set "ARGS="

:: Loop through the remaining arguments and append them to ARGS
:loop
if "%~1"=="" goto endloop
set "ARGS=%ARGS% %1"
shift
goto loop

:endloop

:: Now ARGS contains all the arguments except for the first one
"%BLITZPATH%\bin\blitzcc.exe" %ARGS% %SRCFILE%

cd %ROOTDIR%

endlocal