@echo off
setlocal

set ROOTDIR=%CD%

call .\compile.bat

cd %ROOTDIR%

if exist "%ROOTDIR%\release" rmdir /S /Q "%ROOTDIR%\release"

mkdir "%ROOTDIR%\release"

xcopy /E /Y /I "%ROOTDIR%\bin" "%ROOTDIR%\release\bin"
xcopy /E /Y /I "%ROOTDIR%\cfg" "%ROOTDIR%\release\cfg"
xcopy /E /Y /I "%ROOTDIR%\games" "%ROOTDIR%\release\games"
xcopy /E /Y /I "%ROOTDIR%\help" "%ROOTDIR%\release\help"
xcopy /E /Y /I "%ROOTDIR%\media" "%ROOTDIR%\release\media"
xcopy /E /Y /I "%ROOTDIR%\mediaview" "%ROOTDIR%\release\mediaview"
xcopy /E /Y /I "%ROOTDIR%\samples" "%ROOTDIR%\release\samples"
xcopy /E /Y /I "%ROOTDIR%\tutorials" "%ROOTDIR%\release\tutorials"
xcopy /E /Y /I "%ROOTDIR%\userlibs" "%ROOTDIR%\release\userlibs"

REM Copy the compiled vscode extension
if exist "%ROOTDIR%\..\..\extras\vscode-blitz-forge" (
    call "%ROOTDIR%\..\..\extras\vscode-blitz-forge\compile.bat"

    cd %ROOTDIR%
    
    xcopy /Y "%ROOTDIR%\..\..\extras\vscode-blitz-forge\*.vsix" "%ROOTDIR%\release\"
)

REM Create a README.txt file
echo "BlitzForge is a compiler for an enhanced version of the Blitz3D language. It is a fork of the Blitz3D compiler and adds support for the BlitzForge commands and syntax. You can develop with BlitzForge in Visual Studio Code by installing the .vsix extension. Press Ctrl+Shift+P and search for vsix to install the extension." > "%ROOTDIR%\release\README.txt"

endlocal