#!/bin/sh

projectName="knowledge"

echo "shutdown : $projectName"
pidlist=`ps -ef|grep ${projectName}.jar|grep -v grep|awk '{print $2}'`
if [ "$pidlist" = "" ]
	then
		echo "no $projectName pid alive !"
else
	echo "$projectName pid list : $pidlist"
	kill -9 $pidlist
	echo "kill $projectName success $pidlist"
	echo "shutdown $projectName success"
fi
