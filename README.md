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
gradlew clean build
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
### AWS ECS
#### Prerequisites
- AWS Account
- AWS CLI installed
- Docker with docker image as described in previous section
- IAM Role with permissions to create ECS resources, clusters, services and tasks
#### High Level Steps
Assumes you have tested the docker image locally
- Tag the Docker Image (to ease deployment process)
- Authenticate with AWS ECR and push the docker image
- Setup an AWS Cluster with an IAM role via the console
- Define a task definition to add a container with memory, CPU, port settings, environment configuration, etc
- Deploy the service on AWS ECS and monitor its progress

## Considerations
Additional considerations regarding security, data integrity, testing and scaling

### Security and Authorization
Currently, all endpoints are open. Securing endpoints would be implemented using Spring Security that has comprehensive support
for multiple authentication and authorization providers, protocols such as OIDC/OAuth2/SAML and RBAC/ABAC services.

Below are code snippets to add some basic authentication requiring the logged-in user to have the role of *ADMIN* to create, update or delete a customer.

Add the spring security dependency to the **build.gradle** file
```bash
implementation 'org.springframework.boot:spring-boot-starter-security'
```
Add a **SecurityConfig** component to enforce authentication with a **UserDetailsService** bean to add some test accounts with assigned roles
```bash
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/v1/**")
                        .authenticated()
                )
                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(
                User.withUsername("user")
                        .password(passwordEncoder().encode("password"))
                        .roles("USER")
                        .build()
        );

        manager.createUser(
                User.withUsername("admin")
                        .password(passwordEncoder().encode("adminpassword"))
                        .roles("ADMIN")
                        .build()
        );

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```
Add a **PreAuthorize** annotation to the appropriate controller methods to check for the required role
```bash
    private static final String ROLE_ADMIN = "hasRole('ADMIN')";
    
    ...
    
    @PostMapping
    @PreAuthorize(ROLE_ADMIN)
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }
```
Spring also provides support for more fine-grained checking on specific authorities or using **SPEL** to implement custom checking  

### Data Integrity
Spring Data and JPA has in-built support for transaction management including annotations for more granular control of transactions.

Java Bean Validation provides a comprehensive set of annotations to validate DTO field types, sizes, string patterns, etc.

Refer to the *Customer** bean for examples on first name, last name and national security number

### Testing

The current application includes unit and integration tests that ensure the spring context loads correctly and that the layers communicate correctly.
But a more comprehensive set of testing would be required in a CI/CD environment.

### Contract Tests
Ensure that the interactions between different microservices adhere to agreed-upon contracts (e.g., API interfaces).
 
### API Tests
Validate that the APIs function end-to-end as expected including authorization checking.

### Performance Tests
Assess the performance characteristics of the applicationusing tools like JMeter to mitigate against performance degradation introduced by software updates.

### Security Tests
Identify security vulnerabilities within your microservice, such as SQL injection, XSS, or authentication issues 
using tools such as SonarQube, Snyk, Blackduck, Checkmarx.

### Canary Testing (Optional)
Useful to have a dedicated canary microservice application to test major framework or software version changes before rolling out to entire suite of applications

### Chaos Testing (Optional)
Test the resilience of the microservice by intentionally introducing failures and ensuring the system can recover gracefully
using tools like Chaos Monkey

### Database Migration Tests
Verify that database migrations (schema changes, data migrations) can be applied and rolled back without issues
using a tool like Flyway

### Scaling
Given the application is developed as a standalone stateless microservice component and can be deployed to any containerised environment, it can be scaled horizontally by increasing the number of replicas in Kubernetes or AWS ECS.

Amazon RDS for PostgreSQL has built in support for performance, scaling and high availability as do Azure and Google Cloud.
Horizontal scaling of databases can be achieved with read replicas (off-loading queries to replicas)

Even though PostgreSQL soes not directly support sharding, it could be implemented in Spring. Another option is to migrate to a database like MongoDB or Redis that does support sharding.
This would be relatively easy given the application uses Spring Data.
