@echo off

:: 日志文件夹的路径
set BASE_PATH=./../log
:: GC log文件
set GC_LOG=./../log/gc/gc.log

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
    :: 已存在gc.log则备份原gc文件
    ren %GC_LOG% %date:~0,4%%date:~5,2%%date:~8,2%%time:~0,2%%time:~3,2%%time:~6,2%gc.log
    echo %GC_LOG%, already exist, not need create!
  )
endlocal