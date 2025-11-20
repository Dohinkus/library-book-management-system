# Database Scripts Overview (db/ directory)

This folder contains all SQL files used to create, reset, and seed the LBMS database for the project. Each file has a specific purpose. The descriptions below explain what each file is, who would use it, when it should be used, and how to run it.

# Typical usage order:

drop-all-tables.sql (optional, for resets)

mysql-ddl.sql

views.sql

seed-data.sql (if sample data is needed)

# More information

drop-all-tables.sql
What this file is:
A reset script that forcefully removes all tables from the LBMS database.

Who uses it:
Developers or instructors who need to wipe the database during testing or before rebuilding it.

When it is used:
Before recreating the schema, or when starting fresh during development.

How to use it:
Run it directly in MySQL. Example:
mysql < drop-all-tables.sql

mysql-ddl.sql
What this file is:
The main schema definition for the LBMS database. It creates the database, tables, indexes, and foreign keys.

Who uses it:
Anyone who needs to create the actual database structure (developers, instructors, automated setup scripts).

When it is used:
After resetting the database or when setting up the database for the first time.

How to use it:
Run this file after drop-all-tables.sql. Example:
mysql < mysql-ddl.sql

relational-schema.sql
What this file is:
A non-executable description of the database tables in a logical/schema notation. It documents the relational design but is not meant to be run by MySQL.

Who uses it:
Students, reviewers, and instructors reading the design. It is for understanding structure, not building the database.

When it is used:
During design review or when referencing how tables relate conceptually.

How to use it:
Do not run this file. Read it for documentation only.

seed-data.sql
What this file is:
A data seeding script that fills the LBMS database with example data for testing (publishers, authors, books, accounts, checkouts, etc.).

Who uses it:
Anyone who needs test data in a fresh database.

When it is used:
After the schema has been created and you need a working dataset to test system features.

How to use it:
Run it after mysql-ddl.sql. Example:
mysql < seed-data.sql

views.sql
What this file is:
A script that creates database views such as BookAvailability and ActiveCheckouts. These views simplify common queries.

Who uses it:
Developers and instructors who need the derived views available in the database.

When it is used:
After the tables are created (mysql-ddl.sql). Views depend on the schema existing first.

How to use it:
Run it any time after the schema exists. Example:
mysql < views.sql
