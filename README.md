# TRAABCO Business Management & Booking Platform

A full-stack web application developed to support the business operations of **Tacks Registered Accountants and Business Consultants (TRAABCO)**.

## About TRAABCO

TRAABCO is an accounting and business consulting firm established in 2012 and based in Mthatha, Eastern Cape. The company provides services including:

* Accounting and bookkeeping
* Tax services
* Auditing and independent reviews
* Business consulting

This project aims to provide TRAABCO with a centralized platform for managing clients, services, bookings, payments, and business engagements.

## Features

* User management
* Client management
* Service management
* Booking management
* Payment management
* Engagement management
* Role-based access
* RESTful API
* JWT authentication

## Technology Stack

**Frontend**

* React
* TypeScript
* React Router
* Axios
* CSS

**Backend**

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* JWT
* MapStruct
* Maven

**Database**

* MySQL

**Tools**

* Git & GitHub
* IntelliJ IDEA
* Visual Studio Code

## Architecture

```text
React + TypeScript
       │
       │ REST API / JSON
       ▼
Spring Boot
       │
       ▼
MySQL
```

The backend follows a layered architecture:

```text
Controller → Service → Repository → Database
```

The React frontend communicates with the backend through RESTful API endpoints.

## Project Structure

```text
traabco-business-platform/
├── backend/
│   └── app/
└── frontend/
    └── src/
        ├── components/
        ├── pages/
        ├── services/
        ├── types/
        ├── routes/
        └── App.tsx
```

## Getting Started

### Backend

```bash
cd backend/app
mvnw.cmd spring-boot:run
```

Backend:

```text
http://localhost:8080
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

The frontend normally runs at:

```text
http://localhost:5173
```

A local MySQL database must also be configured before starting the backend.

## My Contribution

This is a collaborative project. My primary responsibilities include **backend development and selected frontend functionality**.

My work includes:

* Spring Boot REST APIs
* Controllers, services and repositories
* Database integration
* DTOs and entity mapping
* Authentication and authorization
* JWT security
* Backend testing
* React and TypeScript frontend development
* API integration
* Frontend pages and components

## Project Status

**In Development**

The system is actively being developed and additional functionality may be added as project requirements evolve.

## Developer

**Muso Nkuntsu**

Diploma in ICT Application Development
Cape Peninsula University of Technology (CPUT)

**Focus:** Java • Spring Boot • REST APIs • MySQL • React • TypeScript • Git
