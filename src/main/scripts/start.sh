#!/bin/sh

projectName="knowledge"

echo "startup : $projectName"
echo "step 1 : shutdown alive $projectName server"
pidlist=`ps -ef|grep ${projectName}.jar|grep -v grep|awk '{print $2}'`
if [ "$pidlist" = "" ]
        then
                echo "no $projectName server pid alive !"
else
        echo "$projectName server pid list : $pidlist"
        kill -9 $pidlist
        echo "kill $projectName server success $pidlist"
fi
echo "step 2 : startup $projectName server"
executeDir=`dirname $0`
echo "executeDir : $executeDir"
cd $executeDir
cd ../dist_lib
setsid java -jar -Dclient.encoding.override=UTF-8 -Dfile.encoding=UTF-8 -Dspring.config.location=./../config/application.properties -Dspring.profiles.active=online ./${projectName}.jar >/dev/null &
echo "startup $projectName server success"
