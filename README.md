# TRAABCO Business Management & Booking Platform

## 📌 Project Overview

The **TRAABCO Business Management & Booking Platform** is a full-stack web application developed to support the operations of **Tacks Registered Accountants and Business Consultants (TRAABCO)**.

TRAABCO is an accounting and business consulting firm established in 2012 and based in Mthatha, Eastern Cape. The company provides services to Small and Medium-sized Enterprises (SMEs), including accounting and bookkeeping, tax services, auditing and independent reviews, and business consulting.

The purpose of this platform is to provide a centralized digital system for managing **clients, users, services, bookings, payments, and business engagements**.

The system is designed to improve the organization of business operations while providing a structured way for clients and consultants to interact with the services offered by TRAABCO.

---

## 🎯 Business Context

TRAABCO's vision is to contribute to the development of SMEs through quality professional services, job creation, and skills development, particularly in underserved areas.

The company's services include:

* Accounting and bookkeeping
* Tax advisory and compliance services
* Auditing and independent reviews
* Business consulting

As the business grows and expands its operations, managing clients, services, appointments, payments, and engagements through manual processes can become increasingly difficult.

This project addresses that problem by providing a centralized platform for managing these activities digitally.

---

## 🚀 Project Objectives

The main objectives of the system are to:

* Centralize TRAABCO's business information.
* Manage clients and system users.
* Manage the professional services offered by TRAABCO.
* Allow bookings to be created and managed.
* Track payments associated with bookings or services.
* Manage client engagements.
* Provide role-based access to different users.
* Improve organization and accessibility of business information.
* Provide a foundation that can be extended as TRAABCO's operations grow.

---

## ✨ Key Features

### 👤 User Management

The system supports different types of users and role-based access.

Users can be managed according to their responsibilities within the platform.

Supported roles include:

* `ADMIN`
* `CONSULTANT`
* `VIEWER`

---

### 👥 Client Management

The platform provides functionality for managing TRAABCO's clients.

Client information can be stored and associated with services, bookings, payments, and engagements.

---

### 🧾 Service Management

TRAABCO's professional services can be managed through the system.

Examples include:

* Accounting and bookkeeping
* Tax services
* Auditing and independent reviews
* Business consulting

Services can be maintained within the system and associated with client bookings and engagements.

---

### 📅 Booking Management

The booking functionality allows appointments or service requests to be recorded and managed.

A booking can be associated with:

* A client
* A service
* A consultant
* A date/time
* A payment
* An engagement

This provides a structured way of managing client interactions.

---

### 💳 Payment Management

The system includes payment management functionality for recording and managing payments associated with business services and bookings.

---

### 📋 Engagement Management

Client engagements can be managed as part of the business workflow.

An engagement represents the professional work being performed for a client and provides a connection between the client, service, and business processes.

---

## 🏗️ System Architecture

The application follows a layered architecture based on the Spring Boot ecosystem.

```text
                    ┌──────────────────────┐
                    │      Frontend        │
                    │   HTML / CSS / JS    │
                    └──────────┬───────────┘
                               │
                               │ HTTP / REST
                               ▼
                    ┌──────────────────────┐
                    │    REST Controllers  │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │    Service Layer     │
                    │   Business Logic     │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │   Repository Layer   │
                    │     Data Access      │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │       MySQL          │
                    │      Database        │
                    └──────────────────────┘
```

The backend follows a separation of responsibilities between:

* Controllers
* Services
* Repositories
* Entities
* DTOs
* Mappers
* Security components

---

## 🛠️ Technology Stack

### Backend

* **Java 21**
* **Spring Boot**
* **Spring Web**
* **Spring Data JPA**
* **Spring Security**
* **JWT Authentication**
* **MapStruct**
* **Maven**

### Frontend

* **HTML5**
* **CSS3**
* **JavaScript**

The frontend is intentionally implemented using standard web technologies rather than a frontend framework.

### Database

* **MySQL**

### Development Tools

* IntelliJ IDEA
* Git
* GitHub
* Maven
* MySQL

---

## 📁 Project Structure

```text
traabco-business-platform/
│
├── backend/
│   └── app/
│       ├── src/
│       │   ├── main/
│       │   │   ├── java/
│       │   │   │   └── com/
│       │   │   │       └── cput/
│       │   │   │           └── traabcobusinessplatform/
│       │   │   │
│       │   │   └── resources/
│       │   │
│       │   └── test/
│       │
│       └── pom.xml
│
├── frontend/
│   ├── admin/
│   ├── client/
│   ├── css/
│   ├── js/
│   └── index.html
│
└── README.md
```

---

## 🔐 Security

The backend uses **Spring Security** to protect application resources.

Authentication is implemented using **JSON Web Tokens (JWT)**.

Role-based authorization is used to control access to functionality according to the authenticated user's role.

For example, administrative functionality can be restricted to users with the `ADMIN` role.

---

## 🌐 REST API

The backend exposes RESTful endpoints that allow the frontend to communicate with the application.

The API is organized around the application's main resources, including:

```text
/api/users
/api/clients
/api/services
/api/bookings
/api/payments
/api/engagements
```

The exact endpoints and request/response structures are documented within the backend controllers and DTOs.

---

## 🗄️ Database

The application uses **MySQL** as its relational database.

The database is responsible for storing information relating to:

* Users
* Clients
* Services
* Bookings
* Payments
* Engagements

Relationships between these entities allow the system to represent the workflow of TRAABCO's business operations.

---

## ⚙️ Getting Started

### Prerequisites

Before running the application, make sure the following are installed:

* Java 21
* Maven
* MySQL
* Git
* A modern web browser

---

### 1. Clone the Repository

```bash
git clone https://github.com/Muso-Nkuntsu/traabco-booking-system.git
```

Navigate into the project:

```bash
cd traabco-booking-system
```

---

### 2. Configure the Database

Create a MySQL database for the application:

```sql
CREATE DATABASE traabco_business_management;
```

Configure the application's database connection in the backend configuration.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/traabco_business_management
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

> Do not commit database passwords, JWT secrets, or other sensitive credentials to GitHub.

---

### 3. Run the Backend

Navigate to the backend application:

```bash
cd backend/app
```

Run the application using Maven:

```bash
./mvnw spring-boot:run
```

On Windows, you can use:

```bash
mvnw.cmd spring-boot:run
```

The backend runs on:

```text
http://localhost:8080
```

---

### 4. Run the Frontend

Open the frontend files in a browser or serve them using a local development server.

The frontend communicates with the Spring Boot backend through its REST API.

---

## 🧪 Testing

The backend includes automated tests for application functionality.

Testing covers areas such as:

* REST controllers
* Business logic
* API requests
* Booking functionality
* Validation
* Security-related functionality

Tests can be executed using Maven:

```bash
./mvnw test
```

On Windows:

```bash
mvnw.cmd test
```

---

## 👨‍💻 Development Approach

The project follows a layered backend architecture:

```text
Controller
     ↓
Service
     ↓
Repository
     ↓
Database
```

DTOs are used to control the data exposed through the API, while mappers are used to convert between entities and DTOs where required.

This separation makes the application easier to maintain, test, and extend.

---

## 🔮 Future Improvements

Potential future improvements include:

* Online payment gateway integration
* Email notifications for bookings
* Automated appointment reminders
* Improved client self-service functionality
* Reporting and business analytics
* Document management
* Advanced booking availability management
* Expanded audit logging
* Deployment to a cloud environment
* Improved administrative dashboards
* Mobile-friendly improvements

---

## 🏢 About TRAABCO

**Tacks Registered Accountants and Business Consultants (TRAABCO)** was established in 2012 and operates from Mthatha in the Eastern Cape.

The company provides professional accounting and business advisory services to SMEs across different industries.

Its services include:

* Accounting and bookkeeping
* Tax services
* Auditing and independent reviews
* Business consulting

TRAABCO's broader vision focuses on contributing to SME development, job creation, skills development, and economic development in underserved communities.

---

## 👤 Developer

**Muso Nkuntsu**

Diploma in ICT Application Development
Cape Peninsula University of Technology (CPUT)

### Technical Focus

* Java
* Spring Boot
* Spring Security
* REST APIs
* MySQL
* Object-Oriented Programming
* HTML
* CSS
* JavaScript
* Git & GitHub

---

## 📄 Project Status

This project is currently under development.

Features and architecture may continue to evolve as additional business requirements are implemented.

---

## 📜 License

This project was developed for educational and project purposes in collaboration with TRAABCO.

All TRAABCO business information, branding, and proprietary information remain the property of TRAABCO.
