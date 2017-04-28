@echo off
set "menuname=test"

reg delete "HKEY_CLASSES_ROOT\*\shell\%menuname%"  /f