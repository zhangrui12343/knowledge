@echo off
:: windows环境下的启动脚本

:: 启动前判断当前端口是否被占用，如果被占用则先关闭
call stop.bat

:: JVM 启动参数
set JAVA_OPTS=-Xms1024m -Xmx2048m -Xss256k -verbosegc -XX:+PrintGCDateStamps -XX:+PrintGCDetails^
 -Xloggc:../log/gc/gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=../log/dump/dump.hprof^
 -Dclient.encoding.override=UTF-8 -Dfile.encoding=UTF-8 -Ddb.config.path=./../../config/DBConfig.json^
 -Drun.ev=server -Dspring.config.location=./../config/application-online.properties^
 -Dspring.profiles.active=online
:: 日志文件夹的路径
set BASE_PATH=./../log
:: GC log文件
set GC_LOG=./../log/gc/gc.log
set JAR_FILE=knowledge.jar

setlocal
  ::获取bin/stat.bat的父级目录
  for %%d in ("%~dp0.") do set Directory=%%~fd
  echo Directory=%Directory%
  for %%d in ("%~dp0..") do set ParentDirectory=%%~fd
  set BASE_PATH="%ParentDirectory%"
  echo BASE_PATH = %BASE_PATH%
  cd %BASE_PATH%
  
  ::如果文件夹不存在，则创建文件夹
  if not exist log md log
  
  cd %BASE_PATH%\log
  
  ::创建gc和dump的文件
  if not exist gc md gc
  if not exist dump md dump
  
  cd %BASE_PATH%\log\gc
  set GC_LOG=%BASE_PATH%\log\gc\gc.log
  
  :: 处理gc.log 不存在则创建
  if not exist %GC_LOG% (
    type nul>%GC_LOG%
	echo %GC_LOG%, file not exist, has created!
  ) else (
    echo %GC_LOG%, already exist, not need create!
  )
endlocal

echo ----------------JAVA_OPTS start----------------
echo %JAVA_OPTS%
echo ----------------JAVA_OPTS end------------------

:Choice
set /p Choice=Is run in background? [y/n]:

IF /i "%Choice%"=="y" goto BACK
IF /i "%Choice%"=="n" goto BEFORE

:BACK
  echo Choice back
  START "app" "%JAVA_HOME%\bin\javaw" -jar %JAVA_OPTS% ./../dist_lib/%JAR_FILE%
  goto NEXT

:BEFORE
  echo Choice before
  "%JAVA_HOME%\bin\java" -jar %JAVA_OPTS% ./../dist_lib/%JAR_FILE%
  goto NEXT

:NEXT
  echo start success!


 
