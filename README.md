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
├── db/               // Database connection and exceptions
├── entities/         // Entity classes: Client, Product, Order, OrderItem
├── dao/              // DAO interfaces and implementations
└── application/      // Demo class with main() for testing the CRUD operations
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

## 🚀 How to Run (when completed)

Instructions on how to set up the database, configure the connection, and run the project will be added here soon.

---

## 📅 Current Progress

- [x] Database design (MySQL schema, ER diagram)
- [x] SQL scripts for table creation
- [x] Initial project structure (Java packages and classes)
- [x] DAO interfaces created
- [ ] DAO implementations
- [ ] CRUD methods for each entity
- [ ] Main application for testing

---

## 🤝 Contributing

This project is part of my personal learning journey in backend development with Java and MySQL.

Feel free to fork, suggest improvements, or use it as a reference for your own learning!

---

## 📄 License

Open for educational use.

---

**Developed by [🧑‍💻 Matheus Teles](https://github.com/matheusmarqs1)**
