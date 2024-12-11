## Jewelry Store Management System For DOA Master's class

This is a Java-based management system for a jewelry store. It allows managing customers, employees, jewelry inventory, orders, and payments. The system persists data in CSV files and ensures data integrity with validations and exception handling.

### Features

- **Customer Management**: Create, read, update, and delete customer records.
- **Employee Management**: Manage employee data, including managers and salespersons.
- **Jewelry Inventory**: Add and manage jewelry items like rings and necklaces.
- **Order Processing**: Create orders for customers, manage order items, and track order statuses.
- **Payment Handling**: Process payments for orders, ensuring payments do not exceed order totals.
- **Data Persistence**: All data is stored in CSV files for simplicity and ease of access.
- **Unit Testing**: Comprehensive unit tests using JUnit to ensure code reliability.

### Project Structure

The project follows a layered architecture with clear separation of concerns:

- **Entity Layer (`entity`)**: Contains classes representing the core business objects, such as `Customer`, `Employee`, `Jewelry`, `Order`, and `Payment`.
- **Repository Layer (`repository`)**: Manages data persistence for entities. Each repository class handles reading from and writing to CSV files.
- **Service Layer (`service`)**: Contains business logic and validations. Services interact with repositories to perform operations.
- **Controller Layer (`controller`)**: Acts as an interface between the services and the user interface or startup scripts. Controllers handle input and output data formats.
- **Startup (`startup`)**: Initializes the application, sets up initial data, and demonstrates the usage of various components.
- **DTOs (`dto`)**: Data Transfer Objects used to encapsulate data, such as combining orders with their associated payments.
- **Exceptions (`exception`)**: Custom exception classes to handle specific error scenarios and maintain clean code.
- **Enums (`entity`)**: Enumerations used within the entities, such as `MaterialType`, `JewelryCategory`, `OrderStatus`, and `PaymentMethod`.
- **Tests (`test`)**: Contains unit tests for services and controllers to validate functionality.

### Getting Started

#### Prerequisites

- Java Development Kit (JDK) 8 or higher
- An IDE like IntelliJ IDEA, Eclipse, or NetBeans
- Maven or Gradle (if you're using build tools)

#### Installing

1. **Clone the Repository**

   ```bash
   git clone https://github.com/deboraamartinez/doa_jewelry.git
   ```
#### Running the Application

The application can be run by executing the main method in the StartupInitializer class.

1.  **Initialize Data**
The initializeData() method in StartupInitializer sets up initial data for testing and demonstration purposes.

2. **Run the Application**
In your IDE, run the main method of the StartupInitializer class.
Observe the console output to see the results of the operations performed.
