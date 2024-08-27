# **Customer**

Customer is a Spring Boot-based RESTful web service that provides APIs for
managing customers. The application is designed to be scalable, maintainable,
and easy to use.

## **Table of Contents**

- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [API](#api)
- [Containerization](#containerization)
- [Additional Considerations](#considerations)

## **Features**

- RESTful APIs for CRUD operations.
- Integrated H2 in-memory database for development and testing.
- Support for PostgreSQL for production environments.
- Global exception handling and custom error responses.
- Docker support for containerization.

## **Requirements**

- **Java 17** or higher
- **Gradle 8.8** or higher
- **Spring Boot 3.3.3**
- **PostgreSQL** (Optional for localized running)
- **Docker** (optional, for containerized deployment)

## Installation

### **Clone the repository**
```bash
git clone https://github.com/connoj14/customer.git
cd customer
```

### **Build the Application**
This will run all tests and build the deployment artifact
```bash
gradlew clean install
```

### **Run the Application**
```bash
gradlew bootRun
```
The application will start at http://localhost:8080

The default profile will run an in-memory H2 database also used for component testing.
Running the application using the **cloud** profile will use a Postgres database. To do so:
```bash
Update ./src/main/resource/application-cloud.properties with your PosgtreSQL server settings

./gradlew bootRun --args='--spring.profiles.active=cloud'
```
## API
| Endpoint            | Method | Description             |
|---------------------|--------|-------------------------|
| /api/customers      | GET    | Retrieve all customers  |
| /api/customers/{id} | GET    | Retrieve customer by ID |
| /api/customers      | POST   | Create a new customer |
| /api/customers/{id} | PUT    | Update an existing customer |
| /api/customers/{id} | DELETE | Delete an existing customer |

For example, to create a new customer:
```bash
curl -X POST http://localhost:8080/api/customers \
-H "Content-Type: application/json" \
-d '{
  "firstName": "John",
  "lastName": "Doe",
  "address": "123 Main St, Anytown, USA",
  "phoneNumber": "123-456-7890",
  "dateOfBirth": "1990-01-01",
  "nationalSecurityNumber": "123-45-6789"
}'
```
## Containerization
### Docker
To build a docker image:
```bash
docker build -t customer .
```
To run the docker container:
```bash
docker run -d -p 8080:8080 customer
```
### PCF
### AWS ECS

## Considerations
Additional considerations regarding security, data integrity, testing and scaling

### Security and Authorization
**Endpoint-Based Authorization**: Currently, all endpoints are open. Future enhancements will include role-based access control (RBAC) using Spring Security.
**Data-Based Authorization**: Planned for future implementation, where users will have access to specific data based on their roles.

### Data Integrity
**Transactional Management**: The service layer is annotated with @Transactional to ensure that all operations within a transaction are completed successfully before the transaction is committed.
**Validation**: Data validation is performed at the DTO level using Java Bean Validation (JSR 380).

### Testing

### Scaling
**Horizontal Scaling**: The application can be scaled horizontally by increasing the number of replicas in Kubernetes or AWS ECS.
**Database Scaling**: Consider using MySQL read replicas for scaling the database and Redis clustering for scaling the cache layer.
