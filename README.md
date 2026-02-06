# Worklog API

Worklog API is a portfolio backend project built with Java 17 and Spring Boot 4.0.2.
The goal is to demonstrate backend development practices with
clean architecture, real database integration, and production-like setup this includes using what i have learned
using docker + AWS.

## Tech Stack

- Java 17
- Spring Boot 4.0.2
- Spring Data JPA
- PostgreSQL
- Flyway (database migrations)
- Testcontainers (integration testing)
- Maven
- Docker
- AWS (Planned)

## Architecture

The application follows a layered architecture:

Controller → Service → Repository → Database

- **Controller**: HTTP handling, request/response mapping (planned)
- **Service**: Business use cases and transactional boundaries
- **Repository**: Data access via Spring Data JPA
- **Domain (Entities)**: Business rules and invariants

## Database

- PostgreSQL
- Schema managed via Flyway migrations
- Hibernate `ddl-auto=validate` to ensure mapping matches schema

## Testing

Integration tests use **Testcontainers** with a real PostgreSQL instance.

Run all tests with:

```bash
mvn test
