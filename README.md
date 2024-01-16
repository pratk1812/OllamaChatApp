# StudentMVC

## Description
StudentMVC is a Spring MVC project designed to perform CRUD (Create, Read, Update, Delete) operations on Student Data. It uses Spring Security to implement authentication and authorization management.

## Roles
There are two default roles: "ADMIN" and "USER". 
- The "ADMIN" role has the privilege to perform all CRUD operations.
- The "USER" role can only perform read (SELECT) and add (INSERT) operations.

## Prerequisites
- Java 17
- Maven
- MySQL

## Dependencies
- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- Spring Boot Starter Thymeleaf
- Spring Boot Starter Web
- Thymeleaf Extras Springsecurity6
- MySQL Connector Java
- Spring Boot Starter Validation
- Spring Boot Starter Test
- Spring Boot Devtools
- Spring Security Test

## Setup
1. Clone the repository:

git clone https://github.com/pratk1812/StudentMVC.git

2. Navigate to the project directory:

cd StudentMVC

3. Build the project:

mvn clean install

4. Run the application:

mvn spring-boot:run

Visit `http://localhost:8080` in your browser to access the application.

## License
This project is licensed under the MIT License.