# Expense Tracker Full-Stack 💰

> A full-stack expense tracking application built with Spring Boot 3 (backend) and React + TypeScript (frontend). Features JWT authentication, PostgreSQL persistence, category-based expense filtering, monthly summaries, and Docker Compose deployment.

![Java](https://img.shields.io/badge/Java-17-orange.svg) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.x-green.svg) ![React](https://img.shields.io/badge/React-18.x-blue.svg) ![TypeScript](https://img.shields.io/badge/TypeScript-5.x-blue.svg)

## Features

- JWT Authentication (register/login) with Spring Security
- Full-stack: Spring Boot REST API + React (Vite/TypeScript) frontend
- Expense CRUD with categories, amounts, dates
- Monthly summary dashboard with income/expense totals
- Category-based filtering and search
- PostgreSQL with JPA/Hibernate
- Responsive UI with Tailwind CSS
- Docker Compose for one-command deployment
- REST API with Swagger/OpenAPI docs
- Input validation (backend + frontend)

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Spring Boot 3.2, Spring Security, JPA/Hibernate |
| Frontend | React 18, TypeScript, Vite, Tailwind CSS, Axios |
| Database | PostgreSQL 16 |
| Auth | JWT (jjwt) |
| Docs | springdoc-openapi (Swagger UI) |
| Deploy | Docker Compose |

## Quick Start

### Docker Compose (recommended)

```bash
git clone https://github.com/shivamkumarhub-web/expense-tracker-fullstack.git
cd expense-tracker-fullstack
docker-compose up --build
```

- Frontend: http://localhost:5173
- Backend API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- PostgreSQL: localhost:5432

### Run Separately (dev mode)

**Backend:**
```bash
cd backend
./mvnw spring-boot:run
```

**Frontend:**
```bash
cd frontend
npm install
npm run dev
```

## Project Structure

```
expense-tracker-fullstack/
├── backend/                  # Spring Boot API
│   ├── src/main/java/com/shivam/expensetracker/
│   │   ├── config/            # Security, OpenAPI, CORS config
│   │   ├── controller/        # REST controllers
│   │   ├── dto/               # Request/Response DTOs
│   │   ├── entity/            # JPA entities (User, Expense, Category)
│   │   ├── exception/         # Global exception handling
│   │   ├── repository/        # Spring Data JPA repositories
│   │   ├── security/          # JWT auth provider, filter, UserDetailsService
│   │   └── service/           # Business logic
│   ├── src/main/resources/
│   │   └── application.yml
│   ├── Dockerfile
│   └── pom.xml
├── frontend/                 # React + TypeScript
│   ├── src/
│   │   ├── components/        # Reusable UI components
│   │   ├── pages/             # Page components
│   │   ├── api/               # API client (Axios)
│   │   ├── context/           # Auth context
│   │   └── types/             # TypeScript types
│   ├── Dockerfile
│   ├── package.json
│   └── vite.config.ts
├── docker-compose.yml
└── README.md
```

## API Endpoints

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | /api/auth/register | Register | No |
| POST | /api/auth/login | Login | No |
| GET | /api/expenses | Get user expenses | Yes |
| POST | /api/expenses | Create expense | Yes |
| PUT | /api/expenses/{id} | Update expense | Yes |
| DELETE | /api/expenses/{id} | Delete expense | Yes |
| GET | /api/expenses/summary | Monthly summary | Yes |
| GET | /api/categories | Get categories | Yes |

## License

MIT