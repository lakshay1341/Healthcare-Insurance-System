@echo off
echo Stopping all ISH services...

echo Killing all Java processes...
taskkill /F /IM java.exe

echo All services have been stopped!
