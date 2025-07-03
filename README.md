# SampleSQS_SDK_V2 🌟

A Spring Boot example demonstrating AWS SQS (Standard & FIFO) integration using AWS SDK v2 and ElasticMQ for local testing.

## ✅ Features

- Sending messages to Standard queue
- (Optionally) Support for FIFO queue
- Local SQS emulation using ElasticMQ (Docker)
- Spring Boot REST interface for message sending
- AWS SDK v2 for SQS operations

## 📁 Project Structure

```
src/
  main/java/com/example/sqs/
    controller/       # REST API for sending messages
    service/          # SqsSender encapsulates AWS SQS interactions (Use SqsAsyncSender for async call)
    config/           # AWS SDK & ElasticMQ endpoint configuration (SqsConfig)
    SampleSqsApp.java # Main @SpringBootApplication class
Dockerfile           # Container image build
docker-compose.yml   # Launches ElasticMQ + Spring Boot app
elasticmq.conf       # Custom ElasticMQ configuration
pom.xml              # Maven project configuration
README.md            # This documentation
```

## 🛠️ Prerequisites

- Java 8
- Maven 3.6+
- Docker & Docker Compose

## 🚀 Getting Started

### Build the project
```bash
mvn clean package -DskipTests
```

### Run locally with Docker Compose
```bash
docker-compose down -v
docker-compose up --build
```
- Spring Boot API: `http://localhost:8080`
- ElasticMQ SQS endpoint: `http://localhost:9324`

## 📡 Using the API

Send a message to the standard queue:
```bash
curl -X POST "http://localhost:8080/api/sqs/standard?message=HelloSQS"
```

<Response>
```
Sent to standard queue
```

## ⚙️ How it works

- `SqsSender` builds an `SqsClient` with the ElasticMQ endpoint.
- Spring injects this client into a service used by the REST controller.
- Messages are sent using `SendMessageRequest`.
- On-the-fly queue creation may be handled in `SqsConfig` or queue initializer.

## ⚠️ Troubleshooting

- **Connection refused to `localhost:9324`**?  
  Ensure Docker Compose correctly links `app` and `elasticmq` under a common network.  
  Example `docker-compose.yml` segment:

  ```yaml
  services:
    elasticmq:
      image: softwaremill/elasticmq-native
      ports:
        - "9324:9324"
    app:
      build: .
      ports:
        - "8080:8080"
      depends_on:
        - elasticmq
      environment:
        - AWS_SQS_ENDPOINT=http://elasticmq:9324
  ```

## 📝 Notes

- ElasticMQ simulates AWS SQS locally – no AWS account or network required.
- AWS SDK v2 provides modern, modular, and async-capable clients.
- You can extend this to include FIFO queues by enabling the commented FIFO endpoint in the controller.
