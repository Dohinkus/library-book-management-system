# Library Book Management System (LBMS)

The Library Book Management System (LBMS) is a local desktop Java application that helps librarians and library members complete common library tasks efficiently, including login authentication, catalog search, book management, and checkout/return workflows.

This project is built by Group 2 using:

- Java
- JavaFX
- JDBC
- MySQL and MySQL Workbench
- diagrams.net (for ERD design)

The project follows a layered architecture with clear separation between the user interface, business logic, and data access layers.

---

## Project Structure

All Java source code is located under:

src/main/java/lbms/

The following packages are used:

- gui/      JavaFX controllers and user interface logic
- dao/      Data Access Objects for database operations
- bo/       Business Objects containing application rules and workflows
- utils/    Shared utility classes such as database connections and password hashing
- model/    Entity classes such as Book, User, and Member

Database files are located under:

db/
    schema.sql
    seed-data.sql
    (optional) erd-diagram.drawio or ERD export

Documentation is located under:

docs/

---

## Branching and Workflow

The main branch is protected from accidental modification. It cannot be pushed to directly.

All work must be performed in feature branches using the format:

feature/<short-description>-<your-name>

Examples:
- feature/books-crud-erik
- feature/login-ui-brycen
- feature/auth-backend-elisa
- feature/db-schema-everette

All changes must be merged into main through pull requests.

---

## Getting Started

Clone the repository:

git clone https://github.com/Dohinkus/library-book-management-system.git
cd library-book-management-system

TODO Fill this out

---

## Running the Application

TODO Fill this out

---

## Team Members and Roles

Christian McCool       Project Manager and repository maintainer
Erik Barbieri          Backend (BO and DAO collaboration)
Elisa Garcia           Backend (BO and DAO collaboration)
Everette Carrig        Database design and DAO
William Henderson      Database design and DAO
Brycen Canion          GUI development with JavaFX
Brianna Hendrix        Documentation, quality assurance, testing, and demo preparation

All members are encouraged to assist outside their primary role as needed.

---

## Documentation Overview

The following documents are provided:

- CONTRIBUTING.md           How to work on the project
- docs/COMMIT_MESSAGES.md   Commit message rules
- docs/design-decisions.md  Architectural and technical decisions
- docs/test-plan.md         QA testing procedures
- db/schema.sql             Database schema

---

## Features

The system includes or will include:

- Authentication for librarians and members
- User role separation (read-only vs. management features)
- Book catalog search
- Full CRUD for the Books table (librarian only)
- Checkout and return workflows with constraints
