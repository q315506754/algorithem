@echo off
set "menuname=bash"
set "exepath=cmd"
set val="%exepath%"
echo %val%
reg add "HKEY_CLASSES_ROOT\*\shell\%menuname%\Command" /ve /t reg_sz /d "%val%" /f