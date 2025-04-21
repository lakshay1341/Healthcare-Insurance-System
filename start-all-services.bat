@echo off
echo Starting all ISH services in the correct order...

echo 1. Starting SSA Web API...
start "SSA Web API" cmd /c "cd C:\ISH\ISH-SSA-web-API && "C:\Program Files\apache-maven-3.9.9\bin\mvn.cmd" spring-boot:run"
timeout /t 20

echo 2. Starting Config Server Git...
start "Config Server Git" cmd /c "cd C:\ISH\ISH-ConfigServer-GIT && "C:\Program Files\apache-maven-3.9.9\bin\mvn.cmd" spring-boot:run -Dspring-boot.run.main-class=in.lakshay.ISHConfigServerGitApplication"
timeout /t 20

echo 3. Starting Eureka Server...
start "Eureka Server" cmd /c "cd C:\ISH\ISH-EurekaServer && "C:\Program Files\apache-maven-3.9.9\bin\mvn.cmd" spring-boot:run -Dspring-boot.run.main-class=in.lakshay.ISHEurekaServerApplication"
timeout /t 20

echo 4. Starting API Gateway...
start "API Gateway" cmd /c "cd C:\ISH\ISH-CloudAPIGateway && "C:\Program Files\apache-maven-3.9.9\bin\mvn.cmd" spring-boot:run -Dspring-boot.run.main-class=in.lakshay.ISHCloudApiGatewayApplication"
timeout /t 20

echo 5. Starting User Management Service...
start "User Management Service" cmd /c "cd C:\ISH\ISH-UserMgmtService && "C:\Program Files\apache-maven-3.9.9\bin\mvn.cmd" spring-boot:run"
timeout /t 10

echo 6. Starting Application Registration Service...
start "Application Registration Service" cmd /c "cd C:\ISH\ISH-ApplicationRegistrationService && "C:\Program Files\apache-maven-3.9.9\bin\mvn.cmd" spring-boot:run"
timeout /t 10

echo 7. Starting Data Collection Service...
start "Data Collection Service" cmd /c "cd C:\ISH\ISH-DataCollectionService && "C:\Program Files\apache-maven-3.9.9\bin\mvn.cmd" spring-boot:run"
timeout /t 10

echo 8. Starting Eligibility Determination Service...
start "Eligibility Determination Service" cmd /c "cd C:\ISH\ISH-EligibilityDeterminationService && "C:\Program Files\apache-maven-3.9.9\bin\mvn.cmd" spring-boot:run"
timeout /t 10

echo 9. Starting Benefit Issuance Service...
start "Benefit Issuance Service" cmd /c "cd C:\ISH\ISH-BenefitIssuanceService && "C:\Program Files\apache-maven-3.9.9\bin\mvn.cmd" spring-boot:run -Dspring.jpa.hibernate.ddl-auto=none -Dspring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false -Dspring.batch.jdbc.initialize-schema=never -Dspring.jpa.open-in-view=false"
timeout /t 10

echo 10. Starting Correspondence Service...
start "Correspondence Service" cmd /c "cd C:\ISH\ISH-CorrespondenceService && "C:\Program Files\apache-maven-3.9.9\bin\mvn.cmd" spring-boot:run"
timeout /t 10

echo 11. Starting Government Report Service...
start "Government Report Service" cmd /c "cd C:\ISH\ISH-GovernmentReportService && "C:\Program Files\apache-maven-3.9.9\bin\mvn.cmd" spring-boot:run"
timeout /t 10

echo 12. Starting Admin Service...
start "Admin Service" cmd /c "cd C:\ISH\ISH-Admin-Service && "C:\Program Files\apache-maven-3.9.9\bin\mvn.cmd" spring-boot:run"

echo All services have been started!
