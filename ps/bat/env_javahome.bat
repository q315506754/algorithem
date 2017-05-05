set "t_DIR=c:\dd"
set "t_ATTR=JAVA_HOME2"
wmic ENVIRONMENT where "name='%t_ATTR%'" delete
wmic ENVIRONMENT create name="%t_ATTR%",username="<system>",VariableValue="%t_DIR%"