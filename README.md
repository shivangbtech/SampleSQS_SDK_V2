# Spring Boot SQS Integration with ElasticMQ (Local Setup)

This project demonstrates how to use **Spring Boot** with **AWS SQS** using **ElasticMQ** (a lightweight in-memory SQS-compatible message broker), suitable for local development and testing.

---

## ✅ Features

- SQS integration using Spring Cloud AWS
- FIFO and Standard Queue handling
- DLQ (Dead Letter Queue) setup
- Docker Compose integration with ElasticMQ
- Automatic queue creation at startup
- Message listener using `@SqsListener`

---

## 🧱 Project Structure

| Component               | Description                                                  |
|------------------------|--------------------------------------------------------------|
| `SqsDemoApplication`   | Main Spring Boot application class                           |
| `SqsQueueInitializer`  | Java class to auto-create queues if they don't exist         |
| `application.yml`      | Config to connect to ElasticMQ instead of AWS SQS            |
| `docker-compose.yml`   | Starts ElasticMQ and Spring Boot app using Docker Compose    |

---

## ⚙️ Prerequisites

- Java 8 or higher
- Maven 3.6+
- Docker & Docker Compose

---

## 🚀 Getting Started

### 1. Build the application:

```bash
mvn clean package -DskipTests
```

## Run using Docker Compose:
```bash
docker-compose down -v       # Clean existing volumes and containers

docker-compose up -d --build # Start the services
```
Your Spring Boot app will be accessible on: http://localhost:8080

ElasticMQ console (optional): http://localhost:9324

## cURL to use

### To post message to standard queue:
curl --location --request POST 'http://localhost:8080/api/sqs/standard?message=HelloSQS'