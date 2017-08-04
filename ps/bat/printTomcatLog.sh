#!/bin/bash
DISLINE=${1}
if [ -z ${1} ];then
 echo "set200"
 DISLINE=200
else
 echo "remain"
fi
echo $DISLINE

tail -f -n $DISLINE /data/apache-tomcat-7.0.68/logs/catalina.out