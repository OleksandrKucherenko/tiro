@echo off
::
:: cURL is a part of GIT for Windows
::
SET ScriptPath="%~dp0"
call :DeQuote ScriptPath
`

:: update PATH variable by own folder with binaries
SET mypath=%~dp0
SET "PATH=%mypath:~0,-1%\..\tools\win;%PATH%;"

SET CURL="curl.exe"
SET CallParams=-i --connect-timeout 1 --max-time 5

:: get version of the API
echo.
echo Call: http://localhost:9191/api/v1/security/version
%CURL% %CallParams% http://localhost:9191/api/v1/security/version

echo DONE.

goto :EOF

:: ---------------------------------------------------------------------------------------------------------------------
:DeQuote
for /f "delims=" %%A in ('echo %%%1%%') do set %1=%%~A
Goto :eof