# Insurance System for Health (ISH)

A comprehensive microservices-based health insurance management system designed to handle the entire lifecycle of health insurance applications.

## 📋 Overview

The Insurance System for Health (ISH) is a modern, cloud-native application built using Spring Boot that manages the complete lifecycle of health insurance applications - from registration to benefit issuance. The system follows a microservices architecture pattern for scalability, resilience, and maintainability.

## 🏗️ Architecture

The system consists of the following microservices:

1. **User Management Service**: Handles user and worker registration, authentication, and profile management
2. **Application Registration Service**: Manages citizen applications for health insurance
3. **Data Collection Service**: Collects and manages applicant data including income, education, and dependents
4. **Eligibility Determination Service**: Determines eligibility for health insurance based on collected data
5. **Benefit Issuance Service**: Manages the issuance of benefits to eligible applicants
6. **Correspondence Service**: Handles communication with applicants
7. **Government Report Service**: Generates reports for government agencies
8. **Admin Service**: Provides administrative functions for managing plans and system configuration
9. **Cloud API Gateway**: Routes requests to appropriate microservices
10. **Eureka Server**: Service discovery for microservices
11. **Config Server**: Centralized configuration management
12. **SSA Web API**: Simulates the Social Security Administration web service for SSN validation

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher

### Database Setup

1. Create a MySQL database named `InsuranceSystemForHealth`
2. Update the database configuration in each microservice's `application.yml` file if needed

### Environment Variables

Set up the following environment variables for email functionality:

```bash
# Windows
set MAIL_PASSWORD=your_app_password_here

# Linux/Mac
export MAIL_PASSWORD=your_app_password_here
```

For Gmail, create an App Password:
1. Go to your Google Account > Security > 2-Step Verification
2. At the bottom, click on "App passwords"
3. Select "Mail" and "Other (Custom name)"
4. Enter "ISH Application" and click "Generate"
5. Use the 16-character password that appears

### Running the Application

Start all services using the provided script:

```bash
./start-all-services.bat  # Windows
./start-all-services.sh   # Linux/Mac
```

The services will start in the following order:
- SSA Web API
- Config Server
- Eureka Server
- Cloud API Gateway
- User Management Service
- Application Registration Service
- Data Collection Service
- Eligibility Determination Service
- Benefit Issuance Service
- Correspondence Service
- Government Report Service
- Admin Service

Access the API Gateway at: http://localhost:7777

## 📚 API Documentation

### Postman Collection

[Click here to download the Postman Collection](https://github.com/lakshay1341/Healthcare-Insurance-System/blob/main/ISH-Postman-collections/ISH%20-%20Insurance%20Service%20Hub.postman_collection.json)

The collection includes a comprehensive set of API requests to test the normal flow a user might experience when interacting with the ISH application.

### API Endpoints

#### User Management Service (Port: 4041)

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/user-api/save` | POST | Register a new user |
| `/user-api/activate` | POST | Activate a user account |
| `/user-api/login` | POST | User login |
| `/worker-api/save` | POST | Register a new worker |
| `/worker-api/activate` | POST | Activate a worker account |
| `/worker-api/login` | POST | Worker login |
| `/api/auth/login` | POST | Combined user/worker login |

#### Application Registration Service (Port: 7071)

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/CitizenAR-api/save` | POST | Register a new citizen application |

#### Data Collection Service (Port: 7072)

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/dc-api/loadCaseNo/{appId}` | POST | Generate a case number for an application |
| `/dc-api/planNames` | GET | Get all plan names |
| `/dc-api/updatePlanSelection` | PUT | Update plan selection |
| `/dc-api/saveIncome` | POST | Save income details |
| `/dc-api/saveEducation` | POST | Save education details |
| `/dc-api/saveChilds` | POST | Save children details |

#### Eligibility Determination Service (Port: 7073)

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/ed-api/determine/{caseNo}` | GET | Determine eligibility for a case |

#### Admin Service (Port: 7074)

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/plan-api/categories` | GET | Get all plan categories |
| `/plan-api/register` | POST | Register a new plan |

#### Correspondence Service (Port: 7075)

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/co-triggers-api/process` | GET | Process correspondence triggers |

#### Benefit Issuance Service (Port: 7076)

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/bi-api/send` | GET | Send benefits to beneficiaries |

#### Government Report Service (Port: 7077)

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/report-api/government/reports` | GET | Get government reports |

## 🔧 Technical Details

- **Language**: Java 17
- **Framework**: Spring Boot 3.x
- **Database**: MySQL 8.0
- **Service Discovery**: Eureka
- **API Gateway**: Spring Cloud Gateway
- **Configuration**: Spring Cloud Config
- **Circuit Breaker**: Resilience4j
- **Documentation**: Swagger/OpenAPI
- **Authentication**: JWT-based authentication
- **Communication**: WebClient for inter-service communication

## 🔒 Security Features

- JWT-based authentication
- Password encryption using BCrypt
- Role-based access control
- Secure email communication

## 🧪 Testing

A comprehensive test script is provided to test all endpoints:

```bash
./test-all-endpoints.sh
```

Additionally, a Postman collection is available for manual testing of all endpoints.
