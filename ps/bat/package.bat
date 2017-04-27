set "java_home=C:\Program Files\Java\jdk1.6.0_45"
echo %java_home%
java -version
set "batPath=%cd%"
set "projectPath=D:\\projects\\oversea\\createcourse"
set "targetName=CreateCourse.war"
set "targetPath=%projectPath%\\target\\%targetName%"

echo generated path is :%targetPath%
echo destination path is: %batPath%\%targetName%

del "%batPath%\%targetName%"
echo  deleted

cd %projectPath%
D:

call mvn clean package  -DskipTests
echo maven over...

copy "%targetPath%" "%batPath%\%targetName%" /Y

cd %batPath%
c:
pause



