# Worklog API

Worklog API is a portfolio backend project built with Java 17 and Spring Boot 4.0.2.
The goal is to demonstrate backend development practices with
clean architecture, real database integration, and production-like setup this includes using what i have learned
using docker + AWS.

## Tech Stack

- Java 17
- Spring Boot 4.0.2 (Web MVC, Validation, Actuator)
- Spring Data JPA (Hibernate)
- PostgreSQL
- Flyway (database migrations)
- Testcontainers (integration testing)
- OpenAPI/Swagger (springdoc)
- Actuator - Prometheus (Metrics)
- Maven
- Docker (Dockerfile + docker-compose)
- AWS (Planned)

## Architecture

The application follows a layered architecture:

Controller → Service → Repository → Database

- **Controller**: HTTP handling, request/response mapping (record-DTOs), validation
- **Service**: Business use cases and transactional boundaries
- **Repository**: Data access via Spring Data JPA
- **Domain (Entities)**: Business rules and invariants (aggregate root: Project)
- **Exceptions**: Consistent API error handling

## Database

- PostgreSQL
- Schema managed via Flyway migrations
- Hibernate `ddl-auto=validate` to ensure mapping matches schema

## API Documentation (Swagger / OpenAPI)

While application is running:

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Observability (Actuator + Metrics)

This project uses Spring Boot Actuator as default for health checks and basic observability.

- Health Endpoints: 
- GET /actuator/health
- GET /actuator/health/liveness
- GET /actuator/health/readiness
- GET /actuator/info

## Metrics (local profile only)

- GET /actuator/metrics
- GET /actuator/prometheus

## Run locally with Docker 

1) Configure environment variables
Create a .env file in the project root(see .env.example file)

2) Start (API + PostgreSQL)
- bash: 
docker compose up --build
docker compose down (STOP)
docker compose down -v (Removes Docker volume)


## Testing

Integration tests use **Testcontainers** with a real PostgreSQL instance.

Run all tests with:

```bash
mvn test
