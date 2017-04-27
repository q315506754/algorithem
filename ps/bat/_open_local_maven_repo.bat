for /f "delims=" %%a in ('dir /b/a-d/oN *.version') do (
set jarVersion=%%a
)

:set jarVersion=%jarVersion:~17%
set jarVersion=%jarVersion:~0,-8%

start  /min D:\Portable\zhihuishu\m2repos\com\zhihuishu\micro\course\openapi-treenity\%jarVersion%