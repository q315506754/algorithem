set "targetName=CreateCourse.war"
scp %targetName% root@192.168.9.234:/usr/local/jboss/server/default/deploy/%targetName%
ssh root@192.168.9.234