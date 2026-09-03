Products API Assessment

A RESTful Product Management API built using Java, Spring Boot, Spring Data JPA, Hibernate, MySQL, Spring Security, JWT, JUnit 5, Mockito, Swagger/OpenAPI and Docker.

1. Project Overview

This project provides REST APIs for:
Product CRUD operations
Item CRUD operations
Product-Item relationship
JWT authentication
Request validation
Pagination
Standardized error handling
Unit testing
Repository testing
Controller testing
Integration testing
Swagger/OpenAPI documentation
Docker support

2. Technologies Used

Technology Used:Java 17+,Programming language,Spring Boot,Backend framework,Spring Data JPA,Database access,Hibernate,ORM
MySQL,Main database,H2,Testing database,Spring Security,Security,JWT,Authentication,Maven,Build tool,JUnit 5,Testing,Mockito,Mocking
MockMvc,Controller testing,Swagger/OpenAPI,API documentation,Docker,Containerization,Git/GitHub,Version control

3. Project Structure

productsApi
├── src
│   ├── main
│   │   ├── java/com/product
│   │   │   ├── Controller
│   │   │   ├── Entity
│   │   │   ├── Repository
│   │   │   ├── Service
│   │   │   ├── Security
│   │   │   ├── dto
│   │   │   └── Exception
│   │   └── resources
│   │       └── application.properties
│   └── test
│       ├── java/com/product
│       │   ├── Controller
│       │   ├── Service
│       │   ├── repository
│       │   └── Integration
│       └── resources
│           └── application-test.properties
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── .gitignore
└── README.md

4. How the Project Was Created From Beginning

Step 1: Create Spring Boot Project

Create the project using Spring Initializr.

Configuration:

Project: Maven
Language: Java
Java: 17+
Packaging: Jar

Add these dependencies:

Spring Web
Spring Data JPA
Spring Security
Validation
MySQL Driver
H2 Database
Lombok
Spring Boot Test

Import the project into STS/Eclipse.

Step 2: Create MySQL Database

Open MySQL and run:

CREATE DATABASE productsdb;

Verify:

SHOW DATABASES;

Step 3: Configure Database

Create/edit:

src/main/resources/application.properties

Example:

spring.application.name=productsApi

spring.datasource.url=jdbc:mysql://localhost:3306/productsdb
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8080

jwt.secret=YOUR_JWT_SECRET

Do not commit real passwords or JWT secrets to GitHub.

Step 4: Create Entity Layer

Create:

Product
Item
User

Product fields:

id
productName
createdBy
createdOn
modifiedBy
modifiedOn

Item fields:

id
quantity
product

User fields:

id
username
password
role
createdOn

Relationship:

Product 1 -------- * Item

Step 5: Create Repository Layer

Repositories extend JpaRepository.

Example:

public interface ProductRepository
        extends JpaRepository<Product, Long> {
}

Item repository contains:

findByProductId()
existsByProductId()
countByProductId()

Step 6: Create DTO Layer

Create request and response DTOs:

productrequestdto
productresponsesto
itemrequest
itemresponsedto

DTOs prevent directly exposing entities through the API.

Step 7: Create Service Layer

Create:

ProductService
ItemService
AuthService

Application flow:

Controller
    ↓
Service
    ↓
Repository
    ↓
Database

Step 8: Create Controller Layer

Create:

ProductController
ItemController
AuthController

Base API path:

/api/v1/

Step 9: Add Exception Handling

Create:

ResourceNotFoundException
ErrorResponse
GlobalExceptionHandler

Example error:

{
  "timestamp": "2026-09-03T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Product with id 100 not found",
  "path": "/api/v1/products/100"
}

Step 10: Add Validation

Use Jakarta Validation.

Example:

@NotBlank
@Size(max = 255)
private String productName;

For quantity:

@NotNull
@Min(1)
private Integer quantity;

Invalid input returns:

400 Bad Request

5. Authentication

The application uses Spring Security and JWT.

Authentication flow:

Register
   ↓
Login
   ↓
Username + Password
   ↓
Authentication
   ↓
JWT Token
   ↓
Bearer Token
   ↓
Protected API

Register

POST /api/v1/auth/register

Body:

{
  "username": "hitesh",
  "password": "123456",
  "role": "USER"
}

Login

POST /api/v1/auth/login?username=hitesh&password=123456

Copy the returned JWT token.

In Postman:

Authorization
→ Type: Bearer Token
→ Paste JWT

6. Product APIs

Create Product

POST /api/v1/products

Body:

{
  "productName": "Laptop",
  "createdBy": "Hitesh"
}

Get All Products

GET /api/v1/products

Pagination:

GET /api/v1/products?page=0&size=10

Get Product

GET /api/v1/products/1

Update Product

PUT /api/v1/products/1

Body:

{
  "productName": "Gaming Laptop",
  "createdBy": "Hitesh",
  "modifiedBy": "Admin"
}

Delete Product

DELETE /api/v1/products/1

Response:

204 No Content

7. Item APIs

Create Item

POST /api/v1/products/1/items

Body:

{
  "quantity": 10
}

Get Items By Product

GET /api/v1/products/1/items

Get Item By ID

GET /api/v1/products/items/1

Update Item

PUT /api/v1/products/items/1

Body:

{
  "quantity": 20
}

Delete Item

DELETE /api/v1/products/items/1

8. Pagination

Products support pagination using Spring Data Pageable.

Example:

GET /api/v1/products?page=0&size=10

Where:

page = page number
size = records per page

9. Swagger

Start the application and open:

http://localhost:8080/swagger-ui.html

Swagger provides an interactive API testing interface.

10. Testing

Testing technologies:

JUnit 5
Mockito
Spring Boot Test
MockMvc
H2

Test types:

Service Tests
Controller Tests
Repository Tests
Integration Tests

Example test classes:

ProductServiceTest
ItemServiceTest
AuthServiceTest
ProductControllerTest
ItemControllerTest
productrepositorytest
ItemRepositoryTest
ProductApiIntegrationTest

Run all tests:

mvn clean test

Run a specific test:

mvn clean test -Dtest=ItemRepositoryTest

11. H2 Test Database

Repository and integration tests can use H2 instead of MySQL.

Example:

spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

jwt.secret=ThisIsASecretKeyForTestingOnly123456789

12. Build and Run

Build:

mvn clean package

Build without tests:

mvn clean package -DskipTests

Run with Maven:

mvn spring-boot:run

Run JAR:

java -jar target/productsApi-*.jar

Application URL:

http://localhost:8080

13. Docker

Build the application:

mvn clean package -DskipTests

Start Docker:

docker compose up --build

Stop Docker:

docker compose down

Remove containers and database volume:

docker compose down -v

14. Git and GitHub

Initialize Git:

git init

Add files:

git add .

Commit:

git commit -m "Initial commit"

Set main branch:

git branch -M main

Add remote:

git remote add origin https://github.com/hiteshkalaL123/ProductsApiAssessment.git

Verify:

git remote -v

Push:

git push -u origin main

For future changes:

git add .
git commit -m "Updated Product API"
git push

15. .gitignore

Recommended .gitignore:

target/
.classpath
.project
.settings/
.vscode/
.idea/
*.iml

src/main/resources/application.properties

Never commit:

Database passwords
JWT secrets
API keys
Access tokens
Private credentials

16. Application Architecture

                    Client
                      |
                      ↓
                  REST API
                      |
                      ↓
                  Controller
                      |
                      ↓
                    DTO
                      |
                      ↓
                   Service
                      |
                      ↓
                 Repository
                      |
                      ↓
                 JPA/Hibernate
                      |
                      ↓
                   MySQL

JWT flow:

Client
  ↓
Login
  ↓
Spring Security
  ↓
JWT Token
  ↓
Authorization Header
  ↓
JWT Filter
  ↓
Security Context
  ↓
Protected API

17. Database Relationship

+----------------+
|    PRODUCT     |
+----------------+
| id             |
| product_name   |
| created_by     |
| created_on     |
| modified_by    |
| modified_on    |
+----------------+
        |
        | 1
        |
        | *
        ↓
+----------------+
|      ITEM      |
+----------------+
| id             |
| quantity       |
| product_id     |
+----------------+

One Product can have multiple Items.
Register a user:
POST /api/v1/auth/register
Login:
POST /api/v1/auth/login
Copy the JWT token.
Add it to Postman:
Authorization → Bearer Token
Now test the Product and Item APIs.
