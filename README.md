# Spring Library API

A Java Spring Boot REST API for managing books, students and library borrowings.  
The project demonstrates backend development with Spring Boot, database interaction with Spring Data JPA, validation, error handling, health checks and unit testing.

## Tech Stack

- Java 17
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- H2 in-memory database
- MySQL driver
- Jakarta Validation
- Spring Boot Actuator
- JUnit 5
- Mockito
- Maven

## Features

- Create and list books
- Search books by title
- Create and list students
- Search students by last name
- Borrow an available book
- Prevent borrowing a book that is already borrowed
- Return a borrowed book
- View active borrowings
- Global API error handling
- Health endpoint with Spring Boot Actuator
- Unit tests for borrowing workflow

## API Endpoints

### Books

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/books` | List all books |
| POST | `/api/books` | Create a new book |
| GET | `/api/books/search?title=java` | Search books by title |

### Students

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/students` | List all students |
| POST | `/api/students` | Create a new student |
| GET | `/api/students/search?lastName=sag` | Search students by last name |

### Borrowings

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/borrowings/active` | List active borrowings |
| POST | `/api/borrowings` | Borrow a book |
| POST | `/api/borrowings/{id}/return` | Return a borrowed book |

### Monitoring

| Method | Endpoint | Description |
|---|---|---|
| GET | `/actuator/health` | Application health check |

## Running The Application

```bash
mvn spring-boot:run
```

# The app runs on:

```bash
http://localhost:8080
```

## Example Requests

# Create a book:

```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Clean Code","author":"Robert C. Martin","genre":"Programming"}'
```
# Create a student:

```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Madina","lastName":"Sagatova","email":"madina@example.com"}'
```

# Borrow a book:

```bash
curl -X POST http://localhost:8080/api/borrowings \
  -H "Content-Type: application/json" \
  -d '{"bookId":1,"studentId":1}'
```
# Return a book:

```bash
curl -X POST http://localhost:8080/api/borrowings/1/return
```
## Testing

# Run tests:

# mvn test
Current test coverage includes borrowing workflow rules:

borrowing an available book
preventing duplicate borrowing
returning a borrowed book
preventing duplicate return
Database
The project uses H2 in-memory database for local development.

# H2 console:
```bash
http://localhost:8080/h2-console
```
# JDBC URL:

```bash
jdbc:h2:mem:librarydb
```
# Error Handling
The API returns structured error responses for validation, not-found and business-rule errors.
```bash
Example:

{
  "timestamp": "2026-05-18T20:30:00",
  "status": 409,
  "error": "Conflict",
  "message": "Book is already borrowed"
}
```
## Why This Project
This project was built as a portfolio backend application to practise Java, Spring Boot, REST API development, SQL-style persistence, validation, error handling, testing and production-support concepts such as health checks.
