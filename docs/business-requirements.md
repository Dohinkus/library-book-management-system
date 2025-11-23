!!!!WIP!!!

The Library Book Management System is an offline desktop application that replaces paper and spreadsheets. It runs on a single workstation with a local relational database, requires no internet, and supports export for backup. The library is responsible for backups and restores.

Two UserTypes exist: librarian (StaffAccount) and member (UserAccount). Everyone has a unique username and a salted, hashed password. Librarian accounts include a staff ID and job title. Member accounts include a card number. Only authenticated users can use the system.

Librarians manage books with full create, read, update, and delete rights. 
A book requires a title, at least one author, a genre, ISBN, published year, and exactly one publisher. Series name and volume number are optional. Books have two formats. Physical books store cover type. Digital books store file size and file type. The system tracks total copies and available copies and enforces 0 ≤ available ≤ total. Checked-out count is derived as total minus available and is not stored.

Catalog search supports title, author, and ISBN and returns paged results with availability. A details view shows full metadata. Members have read-only access to search and details. Members cannot add, edit, or delete records.

Circulation applies to books. A checkout records a unique ID, the member, the book, the checkout date, the due date, and later the return date. The default loan period is 14 days. A checkout succeeds only if at least one copy is available, which decrements availability by one. A return increments availability by one and never allows availability to exceed total copies. The system prevents overlapping checkouts of the same physical copy. Renewals are out of scope for the first release.

Deleting a book is allowed only when total copies are zero and no copies are checked out or on hold. If a deletion is attempted sooner, the system blocks it and explains why.

An author can write many books, and a book can have many authors. A publisher can publish many books, but a book has exactly one publisher. A book cannot be saved without a publisher. Author records store name and optional email. Publisher records store name and address. Authors or publishers referenced by books cannot be deleted.

User accounts, books, authors, publishers, and circulation records enforce uniqueness and referential integrity. Usernames and emails are unique. Optional phone and postal address may be stored. All mutable entities capture created-at and updated-at timestamps. The system records which librarian performed each create, update, checkout, return, or delete, and librarians can view the audit history.

Input is validated with clear error messages that do not expose sensitive details.

Success means a librarian can sign in, add books, search, edit metadata and copy counts, check out and return books, manage holds, and delete only when circulation is clear. 
A member can sign in, search, and view details without modifying data. The system enforces availability rules and prevents invalid operations.

-------------------------------------------------------------------------------------------------------------------------------------------
Book: ISBN, title, date, pages, edition, availability, status, genre
  Physical: cover type
  Digital: file size, file type
Author: id, first name, last name, email
Publisher: id, name, address
Volume: volume number, series name
Checkout: order id, date checked out, return date
AppAccount: username, password, first name, last name, email, phone number, address
  EmployeeAccount: staff id, job title
  MemberAccount: card number
