@echo off
:: windows环境下的启动脚本

:: 启动前判断当前端口是否被占用，如果被占用则先关闭
call stop.bat

set RAN=%random%-%random%-%random%-%random%
set GC_FILE=%RAN%_gc.log
set DUMP_FILE=%RAN%_dump.hprof

echo %DUMP_FILE%
echo %GC_FILE%

:: JVM 启动参数
set JAVA_OPTS=-server -Xms2048m -Xmx2048m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m -XX:SurvivorRatio=8 ^
-XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:LargePageSizeInBytes=128m ^
-XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 ^
-XX:+UseParNewGC -XX:MaxTenuringThreshold=5 -XX:+CMSClassUnloadingEnabled ^
-XX:+TieredCompilation -XX:+ExplicitGCInvokesConcurrent -XX:AutoBoxCacheMax=20000 ^
-verbosegc -XX:+PrintGCDateStamps -XX:+PrintGCDetails -Xloggc:../log/gc/%GC_FILE% ^
-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=../log/dump/%DUMP_FILE% -Dsys.name=knowledge ^
-Dclient.encoding.override=UTF-8 -Dfile.encoding=UTF-8 ^
-Drun.ev=server -Dspring.config.location=./../config/application.properties ^
-Dspring.profiles.active=online

:: jar包
set JAR_FILE=knowledge.jar

:: 创建gc的log文件
:: call gc.bat

echo ----------------JAVA_OPTS start----------------
echo %JAVA_OPTS%
echo ----------------JAVA_OPTS end------------------

echo Choice before
"%JAVA_HOME%\bin\java" -jar %JAVA_OPTS% ./../dist_lib/%JAR_FILE%

echo start success!


 
