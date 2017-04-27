rem nexus path
rem com/zhihuishu/micro/course/openapi-treenity/
goto main

<settings >
...
<localRepository></localRepository>

 <pluginGroups>
  </pluginGroups>
  
<proxies>
</proxies>

 <servers>
 
 需要配置这个   用于mvn脚本认证
 <server>   
		<id>thirdparty</id>   
		<username>admin</username>
		<password>admin123</password>   
	</server>
  </servers>
  
  <mirrors>
  </mirrors>
  
  <profiles>
  </profiles>
  
  <activeProfiles>
    </activeProfiles>
  ...
  </settings >
  
:main
#find "string" openapi-treenity-*.jar

set "TEMPFILE=_temp_1234.text"
for /f "delims=" %%a in ('dir /b/a-d/oN openapi-treenity-*.jar') do (
set jarVersion=%%a
echo "aa"
)
::echo %jarVersion% >%TEMPFILE%
::findstr "1" %TEMPFILE%
rem echo %jarVersion:~6%
set jarVersion=%jarVersion:~17%
set jarVersion=%jarVersion:~0,-4%
echo %jarVersion%

call mvn deploy:deploy-file -DgroupId=com.zhihuishu.micro.course -DartifactId=openapi-treenity -Dversion=%jarversion% -Dpackaging=jar -Dfile=openapi-treenity-%jarversion%.jar -Durl=http://maven.i.zhihuishu.com:8081/nexus/content/repositories/thirdparty/ -DrepositoryId=thirdparty
pause




