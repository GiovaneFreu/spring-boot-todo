# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot TODO application built with:
- **Spring Boot 3.5.5** with Java 24
- **Gradle Kotlin DSL** for build management
- **Spring Data JPA** for data persistence with PostgreSQL
- **Spring Security** for authentication and authorization
- **Lombok** for reducing boilerplate code

## Common Commands

### Build and Run
```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun

# Clean build
./gradlew clean build
```

### Testing
```bash
# Run all tests
./gradlew test

# Run a specific test class
./gradlew test --tests "SpringBootTodoApplicationTests"

# Run tests with continuous build
./gradlew test --continuous
```

### Development
```bash
# Check for dependency updates
./gradlew dependencyUpdates

# Generate test report
./gradlew test jacocoTestReport
```

## Architecture

- **Main Application**: `src/main/java/com/example/springboottodo/SpringBootTodoApplication.java`
- **Package Structure**: `com.example.springboottodo.*`
- **Database**: PostgreSQL (configured in dependencies, connection details in `application.properties`)
- **Security**: Spring Security is included but not yet configured
- **Configuration**: `src/main/resources/application.properties`

## Key Technologies

- Uses **JPA/Hibernate** for ORM
- **Lombok** annotations for getters/setters/constructors
- **JUnit 5** and **Spring Boot Test** for testing
- **Gradle wrapper** for consistent builds across environments

## Database Setup

The project is configured for PostgreSQL. You'll need to:
1. Set up a PostgreSQL database
2. Configure connection details in `application.properties`
3. Add appropriate database URL, username, and password properties