@echo off
set port=8080
set flag=1


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
