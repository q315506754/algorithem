call env
cd %deploybase%
for  /f %%a in ('dir /b/a-h/oN *') do (
 echo %%a
 del /q %%a
 rd /q /s %%a
)
cd %CURRENTDIR%