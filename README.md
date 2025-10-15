# User Management API Automation Framework

# Project Description
This project is an API automation framework for testing the "User Management" RESTful APIs. It covers all core endpoints (Create, Get, Update, Delete, Batch Query) with both positive and abnormal test cases. The framework uses Java, TestNG, RestAssured, and ExtentReports for reporting.

# Technology Stack
- Java
- Maven
- TestNG
- RestAssured
- ExtentReports
- Faker (for dynamic test data)

# Project Structure
- `api/` → API request methods
- `tests/` → Test classes for each API
- `common/` → Utilities (listener, reporting, config, test data)
- `reports/` → ExtentReports HTML output
- `testng.xml` → TestNG suite configuration
- `pom.xml` → Maven dependencies

## How to Run Tests
mvn clean test

