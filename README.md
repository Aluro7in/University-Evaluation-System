<div align="center">
  <h1>🎓 UNIVERSITY EVALUATION SYSTEM</h1>
  <p><strong>ARCHITECTURAL & DESIGN SPECIFICATION • FULL-STACK PLATFORM</strong></p>
  <br />
</div>
# College Project

## Full-stack university evaluation platform

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

[![Architecture diagram of aluro7in/university-evaluation-system](https://gitdiagram.com/aluro7in/university-evaluation-system/diagram.png)](https://gitdiagram.com/aluro7in/university-evaluation-system?utm_source=readme&utm_medium=picture)

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

%% Generated by https://gitdiagram.com/aluro7in/university-evaluation-system
flowchart TD

subgraph group_web["Web experience"]
  node_web_ui["React interface<br/>[App.tsx]"]
  node_profiles_grades["Profiles and grades<br/>[StudentProfile.tsx]"]
  node_transcripts_dashboard["Transcript and dashboard<br/>[Transcript.tsx]"]
end

subgraph group_gateway["Web gateway"]
  node_trpc["tRPC gateway<br/>[routers.ts]"]
  node_web_db_access["Web data access<br/>[db.ts]"]
  node_java_adapter["Java API adapter<br/>[javaBackend.ts]"]
end

subgraph group_academic["Academic engine"]
  node_java_api["Spring REST API"]
  node_academic_services["Academic services"]
  node_enrollment_service["Enrollment workflow"]
  node_evaluation_policies["GPA policies"]
  node_grade_scale["Grade conversion<br/>[GradeScale.java]"]
  node_attendance["Attendance tracking"]
end

subgraph group_insight["Analysis and planning"]
  node_analysis["Analytics and risk"]
  node_forecast["Performance forecasts"]
  node_planning["Academic planning"]
  node_reports["Academic reports"]
end

subgraph group_persistence["Persistence"]
  node_web_schema[("Web records<br/>[schema.ts]")]
  node_web_store[("Web database")]
  node_java_persistence[("Academic records")]
  node_java_entities["Academic entities"]
  node_java_db[("Java database")]
end

node_student_admin(("Students and admins"))

node_student_admin -->|"uses"| node_web_ui
node_web_ui -->|"navigates"| node_profiles_grades
node_web_ui -->|"navigates"| node_transcripts_dashboard
node_profiles_grades -->|"requests data"| node_trpc
node_transcripts_dashboard -->|"requests data"| node_trpc
node_trpc -->|"reads and writes"| node_web_db_access
node_web_db_access -->|"uses schema"| node_web_schema
node_web_db_access -->|"persists records"| node_web_store
node_trpc -->|"delegates GPA"| node_java_adapter
node_java_adapter -->|"calls REST API"| node_java_api
node_java_api -.->|"dispatches requests"| node_academic_services
node_java_api -->|"dispatches enrollment"| node_enrollment_service
node_enrollment_service -->|"reads and writes"| node_java_persistence
node_enrollment_service -->|"checks prerequisites"| node_academic_services
node_java_persistence -.->|"stores entities"| node_java_entities
node_java_persistence -->|"persists records"| node_java_db
node_java_api -.->|"uses policies"| node_evaluation_policies
node_evaluation_policies -->|"converts grades"| node_grade_scale
node_academic_services -.->|"supports analytics"| node_analysis
node_academic_services -.->|"supports forecasts"| node_forecast
node_academic_services -.->|"supports planning"| node_planning
node_academic_services -.->|"supports attendance"| node_attendance
node_analysis -.->|"informs reports"| node_reports

click node_web_ui "https://github.com/aluro7in/university-evaluation-system/blob/master/client/src/App.tsx"
click node_profiles_grades "https://github.com/aluro7in/university-evaluation-system/blob/master/client/src/pages/StudentProfile.tsx"
click node_transcripts_dashboard "https://github.com/aluro7in/university-evaluation-system/blob/master/client/src/pages/Transcript.tsx"
click node_trpc "https://github.com/aluro7in/university-evaluation-system/blob/master/server/routers.ts"
click node_web_db_access "https://github.com/aluro7in/university-evaluation-system/blob/master/server/db.ts"
click node_java_adapter "https://github.com/aluro7in/university-evaluation-system/blob/master/server/javaBackend.ts"
click node_web_schema "https://github.com/aluro7in/university-evaluation-system/blob/master/drizzle/schema.ts"
click node_java_api "https://github.com/aluro7in/university-evaluation-system/tree/master/java-backend/src/main/java/university/backend/controller"
click node_academic_services "https://github.com/aluro7in/university-evaluation-system/tree/master/java-backend/src/main/java/university/backend/service"
click node_enrollment_service "https://github.com/aluro7in/university-evaluation-system/blob/master/java-backend/src/main/java/university/backend/service/EnrollmentService.java"
click node_evaluation_policies "https://github.com/aluro7in/university-evaluation-system/tree/master/java-backend/src/main/java/university/backend/policy"
click node_grade_scale "https://github.com/aluro7in/university-evaluation-system/blob/master/java-backend/src/main/java/university/backend/policy/GradeScale.java"
click node_java_persistence "https://github.com/aluro7in/university-evaluation-system/tree/master/java-backend/src/main/java/university/backend/repository"
click node_java_entities "https://github.com/aluro7in/university-evaluation-system/tree/master/java-backend/src/main/java/university/backend/domain/entity"
click node_analysis "https://github.com/aluro7in/university-evaluation-system/tree/master/java-backend/src/main/java/university/backend/analysis"
click node_forecast "https://github.com/aluro7in/university-evaluation-system/tree/master/java-backend/src/main/java/university/backend/forecast"
click node_planning "https://github.com/aluro7in/university-evaluation-system/tree/master/java-backend/src/main/java/university/backend/planning"
click node_attendance "https://github.com/aluro7in/university-evaluation-system/blob/master/java-backend/src/main/java/university/backend/business/AttendancePolicyService.java"
click node_reports "https://github.com/aluro7in/university-evaluation-system/tree/master/java-backend/src/main/java/university/backend/report"

classDef toneNeutral fill:#f8fafc,stroke:#334155,stroke-width:1.5px,color:#0f172a
classDef toneBlue fill:#dbeafe,stroke:#2563eb,stroke-width:1.5px,color:#172554
classDef toneAmber fill:#fef3c7,stroke:#d97706,stroke-width:1.5px,color:#78350f
classDef toneMint fill:#dcfce7,stroke:#16a34a,stroke-width:1.5px,color:#14532d
classDef toneRose fill:#ffe4e6,stroke:#e11d48,stroke-width:1.5px,color:#881337
classDef toneIndigo fill:#e0e7ff,stroke:#4f46e5,stroke-width:1.5px,color:#312e81
classDef toneTeal fill:#ccfbf1,stroke:#0f766e,stroke-width:1.5px,color:#134e4a
class node_web_ui,node_profiles_grades,node_transcripts_dashboard toneBlue
class node_trpc,node_web_db_access,node_java_adapter toneAmber
class node_java_api,node_academic_services,node_enrollment_service,node_evaluation_policies,node_grade_scale,node_attendance toneMint
class node_analysis,node_forecast,node_planning,node_reports toneRose
class node_web_schema,node_web_store,node_java_persistence,node_java_entities,node_java_db,node_student_admin toneIndigo

## License

MIT License.
