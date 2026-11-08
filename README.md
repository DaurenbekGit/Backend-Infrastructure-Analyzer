# Backend Infrastructure Analyzer

**Assignment #6 — Visitor Design Pattern**

**Course:** Software Design Patterns  
**Language:** Java  
**Build Tool:** Maven  
**Design Pattern:** Behavioral — Visitor

## 1. Project Overview

Backend Infrastructure Analyzer is a Java application that demonstrates the implementation of the Visitor Design Pattern.

The application models three backend infrastructure components:

- **ApiServer** — represents an API server with CPU resources and TLS configuration.
- **PostgresDatabase** — represents a PostgreSQL database with storage, backup, and encryption settings.
- **RedisCache** — represents a Redis cache with memory and security configurations.

The system performs two independent operations on these components:

1. **Cost Analysis** — calculates estimated monthly infrastructure expenses.
2. **Security Analysis** — identifies potential security configuration issues.

The Visitor Pattern allows new operations to be introduced without modifying existing infrastructure component classes.

## 2. Design Pattern Implementation

The project implements the following Visitor Pattern components.

| Pattern Component | Java Implementation |
|---|---|
| Element Interface | InfrastructureComponent |
| Concrete Elements | ApiServer, PostgresDatabase, RedisCache |
| Visitor Interface | InfrastructureVisitor |
| Concrete Visitors | CostVisitor, SecurityVisitor |
| Object Structure | Collection of InfrastructureComponent objects in Main |

### Double Dispatch

Each infrastructure component implements the `accept()` method, which calls:

`visitor.visit(this);`

This enables double dispatch, allowing the correct visitor operation to be selected based on the concrete element type and the visitor implementation.

The implementation avoids using `instanceof` chains to identify component types.

## 3. Application Features

### Cost Analysis

The CostVisitor calculates monthly infrastructure expenses using predefined educational rates.

| Resource | Monthly Rate |
|---|---|
| API Server CPU Core | $40 per core per instance |
| PostgreSQL Storage | $0.45 per GB |
| Redis Memory | $10 per GB |

The visitor calculates the cost of each component and the total estimated monthly cost.

### Security Analysis

The SecurityVisitor checks infrastructure security configurations, including:

- API Server TLS configuration
- PostgreSQL backup configuration
- PostgreSQL encryption
- Redis password authentication
- Redis private network configuration

The visitor reports detected issues and counts security warnings.

## 4. Project Structure

The project uses Maven's standard directory structure.

**Main source package:** `src/main/java/kz/assignment/`

Main classes:

- `InfrastructureComponent.java`
- `ApiServer.java`
- `PostgresDatabase.java`
- `RedisCache.java`
- `InfrastructureVisitor.java`
- `CostVisitor.java`
- `SecurityVisitor.java`
- `ResourceValidation.java`
- `Main.java`

**Test package:** `src/test/java/kz/assignment/`

Test classes:

- `ComponentValidationTest.java`
- `CostVisitorTest.java`
- `DoubleDispatchTest.java`
- `SecurityVisitorTest.java`

Additional files include `pom.xml`, `mvnw`, `mvnw.cmd`, and Maven Wrapper configuration.

## 5. Clean Code Principles

### 1. Single Responsibility Principle

Each Concrete Visitor performs one specific operation. CostVisitor handles financial calculations, while SecurityVisitor handles security checks.

### 2. Open/Closed Principle

The system is open for extension through new visitor implementations without requiring modifications to existing Concrete Elements.

### 3. Meaningful Naming

Classes and methods use descriptive names that clearly communicate their responsibilities.

### 4. Encapsulation

Infrastructure components expose their required properties through public accessors while keeping their internal state controlled.

### 5. Avoiding Type Checking

The project uses double dispatch instead of `instanceof` chains or explicit type-checking conditions.

### 6. Input Validation

ResourceValidation provides centralized validation to prevent invalid infrastructure resource values.

### 7. Avoiding Code Duplication

Shared validation and reporting logic is reused instead of being duplicated across multiple components.

## 6. Running the Application

**Requirements:**

- JDK 25
- Git
- Maven Wrapper (included in the repository)

Open a terminal in the project directory.

**Run tests on Windows:**

`.\mvnw.cmd clean test`

**Run the application on Windows:**

`.\mvnw.cmd exec:java`

The Maven Wrapper allows the project to run without requiring a separate Maven installation.

## 7. Example Output

The application produces the following output for the sample configuration:

    Monthly cost report
    API server: $160.00
    PostgreSQL database: $45.00
    Redis cache: $20.00
    Total Monthly Cost: $225.00

    Security report
    - Redis cache has password authentication disabled.
    Total Warnings: 1

All costs are illustrative and do not represent actual cloud provider pricing.

## 8. Unit Testing

The application uses JUnit 5.

The test suite covers:

- Infrastructure component validation
- Monthly cost calculations
- Security configuration checks
- Correct double dispatch behavior

**Test execution results:**

- Tests executed: 17
- Failures: 0
- Errors: 0
- Skipped: 0
- Build status: BUILD SUCCESS

## 9. Advantages and Limitations

**Advantages:**

- Separates operations from infrastructure component classes.
- Supports adding new operations through additional visitors.
- Improves separation of responsibilities.
- Eliminates the need for explicit element type checking.

**Limitations:**

- Adding a new Concrete Element requires updating the Visitor Interface and existing Concrete Visitors.
- Visitors may depend on the public properties exposed by element classes.

## 10. Conclusion

This project demonstrates how the Visitor Design Pattern separates infrastructure data structures from operations performed on them.

The implementation uses double dispatch to support cost calculation and security analysis while maintaining clear responsibilities and extensibility.

The project also demonstrates practical Java development principles, Maven project organization, and automated unit testing.
