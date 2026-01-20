# AccessFlow

<img width="484" height="147" alt="image" src="https://github.com/user-attachments/assets/0dd6e6db-5486-42bb-ad1a-897fb3e605c5" />


Production-grade backend for SaaS authentication, authorization (RBAC), multi-tenant organizations, and audit logging — built as a modular monolith with clean architecture, migrations, and containerized runtime. 


---

## What this system solves

AccessFlow provides the security foundation most SaaS products need from day one:

- **Authentication** with JWT issuance/verification and session lifecycle
- **Authorization** with role-based access control (roles + permissions)
- **Multi-tenancy** via organization scoping and membership rules
- **Audit trails** for security-relevant actions and operational accountability

This project is designed as a **serious backend portfolio artifact**: maintainable structure, clear boundaries, and production-oriented operational setup.

---

## Architecture

<img width="1705" height="2048" alt="cc8a5a94-98b8-47e5-a9eb-adbbab3631f7" src="https://github.com/user-attachments/assets/6a2488cf-ed5d-4d12-9649-fe38fd2a4fdc" />

### High-level flow

1. Request enters REST API
2. JWT is validated and user context is established
3. Tenant (organization) scope is resolved
4. RBAC is enforced (roles/permissions)
5. Use case executes domain logic
6. Audit event is recorded
7. Data is persisted in PostgreSQL

### Code boundaries (Clean Architecture)

src/main/java/com/accessflow/
├── domain/ # Entities, invariants, policies
├── application/ # Use cases, orchestration, ports
├── infrastructure/ # Persistence, security, adapters
└── api/ # Controllers, DTOs, REST endpoints



---

## Core capabilities

- JWT-based authentication (issue/verify/refresh)
- Multi-tenant organizations and membership model
- RBAC with fine-grained permissions
- Automatic audit logging (security + business actions)
- PostgreSQL persistence with Flyway migrations
- OpenAPI/Swagger documentation
- Docker & Docker Compose for reproducible runtime
- Testing with JUnit 5 + Testcontainers (PostgreSQL)

---

## Tech stack

- **Java 17**
- **Spring Boot 3**
- **Spring Security + JWT**
- **PostgreSQL**
- **Flyway**
- **Spring Data JPA**
- **JUnit 5, Testcontainers**
- **Docker, Docker Compose**
- **OpenAPI/Swagger**

---

## Quick start (Docker Compose)

### Prerequisites
- Docker + Docker Compose

### Run
```bash
docker compose up --build
```
This will:

start PostgreSQL

run Flyway migrations on startup

launch the API at http://localhost:8080

## API Documentation

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI spec: `http://localhost:8080/api-docs`

## Main endpoints

### Authentication
- `POST /auth/register` — Register user
- `POST /auth/login` — Login and receive JWT
- `POST /auth/logout` — Invalidate session

### Organizations (multi-tenant)
- `POST /orgs` — Create organization
- `POST /orgs/{id}/invite` — Invite user to organization

### RBAC
- `POST /roles` — Create role
- `POST /roles/{id}/permissions` — Assign permissions to role

### User
- `GET /me` — Current profile
- `GET /me/permissions` — Effective permissions

### Audit
- `GET /audit` — Query audit logs

## Domain model (core)

- **User** — authenticated identity
- **Organization** — tenant boundary
- **Membership** — user ↔ organization link with roles
- **Role** — named permission set
- **Permission** — granular access rights
- **Session** — active authentication session tracking
- **AuditLog** — append-only audit trail

## Testing

```bash
# unit tests
mvn test

# integration tests (uses Testcontainers)
mvn verify
```
## Configuration 
-Environment variables
| Variable         | Description         | Default      |
| ---------------- | ------------------- | ------------ |
| `DB_HOST`        | Database host       | `localhost`  |
| `DB_PORT`        | Database port       | `5432`       |
| `DB_NAME`        | Database name       | `accessflow` |
| `DB_USER`        | Database user       | `postgres`   |
| `DB_PASSWORD`    | Database password   | `postgres`   |
| `JWT_SECRET`     | JWT signing secret  | `change-me`  |
| `JWT_EXPIRATION` | JWT expiration (ms) | `86400000`   |
| `PORT`           | Application port    | `8080`       |

## Production notes (security/ops)

If adapting this to a real production system:

- Use a strong `JWT_SECRET` and rotate secrets properly
- Enforce TLS/HTTPS
- Apply rate limiting at the edge (gateway / reverse proxy)
- Lock down CORS and security headers
- Use least-privilege DB credentials
- Implement monitoring/alerting and structured logging

## Project tracking

Implementation details and progress live in `TRACKING.md`.


<img width="356" height="162" alt="image" src="https://github.com/user-attachments/assets/fd6e8efb-c617-4306-ae78-d7302d7de619" />


