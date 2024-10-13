@echo off
setlocal

set ROOTDIR=%CD%

call .\compile.bat

cd %ROOTDIR%

if exist "%ROOTDIR%\release" rmdir /S /Q "%ROOTDIR%\release"

mkdir "%ROOTDIR%\release"

xcopy /E /Y /I "%ROOTDIR%\bin" "%ROOTDIR%\release\bin"
xcopy /Y "%ROOTDIR%\BlitzRC.exe" "%ROOTDIR%\release\"
xcopy /E /Y /I "%ROOTDIR%\cfg" "%ROOTDIR%\release\cfg"
xcopy /E /Y /I "%ROOTDIR%\games" "%ROOTDIR%\release\games"
xcopy /E /Y /I "%ROOTDIR%\help" "%ROOTDIR%\release\help"
xcopy /E /Y /I "%ROOTDIR%\media" "%ROOTDIR%\release\media"
xcopy /E /Y /I "%ROOTDIR%\mediaview" "%ROOTDIR%\release\mediaview"
xcopy /E /Y /I "%ROOTDIR%\samples" "%ROOTDIR%\release\samples"
xcopy /E /Y /I "%ROOTDIR%\tutorials" "%ROOTDIR%\release\tutorials"
xcopy /E /Y /I "%ROOTDIR%\userlibs" "%ROOTDIR%\release\userlibs"

endlocal