@echo off
echo ===================================================
echo Starting ISH Microservices
echo ===================================================

echo.
echo Starting SSA Web API...
start "SSA Web API" cmd /c "cd /d C:\ISH\ISH-SSA-web-API && mvn spring-boot:run"
timeout /t 10

echo.
echo Starting Config Server...
start "Config Server" cmd /c "cd /d C:\ISH\ISH-ConfigServer-GIT && mvn spring-boot:run"
timeout /t 15

echo.
echo Starting Eureka Server...
start "Eureka Server" cmd /c "cd /d C:\ISH\ISH-EurekaServer && mvn spring-boot:run"
timeout /t 15

echo.
echo Starting Cloud API Gateway...
start "Cloud API Gateway" cmd /c "cd /d C:\ISH\ISH-CloudAPIGateway && mvn spring-boot:run"
timeout /t 15

echo.
echo Starting User Management Service...
start "User Management Service" cmd /c "cd /d C:\ISH\ISH-UserMgmtService && mvn spring-boot:run"
timeout /t 15

echo.
echo Starting Application Registration Service...
start "Application Registration Service" cmd /c "cd /d C:\ISH\ISH-ApplicationRegistrationService && mvn spring-boot:run"
timeout /t 15

echo.
echo Starting Data Collection Service...
start "Data Collection Service" cmd /c "cd /d C:\ISH\ISH-DataCollectionService && mvn spring-boot:run"
timeout /t 15

echo.
echo Starting Eligibility Determination Service...
start "Eligibility Determination Service" cmd /c "cd /d C:\ISH\ISH-EligibilityDeterminationService && mvn spring-boot:run"
timeout /t 15

echo.
echo Starting Benefit Issuance Service...
start "Benefit Issuance Service" cmd /c "cd /d C:\ISH\ISH-BenefitIssuanceService && mvn spring-boot:run"
timeout /t 15

echo.
echo Starting Correspondence Service...
start "Correspondence Service" cmd /c "cd /d C:\ISH\ISH-CorrespondenceService && mvn spring-boot:run"
timeout /t 15

echo.
echo Starting Government Report Service...
start "Government Report Service" cmd /c "cd /d C:\ISH\ISH-GovernmentReportService && mvn spring-boot:run"
timeout /t 15

echo.
echo Starting Admin Service...
start "Admin Service" cmd /c "cd /d C:\ISH\ISH-Admin-Service && mvn spring-boot:run"
timeout /t 15

echo.
echo ===================================================
echo All services started successfully!
echo ===================================================
echo.
echo Access the API Gateway at: http://localhost:7777
echo Access Eureka Dashboard at: http://localhost:8761
echo.
echo Press any key to exit...
pause > nul
