# Equipment Maintenance API

A REST API for tracking equipment and maintenance tickets, built with Java and Spring Boot.

I built this project to get hands-on experience with Spring Boot and learn how a typical backend application is structured. The API lets you create equipment assets, open maintenance tickets for them, update ticket statuses, and filter tickets by status or priority.

## Features

- Create and view equipment assets
- Create maintenance tickets associated with an asset
- Update ticket status (`OPEN`, `IN_PROGRESS`, `RESOLVED`)
- Set ticket priority (`LOW`, `MEDIUM`, `HIGH`)
- Filter tickets by status and/or priority
- Request validation and HTTP error handling
- Persistent storage using H2 and Spring Data JPA
- Unit tests for the service layer using JUnit and Mockito

## Tech Stack

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- JUnit 5
- Mockito
- Maven

## Project Structure

```text
controller/    REST endpoints and HTTP handling
service/       Application and business logic
repository/    Database access using Spring Data JPA
model/         Ticket, Asset, and related enums
dto/           Request objects
exception/     API exceptions
```

Requests generally follow:

```text
HTTP Request
     ↓
Controller
     ↓
Service
     ↓
Repository
     ↓
H2 Database
```

## API

### Assets

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/assets` | Get all assets |
| `POST` | `/assets` | Create an asset |

Example:

```json
{
  "name": "CNC Machine #4",
  "location": "Building A"
}
```

### Maintenance Tickets

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/tickets` | Get all tickets |
| `GET` | `/tickets/{id}` | Get a ticket by ID |
| `POST` | `/tickets` | Create a maintenance ticket |
| `PATCH` | `/tickets/{id}/status` | Update a ticket's status |

Tickets can also be filtered using query parameters:

```text
GET /tickets?status=OPEN
GET /tickets?priority=HIGH
GET /tickets?status=OPEN&priority=HIGH
```

Example ticket request:

```json
{
  "description": "Hydraulic fluid leak",
  "priority": "HIGH",
  "assetId": 1
}
```

## Running Locally

Clone the repository and run:

```bash
mvn spring-boot:run
```

The API will be available at:

```text
http://localhost:8080
```

The project uses a local H2 database, so no external database setup is required.

## Tests

Run the test suite with:

```bash
mvn test
```

The service layer is unit tested with JUnit and Mockito, including ticket creation, asset validation, status updates, ticket lookup, and filtering.