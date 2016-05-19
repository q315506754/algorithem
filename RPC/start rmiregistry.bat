cd %CD%/target/classes
:cd %CD%/target/classes/com/jiangli/rpc/rmi/server
:export PATH=$PATH:/%CD%/target/classes
:"C:\Program Files\Java\jdk1.8.0\bin\rmiregistry"
set "classpath=%classpath%%CD%;"
echo "%classpath%"
:set "JAVA_HOME=C:\Program Files\Java\jdk1.8.0"
"C:\Program Files\Java\jdk1.8.0\bin\rmiregistry" 1098
pause



