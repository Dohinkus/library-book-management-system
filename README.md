# Library Book Management System (LBMS)

The Library Book Management System (LBMS) is a local desktop Java application that helps librarians and library members complete common library tasks efficiently, including login authentication, catalog search, and checkout/return workflows.

This project is built by Group 2 using:

- Java
- JavaFX
- JDBC
- MySQL and MySQL Workbench
- diagrams.net (for ERD design)

The project follows a layered architecture with clear separation between the user interface, business logic, and data access layers.

---

## How to Run Everything

Requirements: Install Java JDK 17, Maven, MySQL Community Server 8.0, MySQL Workbench, and Git


Run MySQL Server and run the scripts in the /db files ([drop-all-tables.sql] -> mysql-ddl.sql -> views.sql -> seed-data.sql) in Workbench


Make sure your database is called "LBMS" and your user/pass is "root" "root"

OR edit src/main/java/lbms/utils/DBConnection.java to match your database name and login info


Clone the repository:
```
git clone https://github.com/Dohinkus/library-book-management-system.git
```

Make sure you are in the repository's directory:
```
cd library-book-management-system
```

Compile the application:
```
mvn clean compile
```

Run the application:
```
mvn exec:java
```


The application will start, and you can login as "admin_demo" "admin123" or as "member_demo" "member123" to demo the application
There's currently no logout button, so close and re-run the application if you wish to demo both admin (librarian) and member (library member)

---

## Project Structure

All Java source code is located under:

src/main/java/lbms/

The following packages are used:

- gui/      JavaFX controllers and user interface logic
- dao/      Data Access Objects for database operations
- bo/       Business Objects containing application rules and workflows
- utils/    Shared utility classes such as database connections and password hashing

Database files are located under:

db/
    drop-all-tables.sql
    mysql-ddl.sql
    relational-schema.sql
    seed-data.sql
    views.sql

Documentation is located under:

docs/

---

## Team Members and Roles

- Christian McCool:       Project Manager
- Erik Barbieri:          Backend (BO and DAO collaboration) and repository maintainer
- Elisa Garcia:           Backend (BO and DAO collaboration)
- Everette Carrig:        Database design
- William Henderson:      Database design
- Brycen Canion:          GUI development with JavaFX
- Brianna Hendrix:        Documentation, quality assurance, testing, and demo preparation

All members are encouraged to assist outside their primary role as needed.

---

## Features

The system includes or will include:

- Authentication for librarians and members
- User role separation (read-only vs. management features)
- Book catalog search
- Checkout and return workflows with constraints
