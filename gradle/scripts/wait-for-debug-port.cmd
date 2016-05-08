@echo off
::
:: https://eternallybored.org/misc/netcat/
::
SET ScriptPath="%~dp0"
call :DeQuote ScriptPath
SET NCAT="%ScriptPath%..\tools\win\nc64.exe"

:next
sleep 0.5
%NCAT% -zv localhost %1

::echo error-level: %ERRORLEVEL%
if "%ERRORLEVEL%" NEQ "0" goto :next

goto :EOF

:: ---------------------------------------------------------------------------------------------------------------------
:DeQuote
for /f "delims=" %%A in ('echo %%%1%%') do set %1=%%~A
Goto :eof