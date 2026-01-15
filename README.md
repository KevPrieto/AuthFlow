# AccessFlow

**Backend SaaS for Authentication, Authorization and Audit Logging**

## Overview

AccessFlow is a production-grade backend system for managing authentication, fine-grained authorization (RBAC), multi-tenant organizations, and security audit logging.

### Key Features

- 🔐 JWT-based authentication
- 👥 Multi-tenant organizations
- 🛡️ Role-based access control (RBAC)
- 📝 Automatic audit logging
- 🐳 Dockerized deployment
- 📚 OpenAPI/Swagger documentation

## Technology Stack

- **Language:** Java 17
- **Framework:** Spring Boot 3
- **Security:** Spring Security + JWT
- **Database:** PostgreSQL
- **Migrations:** Flyway
- **ORM:** Spring Data JPA
- **Testing:** JUnit 5, Testcontainers
- **Containerization:** Docker, Docker Compose
- **Documentation:** OpenAPI/Swagger

## Architecture

AccessFlow follows a **Modular Monolith** architecture based on Clean Architecture principles:

```
src/main/java/com/accessflow/
├── domain/         # Entities, invariants, policies
├── application/    # Use cases, services
├── infrastructure/ # DB, security, persistence
└── api/            # Controllers, DTOs
```

## Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.9+
- Docker and Docker Compose
- PostgreSQL 16 (if running locally without Docker)

### Running with Docker Compose

The easiest way to run AccessFlow:

```bash
docker-compose up
```

This will:
- Start PostgreSQL database
- Build and run the AccessFlow application
- Apply database migrations automatically
- Expose the API at `http://localhost:8080`

### Running Locally

1. **Start PostgreSQL:**
   ```bash
   docker-compose up postgres
   ```

2. **Build the project:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

### API Documentation

Once running, access the interactive API documentation at:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI Spec: http://localhost:8080/api-docs

## Core Endpoints

### Authentication
- `POST /auth/register` - Register new user
- `POST /auth/login` - Login and receive JWT
- `POST /auth/logout` - Logout and invalidate session

### Organizations
- `POST /orgs` - Create organization
- `POST /orgs/{id}/invite` - Invite user to organization

### RBAC
- `POST /roles` - Create role
- `POST /roles/{id}/permissions` - Assign permissions to role

### User
- `GET /me` - Get current user profile
- `GET /me/permissions` - Get user permissions

### Audit
- `GET /audit` - Query audit logs

## Domain Model

### Core Entities

- **User** - User accounts with authentication
- **Organization** - Multi-tenant organizations
- **Membership** - User-organization relationships with roles
- **Role** - Named permission sets
- **Permission** - Granular access rights
- **Session** - Active authentication sessions
- **AuditLog** - Security audit trail

## Development

### Running Tests

```bash
# Unit tests
mvn test

# Integration tests (requires Docker for Testcontainers)
mvn verify

# All tests with coverage
mvn clean verify
```

### Database Migrations

Flyway migrations are located in `src/main/resources/db/migration/`.

Migrations run automatically on application startup.

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `DB_HOST` | Database host | `localhost` |
| `DB_PORT` | Database port | `5432` |
| `DB_NAME` | Database name | `accessflow` |
| `DB_USER` | Database user | `postgres` |
| `DB_PASSWORD` | Database password | `postgres` |
| `JWT_SECRET` | JWT signing secret | _(change in production)_ |
| `JWT_EXPIRATION` | JWT expiration (ms) | `86400000` (24h) |
| `PORT` | Application port | `8080` |

## Security Considerations

⚠️ **Important for Production:**

1. Change the `JWT_SECRET` to a strong, randomly generated value (min 32 characters)
2. Use HTTPS/TLS for all communications
3. Configure proper CORS policies
4. Set strong database passwords
5. Enable rate limiting
6. Review and configure security headers

## Testing Strategy

- **Unit Tests:** Domain rules and business logic
- **Integration Tests:** Using Testcontainers with PostgreSQL
- **Security Tests:** Role/permission enforcement
- **Coverage Goal:** Meaningful coverage of critical paths

## Project Status

Track implementation progress in [TRACKING.md](TRACKING.md).

## License

This project is built for portfolio and educational purposes.

## Contributing

This is a portfolio project. For questions or suggestions, please open an issue.

---

**Built with ❤️ to demonstrate real-world backend engineering skills**
