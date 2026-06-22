# KawaiiDB 🌸

A lightweight database engine built from scratch in Java 17 to understand the fundamentals of database internals, storage engines, query execution, and file persistence.

## Features

* Create tables with custom schemas
* Insert records into tables
* Retrieve all records using SELECT
* Filter records using WHERE conditions
* Update existing records
* Delete records matching conditions
* File-based persistence using `.tbl` files
* Automatic table loading on startup
* Schema and metadata management
* Basic data type validation
* SQL-like command-line interface
* Basic indexing infrastructure

---

## Architecture

```text
Main
│
├── SqlParser
│
├── Database
│   ├── Table
│   ├── Row
│   ├── Schema
│   └── Column
│
├── StorageManager
│
└── Index Manager
```

---

## Functionality

### Table Creation

Create tables with custom columns.

```sql
CREATE TABLE students (id,name)
```

---

### Insert Operation

Insert records into a table.

```sql
INSERT INTO students VALUES (1,Ragini)
INSERT INTO students VALUES (2,Sakura)
```

---

### Select Operation

Retrieve all records from a table.

```sql
SELECT * FROM students
```

Output:

```text
id    name
1     Ragini
2     Sakura
```

---

### WHERE Filtering

Retrieve records matching a condition.

```sql
SELECT * FROM students WHERE id=1
```

Output:

```text
id    name
1     Ragini
```

---

### Update Operation

Update existing records.

```sql
UPDATE students SET name=Sakura WHERE id=1
```

Output:

```text
1 row updated.
```

---

### Delete Operation

Remove records matching a condition.

```sql
DELETE students id 1
```

Output:

```text
1 row deleted.
```

---

### File Persistence

KawaiiDB stores table data in local `.tbl` files. Data remains available even after restarting the application.

Example:

```text
students.tbl

id,name
1,Ragini
2,Sakura
```

Benefits:

* Persistent storage
* Automatic recovery on startup
* No external database dependency
* Simple storage engine implementation

---

## Screenshots

### Creating a Table

<img width="1920" height="1080" alt="Screenshot (553)" src="https://github.com/user-attachments/assets/ee19f4e8-7562-445c-a337-5f67fdf62808" />


### Inserting Records

<img width="1920" height="1080" alt="Screenshot (554)" src="https://github.com/user-attachments/assets/ee8345f4-1fb0-4ca5-a986-8bde21c3504f" />


### Deleting Records
<img width="1920" height="1080" alt="Screenshot (564)" src="https://github.com/user-attachments/assets/a388781c-df55-4e54-baf1-53e417aa6fe9" />



### File Persistence

<img width="1920" height="1080" alt="Screenshot (558)" src="https://github.com/user-attachments/assets/b16154ad-4623-4ba2-9f71-72fc227eee3b" />


---

## Technologies Used

* Java 17
* IntelliJ IDEA
* Maven
* Java Collections Framework
* File I/O
* Object-Oriented Programming

---

## What I Learned

* Database architecture fundamentals
* Designing tables, schemas, rows, and columns
* SQL parsing and command execution
* CRUD operation implementation
* File-based data persistence
* Java File I/O and storage management
* Data validation and type checking
* HashMap and ArrayList usage
* Indexing concepts
* Building modular software systems

---

## Future Improvements

* B+ Tree indexing
* JOIN operations
* ORDER BY support
* GROUP BY and aggregate functions
* Transaction management (COMMIT / ROLLBACK)
* Write Ahead Logging (WAL)
* Query optimizer


---

## Project Goal

The goal of KawaiiDB is to gain hands-on experience with how real database systems such as MySQL, PostgreSQL, and SQLite work internally by implementing core database components from scratch using Java.
