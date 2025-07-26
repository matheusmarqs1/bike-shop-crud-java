# 🚲 Bike Shop Management System (CRUD with JDBC, DAO, and Factory Pattern)

This is a simple Java project demonstrating a **CRUD application** for managing a bike shop, using **JDBC**, **DAO pattern**, and **Factory design pattern** for data access. The application interacts with a **MySQL database** that I designed for this project.

## 📋 Project Overview

The system handles operations for the following entities:

- **Clients**
- **Products**
- **Orders**
- **Order_Items** (associative entity between Orders and Products)

The main goal of this project was to practice database access in Java, applying clean architecture principles with proper separation of concerns.

---

## 📌 Features

✅ Create, Read, Update, Delete (CRUD) operations for:

- Clients  
- Products  
- Orders  

✅ Ability to manage **Order Items** (Many-to-Many relationship between Orders and Products)

✅ Organized using:

- **DAO Pattern**: Each entity has its own DAO interface and implementation
- **Factory Pattern**: DAO objects are instantiated through a Factory class
- **JDBC**: Direct connection and SQL execution with MySQL

---

## 🧱 Project Structure

```plaintext
src/
├── application/       // Entry point: main class (Program.java)
├── db/                // Database connection and low-level exceptions
├── exception/         // Custom domain and business exceptions
├── menu/              // User interaction menus and menu-specific validations
├── model/
│   ├── dao/           // DAO interfaces and their implementations
│   ├── entities/      // Domain entities: Client, Product, Order, etc.
├── service/           // Business logic layer: interfaces and implementations
├── util/              // Utility classes:
│   ├── ValidationUtils.java   // Generic validations (IDs, menu options)
│   └── AppUtils.java          // Common reusable helper methods
└── sql/               // SQL scripts for database creation
```
---

## 🛠️ Technologies Used

- **Java**
- **JDBC API**
- **MySQL Database**

---

## 🗃️ Database Schema (MySQL)

The database contains the following tables:

- `clients`
- `products`
- `orders`
- `order_items` (junction table for the many-to-many relationship between `orders` and `products`)

> The SQL script to create the database and tables is included in the `/sql` folder.

---

## 🏁 How to Set Up and Run

### 1. Prerequisites

- Java 17 or higher installed ([download](https://adoptium.net/))
- MySQL server running ([download](https://dev.mysql.com/downloads/installer/))
- MySQL Connector/J (JDBC driver) ([download .jar](https://dev.mysql.com/downloads/connector/j/))
- Recommended IDE: Eclipse, IntelliJ, or VS Code

### 2. Database Setup

- Create a database named `bike`
- Import the SQL script found in the `/sql` folder to create tables:
  ```sh
  mysql -u root -p < sql/schema.sql
  ```
- Make sure to note the user and password you will use

### 3. Project Setup

- Clone this repository:
  ```sh
  git clone https://github.com/matheusmarqs1/bike-shop-crud-java.git
  ```
- Import the project into your IDE
- Add the MySQL Connector/J `.jar` to your project's build path

### 4. Configure Database Connection

- Update your database credentials in `db.properties` (or in the code if applicable):
  ```
  db.url=jdbc:mysql://localhost:3306/bike_shop
  db.user=root
  db.password=yourpassword
  ```

### 5. Running the Application

- Compile the code (if using CMD):
  ```sh
  javac -d bin src/application/Program.java
  ```
- Run the main class:
  ```sh
  java -cp bin;path/to/mysql-connector.jar application.Program
  ```
  _On Windows, use `;` to separate paths. On Linux/Mac use `:`._

- Or simply run from your IDE (right-click `Program.java` → Run)

---

*If you have any issues or need help running the project, feel free to open an issue or contact me!*

---

## 📅 Current Progress

- [x] Database design (MySQL schema, ER diagram)
- [x] SQL scripts for table creation
- [x] Initial project structure (Java packages and classes)
- [x] DAO interfaces created
- [x] Utility classes for validation and common operations
- [x] DAO implementations
- [x] CRUD methods for each entity
- [x] Main application for testing

---

## 🤝 Contributing

This project is part of my personal learning journey in backend development with Java and MySQL.

Feel free to fork, suggest improvements, or use it as a reference for your own learning!

---

## 📄 License

Open for educational use.

---

**Developed by [🧑‍💻 Matheus Teles](https://github.com/matheusmarqs1)**
