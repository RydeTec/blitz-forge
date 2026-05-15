@echo off
setlocal

for %%I in ("%~dp0.") do set "ROOTDIR=%%~fI"
set "ARCHIVE_BASENAME=blitzforge-windows-x64"
set "ARCHIVE_PATH=%ROOTDIR%\release\%ARCHIVE_BASENAME%.zip"

call "%ROOTDIR%\compile.bat"
if errorlevel 1 goto :fail

cd /d "%ROOTDIR%"

if exist "%ROOTDIR%\release" rmdir /S /Q "%ROOTDIR%\release"

mkdir "%ROOTDIR%\release"

call :copy_tree_if_exists "%ROOTDIR%\bin" "%ROOTDIR%\release\bin"
if errorlevel 1 goto :fail
call :copy_tree_if_exists "%ROOTDIR%\cfg" "%ROOTDIR%\release\cfg"
if errorlevel 1 goto :fail
call :copy_tree_if_exists "%ROOTDIR%\games" "%ROOTDIR%\release\games"
if errorlevel 1 goto :fail
call :copy_tree_if_exists "%ROOTDIR%\help" "%ROOTDIR%\release\help"
if errorlevel 1 goto :fail
call :copy_tree_if_exists "%ROOTDIR%\media" "%ROOTDIR%\release\media"
if errorlevel 1 goto :fail
call :copy_tree_if_exists "%ROOTDIR%\mediaview" "%ROOTDIR%\release\mediaview"
if errorlevel 1 goto :fail
call :copy_tree_if_exists "%ROOTDIR%\samples" "%ROOTDIR%\release\samples"
if errorlevel 1 goto :fail
call :copy_tree_if_exists "%ROOTDIR%\tutorials" "%ROOTDIR%\release\tutorials"
if errorlevel 1 goto :fail
call :copy_tree_if_exists "%ROOTDIR%\userlibs" "%ROOTDIR%\release\userlibs"
if errorlevel 1 goto :fail

REM Copy the compiled vscode extension
if exist "%ROOTDIR%\..\..\extras\vscode-blitz-forge" (
    call "%ROOTDIR%\..\..\extras\vscode-blitz-forge\compile.bat"
    if errorlevel 1 goto :fail

    cd /d "%ROOTDIR%"
    
    if exist "%ROOTDIR%\..\..\extras\vscode-blitz-forge\*.vsix" (
        xcopy /Y "%ROOTDIR%\..\..\extras\vscode-blitz-forge\*.vsix" "%ROOTDIR%\release\" >nul
        if errorlevel 1 goto :fail
    )
)

REM Create a README.txt file
(
    echo BlitzForge is a compiler for an enhanced version of the Blitz3D language.
    echo It is a fork of the Blitz3D compiler and adds support for BlitzForge commands and syntax.
    echo You can develop with BlitzForge in Visual Studio Code by installing the bundled .vsix extension.
) > "%ROOTDIR%\release\README.txt"

set "PACKAGE_TMP=%TEMP%\blitzforge-package-%RANDOM%-%RANDOM%"
set "PACKAGE_ROOT=%PACKAGE_TMP%\%ARCHIVE_BASENAME%"
mkdir "%PACKAGE_ROOT%"
xcopy /E /Y /I "%ROOTDIR%\release\*" "%PACKAGE_ROOT%\" >nul
if errorlevel 1 goto :fail
if exist "%ARCHIVE_PATH%" del /Q "%ARCHIVE_PATH%"
powershell -NoProfile -Command "Compress-Archive -Path '%PACKAGE_ROOT%' -DestinationPath '%ARCHIVE_PATH%' -CompressionLevel Optimal"
if errorlevel 1 (
    goto :fail
)
rd /S /Q "%PACKAGE_TMP%"
echo Created release archive: "%ARCHIVE_PATH%"

endlocal
exit /b 0

:copy_tree_if_exists
set "COPY_SOURCE=%~1"
set "COPY_DEST=%~2"
if not exist "%COPY_SOURCE%\" exit /b 0
xcopy /E /Y /I "%COPY_SOURCE%" "%COPY_DEST%" >nul
if errorlevel 1 (
    echo Failed to copy "%COPY_SOURCE%" into "%COPY_DEST%"
    exit /b 1
)
exit /b 0

:fail
if exist "%PACKAGE_TMP%" rd /S /Q "%PACKAGE_TMP%"
endlocal
exit /b 1
