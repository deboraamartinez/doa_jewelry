# DeOroAtelier Store - Projeto 3

This is a Java-based management system for a jewelry store, built using **Spring Boot** and **Maven**. The system allows managing customers, employees, jewelry inventory, orders, and payments. Data persistence is handled with a **PostgreSQL** database, ensuring reliability and scalability. It includes robust validations and exception handling to maintain data integrity.



### Features

- **Customer Management**: Create, read, update, and delete customer records.
- **Employee Management**: Manage employee data, including managers and salespersons.
- **Jewelry Inventory**: Add and manage jewelry items like rings and necklaces.
- **Order Processing**: Create orders for customers, manage order items, and track order statuses.
- **Payment Handling**: Process payments for orders, ensuring payments do not exceed order totals.
- **Data Persistence**: Uses a PostgreSQL database for storing all data.
- **Unit Testing**: Comprehensive unit tests using JUnit to ensure code reliability.
- **REST API**: Exposes endpoints for managing the system's entities, documented with Swagger.

### Project Structure

The project follows a layered architecture with clear separation of concerns:

- **Entity Layer (`entity`)**: Contains classes representing the core business objects, such as `Customer`, `Employee`, `Jewelry`, `Order`, and `Payment`.
- **Repository Layer (`repository`)**: Interfaces that manage data persistence, powered by Spring Data JPA and PostgreSQL.
- **Service Layer (`service`)**: Contains business logic and validations. Services interact with repositories to perform operations.
- **Controller Layer (`controller`)**: Defines REST endpoints for interacting with the system's features.
- **DTOs (`dto`)**: Data Transfer Objects used to encapsulate and transfer data between layers.
- **Exceptions (`exception`)**: Custom exception classes to handle specific error scenarios and maintain clean code.
- **Enums (`entity`)**: Enumerations used within the entities, such as `MaterialType`, `JewelryCategory`, `OrderStatus`, and `PaymentMethod`.
- **Configuration (`config`)**: Contains configuration classes, such as database and Swagger setup.
- **Tests (`test`)**: Contains unit and integration tests for services and controllers to validate functionality.

### Getting Started

#### Prerequisites

- Java Development Kit (JDK) 17 or higher
- An IDE like IntelliJ IDEA, Eclipse, or NetBeans
- Maven (build tool)
- Docker (optional, for containerized deployment)
- Swagger for Documentation

#### Installing

1. **Clone the Repository**

   ```bash
   git clone https://github.com/seu-usuario/deoroatelier-store.git 
2. **Access the project folder:**:
    ```bash
    cd deoroatelier-store
3. **Run the container:**:
    ```bash
    docker compose up --build
4. **Access Swagger to explore API endpoints:**
    ```bash
    http://localhost:8080/swagger-ui/index.html
5. **Shutting Down Containers**:
    ```bash
    docker compose down