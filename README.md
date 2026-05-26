# Finance Manager Backend

A Spring Boot based Personal Finance Manager backend with JWT Authentication, Transactions, Categories, Goals, and Reports.

---

# Features

- JWT Authentication
- User Registration & Login
- Transaction Management
- Category Management
- Financial Reports
- Goal Tracking
- PostgreSQL Database
- Secure REST APIs
- Render Deployment Ready

---

# Tech Stack

- Java 17
- Spring Boot
- Spring Security
- JWT
- PostgreSQL
- Hibernate / JPA
- Maven

---

# API Base URL

```bash
http://localhost:8080/api
```

Production:

```bash
https://finance-manager-backend-p8n5.onrender.com/api
```

---

# Environment Variables

Create:

```bash
application.properties
```

Add:

```properties
spring.datasource.url=YOUR_DB_URL
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

spring.jpa.hibernate.ddl-auto=update

jwt.secret=YOUR_SECRET_KEY
jwt.expiration=86400000
```

---

# Run Locally

## Clone

```bash
git clone YOUR_BACKEND_REPO_LINK
```

## Open Project

```bash
cd finance-manager-backend
```

## Run

```bash
./mvnw spring-boot:run
```

---

# Authentication APIs

## Register

```http
POST /api/users/register
```

Body:

```json
{
  "name": "Anvesh",
  "email": "test@gmail.com",
  "password": "123456"
}
```

---

## Login

```http
POST /api/users/login
```

Body:

```json
{
  "email": "test@gmail.com",
  "password": "123456"
}
```

---

# Categories APIs

## Get Categories

```http
GET /api/categories
```

## Create Category

```http
POST /api/categories
```

Body:

```json
{
  "name": "Food",
  "type": "EXPENSE"
}
```

---

# Transactions APIs

## Get Transactions

```http
GET /api/transactions
```

## Create Transaction

```http
POST /api/transactions
```

Body:

```json
{
  "amount": 500,
  "description": "Lunch",
  "date": "2026-05-26",
  "categoryId": 1
}
```

---

# Goals APIs

## Get Goals

```http
GET /api/goals
```

---

# Deployment

Backend deployed on:

```bash
Render
```

---

# Author

Anvesh Mahajan
