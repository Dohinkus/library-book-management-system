This directory contains all MySQL database files used by the Library Book Management System (LBMS).
It includes the full schema, seed data, and supporting scripts for creating and initializing the database.

Contents

schema.sql
Full MySQL DDL for all tables, constraints, and relationships.

seed-data.sql
Sample data for testing (publishers, authors, books, accounts, checkouts, waitlist).

README.md
This documentation file.

Requirements

MySQL 8.0 or higher
MySQL Workbench recommended
Database name: LBMS

To create the database manually:

CREATE DATABASE IF NOT EXISTS LBMS;
USE LBMS;

Setup Instructions

Run the schema file:

SOURCE schema.sql;

Load optional seed data:

SOURCE seed-data.sql;

The seed data includes example publishers, authors, books, series, accounts, checkouts, and waitlist entries.
This allows the team to immediately test the JavaFX application and backend.

Password Hashing Note

AppAccount stores the following:

Password: SHA-256 hash
Salt: randomly generated 32 bytes

Real passwords must be hashed at runtime using:

byte[] salt = PasswordUtils.generateSalt();
byte[] hash = PasswordUtils.hashPassword(rawPassword, salt);

Seed accounts use placeholder values (0x01 for both Password and Salt).
This keeps the seed script simple and allows the backend to replace these values with real hashes.

Schema Overview

The schema includes the following main parts:

Book
PhysicalBook
DigitalBook
Author and BookAuthor (many-to-many)
Publisher
Series and Volume
AppAccount
EmployeeAcc and MemberAcc
CheckOut
WaitList

Book availability is not stored. It is always calculated from:

Quantity minus count of active checkouts.

Waitlist entries are ordered by DatePlaced. No waitlist position column is used.

Transaction Safety

Checkout and return operations in the Java backend use database transactions.
checkoutBook uses SELECT ... FOR UPDATE on Book to prevent race conditions.
returnBook updates ReturnDate inside a transaction.

Development Tips

To reset the database:

SOURCE drop-all.sql;
SOURCE schema.sql;
SOURCE seed-data.sql;

To check availability manually:

SELECT b.ISBN, b.Title,
b.Quantity - COUNT(c.ISBN) AS AvailableCopies
FROM Book b
LEFT JOIN CheckOut c ON b.ISBN = c.ISBN AND c.ReturnDate IS NULL
WHERE b.ISBN = '9780451524935'
GROUP BY b.ISBN;

To view the waitlist for a specific book:

SELECT * FROM WaitList
WHERE ISBN = '9780345391803'
ORDER BY DatePlaced;

Testing Checklist

Insert a new book
Edit book metadata
Delete a book (only when Quantity = 0 and no active checkouts)
Search by title or ISBN
Create librarian and member accounts
Test login for both roles
Perform checkout
Perform return
Add user to waitlist
Show waitlist ordering
