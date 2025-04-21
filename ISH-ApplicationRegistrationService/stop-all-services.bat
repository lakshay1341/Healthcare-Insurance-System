@echo off
echo ===================================================
echo Stopping ISH Microservices
echo ===================================================

echo.
echo Stopping all Java processes...
taskkill /F /IM java.exe

echo.
echo ===================================================
echo All services stopped successfully!
echo ===================================================
echo.
echo Press any key to exit...
pause > nul
