# DeOroAtelier Store Management System (DOA)

The **DeOroAtelier Store Management System (DOA)** is a comprehensive management solution for the DeOroAtelier Store, a renowned jewelry retailer. This system efficiently manages employees, customers, suppliers, jewelry inventory, orders, and payments, ensuring a seamless and organized operational workflow.

---

## Repository Structure

The repository is organized as follows:

- **`model.pdf`**: Entity-Relationship (ER) diagram of the system.
- **`sql/create_schema.sql`**: SQL script to create the database and tables.
- **`sql/insert_data.sql`**: SQL script to populate the database with example data.
- **`sql/queries.sql`**: SQL script with sample queries to demonstrate the system's functionality.


### Steps to Run

#### Prerequisites

- [Docker](https://www.docker.com/) installed on your machine.
- Docker Compose version `3.9` or higher.

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/deboraamartinez/doa_jewelry.git
   cd doa_jewelry/data-modeling

2. **Start the container using Docker Compose**:
   ```bash
   docker-compose up -d

