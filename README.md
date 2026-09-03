# Patient Management Application

A microservices-based distributed architecture built with Spring Boot, Docker, and Apache Kafka to handle healthcare administration tasks including patient tracking, authentication, billing, and analytics.

---

## System Architecture & Services

* **API Gateway**: Central entry point routing traffic to internal services (`4003`).
* **Auth Service**: Handles authentication, user credentials, and JWT token issuing (`4004`).
* **Patient Service**: Core logic for patient profiles and operations. Communicates with Billing via gRPC and publishes events via Kafka.
* **Billing Service**: Service managing billing entities (`4001`) and gRPC interface (`9000`).
* **Analytics Service**: Event-driven analytics listener consuming Kafka message events (`4002`).
* **Apache Kafka**: Event streaming broker facilitating asynchronous communication.
* **PostgreSQL Databases**: Dedicated database containers for `patient-service` and `auth-service`.
