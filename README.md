## Patient Management Application

A microservices-based distributed architecture built with Spring Boot, Docker, and Apache Kafka to handle healthcare administration tasks including patient tracking, authentication, billing, and analytics.

## System Architecture & Services

- API Gateway: Central entry point routing traffic to internal services (4003).

- Auth Service: Handles authentication, user credentials, and JWT token issuing (4004).

- Patient Service: Core logic for patient profiles and operations. Communicates with Billing via gRPC and publishes events via Kafka.

- Billing Service: Service managing billing entities (4001) and gRPC interface (9000).

- Analytics Service: Event-driven analytics listener consuming Kafka message events (4002).

- Apache Kafka: Event streaming broker facilitating asynchronous communication.

- PostgreSQL Databases: Dedicated database containers for patient-service and auth-service.

## Prerequisites & Docker Network Setup

Before spinning up containers individually, ensure a shared Docker network named internal

exists so the containers can resolve each other by name: docker network create internal

## Configuration & Environment Setup

Below are the complete Docker run configurations for all services and infrastructure dependencies.

## 1. Databases

## auth-service-db

- Container Name: auth-service-db

Image: postgres:latest

- Bind Ports: 5001:5432

Bind Mounts: Docker_DB\db_volumes\auth-service-db:/var/lib/postgresql/data

- Run Options: --network internal

## Environment Variables:

| Variable | Value |
| --- | --- |
| POSTGRES_DB |  |
| POSTGRES_USER |  |


| Variable | Value |
| --- | --- |
| POSTGRES_PASSWORD | |
| PGDATA | /var/lib/postgresql/data |

## patient-service-db

Container Name: patient-service-db

Image: postgres:latest

- Bind Ports: 5000:5432

Bind Mounts: Docker_DB\db_volumes\patient-service-db:/var/lib/postgresql/data

Run Options: --network internal

## Environment Variables:

| Variable | Value |
| --- | --- |
| POSTGRES_DB |  |
| POSTGRES_USER |  |
| POSTGRES_PASSWORD |  |
| PGDATA |  |

## 2. Apache Kafka Broker

- Container Name: kafka

Image: apache/kafka:latest

Bind Ports: 9092:9092, 9094:9094

Run Options: --network internal

## Environment Variables:

| Variable | Value |
| --- | --- |
| KAFKA_PROCESS_ROLES | controller,broker |
| KAFKA_NODE_ID | 0 |
| KAFKA_LISTENERS | PLAINTEXT://:9092,CONTROLLER://:9093,EXTERNAL://:9094 |
| KAFKA_ADVERTISED_LISTENERS | PLAINTEXT://kafka:9092,EXTERNAL://localhost:9094 |
| KAFKA_LISTENER_SECURITY_PROTOCOL_MAP | CONTROLLER:PLAINTEXT,EXTERNAL:PLAINTEXT,PLAINTEXT:PLAINTEXT |
| KAFKA_CONTROLLER_LISTENER_NAMES | CONTROLLER |
| KAFKA_CONTROLLER_QUORUM_VOTERS | 0@kafka:9093 |
| KAFKA_CLUSTER_ID | MkU3OEVBNTcwNTJENDM2Qk |

## 3. Auth Service

- Container Name: auth-service

Dockerfile Path: Auth Service\Dockerfile

- Bind Ports: 4004:4004

Run Options: --network internal

## Environment Variables:


| Variable | Value |
| --- | --- |
| JWT_SECRET | 5aa58ce08d35965baaf7bce246a88f10ccf286f3 |
| SPRING_DATASOURCE_URL | jdbc:postgresql://auth-service-db:5432/db |
| SPRING_DATASOURCE_USERNAME |  |
| SPRING_DATASOURCE_PASSWORD |  |
| SPRING_JPA_HIBERNATE_DDL_AUTO | update |
| SPRING_SQL_INIT_MODE | always |

## 4. API Gateway

Container Name: api-gateway

Dockerfile Path: API Gateway\Dockerfile

Bind Ports: 4003:4003

Run Options: --network internal

## Environment Variables:

| Variable | Value |
| --- | --- |
| AUTH_SERVICE_URL | http://auth-service:4004 |

## 5. Patient Service

- Container Name: patient-service

Dockerfile Path: Patient Management\Dockerfile

Run Options: --network internal

## Environment Variables:

| Variable | Value |
| --- | --- |
| SPRING_DATASOURCE_URL | jdbc:postgresql://patient-service-db:5432/db |
| SPRING_DATASOURCE_USERNAME |  |
| SPRING_DATASOURCE_PASSWORD |  |
| SPRING_JPA_HIBERNATE_DDL_AUTO | update |
| SPRING_SQL_INIT_MODE | always |
| SPRING_KAFKA_BOOTSTRAP_SERVERS kafka:9092 |   |
| BILLING_SERVICE_ADDRESS | billing-service |
| BILLING_SERVICE_GRPC_PORT | 9000 |

## 6. Billing Service

- Container Name: billing-service

Dockerfile Path: Billing Service\Dockerfile

Bind Ports: 4001:4001, 9000:9000

Run Options: --network internal

## 7. Analytics Service

Container Name: analytics-service

Dockerfile Path: Analytics Service\Dockerfile


- Bind Ports: 4002:4002

Run Options: --network internal

## Environment Variables:

Variable

Value

SPRING_KAFKA_BOOTSTRAP_SERVERS kafka:9092

## Local Development Execution Order

To avoid service connection retries or boot-up failures, initialize and run the containers in the following order:

- 1. Create shared network: docker network create internal

- 2. Database instances (auth-service-db, patient-service-db)

- 3. Kafka broker (kafka)

- 4. Core support services (auth-service, billing-service)

- 5. Functional services (patient-service, analytics-service)

- 6. Edge entry point (api-gateway)
