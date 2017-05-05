@echo off
set "t_ATTR=JAVA_HOME"
rem aa
:sds
:set "t_PATH_ATTR=JAVA_HOME2"

:set `"t_PATH_ATTR_REAL=%t_PATH_ATTR%"`
:echo %t_PATH_ATTR_REAL%

:wmic ENVIRONMENT where "name='JAVA_HOME2' and username='<system>'" set VariableValue="%JAVA_HOME2%;%%%t_ATTR%%%\lib;%%%t_ATTR%%%\bin;" 
echo append value is:
:echo %JAVA_HOME2%
echo ;%%%t_ATTR%%%\lib;%%%t_ATTR%%%\bin;
start SystemPropertiesAdvanced
pause
