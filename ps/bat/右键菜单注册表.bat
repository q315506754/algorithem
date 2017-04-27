@echo off
set "menuname=test"
set "exepath=D:\UltraEdit_SC17\Uedit32.exe"
set val="%exepath%"
echo %val%
reg add "HKEY_CLASSES_ROOT\*\shell\%menuname%\Command" /ve /t reg_sz /d "%val% %%1" /f