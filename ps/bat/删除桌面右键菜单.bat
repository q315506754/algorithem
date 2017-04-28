@echo off
set "menuname=test"

reg delete "HKEY_CLASSES_ROOT\Directory\Background\shell\%menuname%" /f