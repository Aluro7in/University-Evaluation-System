# University Evaluation System

Full-stack university evaluation platform with a React/TypeScript web interface and a substantial Java/Spring Boot business backend.

The original project concept is preserved: students, courses, enrollment, grades, polymorphic GPA calculation, transcripts, role-aware web flows, analytics, and database persistence remain part of the application. The Java backend is added as the business engine rather than replacing the existing TypeScript experience.

## Architecture

```text
┌──────────────────────────────┐
│ React 19 + TypeScript        │
│ Student / Admin web UI       │
└──────────────┬───────────────┘
               │ tRPC / HTTP
               ▼
┌──────────────────────────────┐
│ Express + tRPC                │
│ Authentication + Web Gateway │
└──────────────┬───────────────┘
               │
       ┌───────┴────────┐
       │                │
       ▼                ▼
┌──────────────┐  ┌─────────────────────────────┐
│ MySQL /      │  │ Java Spring Boot Backend    │
│ Drizzle ORM  │  │                             │
│ Web data     │  │ OOP domain + business rules │
└──────────────┘  │ GPA + enrollment + grades   │
                  │ transcripts + analytics     │
                  │ attendance + audit          │
                  └──────────────┬──────────────┘
                                 │ JPA
                                 ▼
                         ┌───────────────┐
                         │ MySQL / H2    │
                         │ Java tables   │
                         └───────────────┘
```

The Java service uses `java_*` table names, so it can share the same MySQL database without colliding with the existing Drizzle schema.

## Core academic rules

The original evaluation concept is preserved.

| Student type | GPA strategy |
|---|---|
| Engineering | Simple average of 4.0-scale course GPAs |
| Management | Credit-weighted average |
| Graduate | Credit-weighted average with finalized-grade support |

Grade conversion remains:

```text
90-100 -> 4.0 / A
80-89  -> 3.0 / B
70-79  -> 2.0 / C
60-69  -> 1.0 / D
0-59   -> 0.0 / F
```

## Java backend

`java-backend/` is the substantial academic business layer and includes:

- Spring Boot REST API
- Spring Data JPA persistence
- H2 for easy standalone development
- MySQL deployment profile
- polymorphic GPA policies
- student, course, enrollment and grade services
- transcript generation
- attendance tracking
- prerequisite checking
- academic standing and risk analysis
- ranking and dashboard analytics
- department and faculty APIs
- audit logging
- validation and centralized exception handling
- deterministic reporting/analysis components
- Java tests
- Docker packaging

### Java source layout

```text
java-backend/src/main/java/university/backend/
├── analysis/       # deterministic analytics and decision helpers
├── config/         # CORS and seed configuration
├── controller/     # HTTP REST API
├── domain/
│   ├── entity/     # JPA persistence models
│   ├── enums/      # academic state types
│   └── model/      # backend value models
├── dto/            # request/response contracts
├── exception/      # API error model
├── policy/         # polymorphic GPA strategies
├── report/         # reusable reporting components
├── repository/     # Spring Data repositories
├── service/        # business/application services
├── util/           # normalization and formatting helpers
└── validation/     # domain validators
```

The original OOP demonstration is also preserved:

```text
people/Person.java
evaluation/Student.java
evaluation/EvaluationPolicy.java
courses/Course.java
courses/EngineeringStudent.java
courses/ManagementStudent.java
exceptions/InvalidMarkException.java
app/Main.java
```

## Java API

The Java service exposes the academic engine over REST. The main routes include:

```text
GET  /health
GET  /api/health
GET  /api/students
POST /api/students
GET  /api/courses
POST /api/courses
POST /api/enrollments
GET  /api/enrollments/student/{studentId}
POST /api/grades
GET  /api/grades/student/{studentId}
POST /api/evaluation/gpa
GET  /api/evaluation/students/{studentId}
GET  /api/evaluation/grade-scale?percentage=85
GET  /api/transcripts/student/{studentId}
GET  /api/attendance/student/{studentId}
GET  /api/analytics/student/{studentId}
GET  /api/system/summary
```

## Complete Java API

```text
GET    /health

GET    /api/students
POST   /api/students
GET    /api/students/{id}
PUT    /api/students/{id}
DELETE /api/students/{id}

GET    /api/courses
POST   /api/courses
GET    /api/courses/{id}
PUT    /api/courses/{id}

GET    /api/enrollments/student/{studentId}
POST   /api/enrollments
POST   /api/enrollments/{id}/complete
DELETE /api/enrollments/{id}

GET    /api/grades/student/{studentId}
POST   /api/grades
DELETE /api/grades/{id}

POST   /api/evaluation/gpa
GET    /api/evaluation/students/{studentId}
GET    /api/evaluation/grade-scale?percentage=85

GET    /api/transcripts/student/{studentId}
GET    /api/transcripts/student/{studentId}/summary

POST   /api/attendance
GET    /api/attendance/enrollment/{id}
GET    /api/attendance/enrollment/{id}/percentage

GET    /api/analytics/overview
GET    /api/analytics/ranking?limit=10
GET    /api/analytics/risk?gpa=2.1&attendance=72

GET    /api/departments
POST   /api/departments
GET    /api/faculty
POST   /api/faculty

GET    /api/admin/summary
GET    /api/admin/audit
GET    /api/system/current-semester
POST   /api/system/current-semester?key=2026-FALL
```

## TypeScript ↔ Java integration

The existing TypeScript gateway stays intact. GPA operations now prefer the Java service and automatically fall back to the existing TypeScript calculator when the Java service is unavailable during local development.

```text
Web UI
  ↓
tRPC
  ↓
server/javaBackend.ts
  ↓
POST /api/evaluation/gpa
  ↓
EvaluationPolicyFactory
  ↓
Engineering / Management / Graduate policy
  ↓
GPA + standing + credits
```

Grade entry also asks the Java service to validate and convert the percentage before persisting the existing web record.

## Quick start with Docker

### Requirements

- Docker Desktop
- Git

From the repository root:

```bash
docker compose up --build
```

Services:

```text
Web application  http://localhost:3000
Java API         http://localhost:8080
Java health      http://localhost:8080/health
MySQL            localhost:3306
```

The compose stack creates one MySQL database, starts the Java service against it, and starts the existing web application with `JAVA_BACKEND_URL=http://java-backend:8080`.

## Run Java without Docker

Requirements: JDK 21+ and Maven 3.9+.

```bash
cd java-backend
mvn spring-boot:run
```

The default profile uses H2 and seeds a small demonstration dataset.

For MySQL:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

## Run the existing web app locally

```bash
pnpm install
```

Create `.env` from `.env.example`, then:

```bash
pnpm db:push
pnpm dev
```

For local Java integration:

```text
JAVA_BACKEND_URL=http://localhost:8080
```

The React UI and existing TypeScript backend remain available exactly as before; the Java service supplies the authoritative academic calculations when online.

## Testing

Web tests:

```bash
pnpm test
pnpm check
```

Java tests:

```bash
cd java-backend
mvn test
```

The Java suite covers policy selection, GPA boundaries, academic standing, prerequisites, risk rules, attendance analytics, reporting components, and application context startup.

## Persistence

The existing web application continues to use the Drizzle/MySQL schema. The Java service uses prefixed tables:

```text
java_students
java_courses
java_enrollments
java_grades
java_attendance
java_semesters
java_departments
java_faculty
java_audit_logs
java_course_prerequisites
```

This gives the two layers a safe coexistence path while allowing the Java business backend to grow without breaking the current frontend.

## Security and deployment

- Do not commit `.env` files or real credentials.
- Replace the sample JWT secret before deployment.
- Restrict Java CORS origins in production.
- Keep the Java API behind the same authentication boundary or an API gateway for production.
- Use managed MySQL/TiDB in production.
- Keep GPA and academic rules on the server side; the browser only renders the result.

## Project principle

TypeScript was not removed. The existing React application, tRPC gateway, database integration, and UI remain in the project. Java is now the larger source-code layer and is used for a real backend domain: persistence, academic rules, REST APIs, analytics, validation, reporting, and tests.

GitHub's language chart is based on recognized source-code volume. The current repository has more Java source by line count than TypeScript source while retaining the original web application.

## License

MIT License.