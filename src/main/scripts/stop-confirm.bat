@echo off
set port=8080
set flag=1

:Choice
set /p Choice=Do you want to stop port '12046'? [y/n]:

IF /i "%Choice%"=="y" goto step1
IF /i "%Choice%"=="n" goto step2

:step1
  for /f "tokens=1-5" %%i in ('netstat -ano^|findstr ":%port%"') do (
	  set %flag%=2
      echo kill the process %%m who use the port
      taskkill /pid %%m -t -f
      goto q
  )

:q
  if "%flag%"=="1" (
     echo jar is not run!
  ) else (
     echo stop success!
  )

:step2
  echo quit!
  goto step3

:step3