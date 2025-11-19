USE LBMS;

SET FOREIGN_KEY_CHECKS = 0;

-- Clear existing data
TRUNCATE WaitList;
TRUNCATE CheckOut;
TRUNCATE BookAuthor;
TRUNCATE Author;
TRUNCATE MemberAcc;
TRUNCATE EmployeeAcc;
TRUNCATE AppAccount;
TRUNCATE Volume;
TRUNCATE Series;
TRUNCATE DigitalBook;
TRUNCATE PhysicalBook;
TRUNCATE Book;
TRUNCATE Publisher;

SET FOREIGN_KEY_CHECKS = 1;

------------------------------------------------------------
-- PUBLISHERS
------------------------------------------------------------
INSERT INTO Publisher (Name, Address) VALUES
('Penguin Random House', '1745 Broadway, New York, NY'),
('HarperCollins', '195 Broadway, New York, NY'),
('O\'Reilly Media', '1005 Gravenstein Hwy N, Sebastopol, CA');

------------------------------------------------------------
-- AUTHORS
------------------------------------------------------------
INSERT INTO Author (FirstName, LastName, Email) VALUES
('George', 'Orwell', 'george.orwell@example.com'),
('J.K.', 'Rowling', 'jk.rowling@example.com'),
('Douglas', 'Adams', 'douglas.adams@example.com'),
('Mark', 'Lutz', 'mark.lutz@example.com');

------------------------------------------------------------
-- BOOKS
------------------------------------------------------------
INSERT INTO Book (ISBN, Quantity, Title, PublishDate, Pages, Edition, Genre, pId) VALUES
('9780451524935', 5, '1984', '1949-06-08', 328, '1st', 'Dystopian', 1),
('9780439708180', 10, 'Harry Potter and the Sorcerer''s Stone', '1997-06-26', 309, '1st', 'Fantasy', 2),
('9780345391803', 3, 'The Hitchhiker''s Guide to the Galaxy', '1979-10-12', 224, '1st', 'Sci-Fi', 1),
('9780596009205', 2, 'Learning Python', '2013-06-12', 1648, '5th', 'Programming', 3);

------------------------------------------------------------
-- FORMAT TABLES (Physical / Digital)
------------------------------------------------------------
INSERT INTO PhysicalBook (ISBN, CoverType) VALUES
('9780451524935', 'Paperback'),
('9780439708180', 'Hardcover'),
('9780345391803', 'Paperback');

INSERT INTO DigitalBook (ISBN, FileType, FileSizeMB) VALUES
('9780439708180', 'PDF', 12.5),
('9780345391803', 'EPUB', 3.2),
('9780596009205', 'PDF', 25.9);

------------------------------------------------------------
-- SERIES AND VOLUMES
------------------------------------------------------------
INSERT INTO Series (Name) VALUES
('Harry Potter'),
('The Hitchhiker Trilogy');

INSERT INTO Volume (ISBN, SeriesId, VolNum) VALUES
('9780439708180', 1, 1),  -- HP Volume 1
('9780345391803', 2, 1);  -- Hitchhiker Volume 1

------------------------------------------------------------
-- BOOK AUTHORS
------------------------------------------------------------
INSERT INTO BookAuthor (ISBN, aId, AuthorNth) VALUES
('9780451524935', 1, 1),    -- Orwell → 1984
('9780439708180', 2, 1),    -- Rowling → HP1
('9780345391803', 3, 1),    -- Adams → Hitchhiker
('9780596009205', 4, 1);    -- Lutz → Learning Python

------------------------------------------------------------
-- ACCOUNTS (PASSWORDS ARE PLACEHOLDERS)
-- Replace with real hashed values during runtime.
------------------------------------------------------------
INSERT INTO AppAccount
(Username, Password, Salt, Email, PhoneNum, FirstName, LastName, Address, Role)
VALUES
('admin', 0x01, 0x01, 'admin@library.com', '555-0001', 'System', 'Admin', 'Library HQ', 'LIBRARIAN'),
('lib_jane', 0x01, 0x01, 'jane.librarian@library.com', '555-0002', 'Jane', 'Doe', 'Library HQ', 'LIBRARIAN'),
('mem_alice', 0x01, 0x01, 'alice.member@example.com', '555-0003', 'Alice', 'Smith', '123 Elm St', 'MEMBER'),
('mem_bob',   0x01, 0x01, 'bob.reader@example.com', '555-0004', 'Bob', 'Brown', '777 Pine Ct', 'MEMBER');

------------------------------------------------------------
-- EMPLOYEES
------------------------------------------------------------
INSERT INTO EmployeeAcc (Username, StaffId, JobTitle) VALUES
('admin', 1001, 'Head Librarian'),
('lib_jane', 1002, 'Librarian');

------------------------------------------------------------
-- MEMBERS
------------------------------------------------------------
INSERT INTO MemberAcc (Username, CardNum, DisplayName) VALUES
('mem_alice', 2001, 'Alice S.'),
('mem_bob', 2002, 'Bob B.');

------------------------------------------------------------
-- CHECKOUTS (Active + Returned)
------------------------------------------------------------
INSERT INTO CheckOut (Username, ISBN, DateCheckedOut, ReturnDate) VALUES
('mem_alice', '9780451524935', '2025-03-01', NULL),      -- Active
('mem_bob', '9780439708180', '2025-02-20', '2025-03-05'), -- Returned
('mem_bob', '9780345391803', '2025-03-02', NULL);         -- Active

------------------------------------------------------------
-- WAITLIST EXAMPLE
------------------------------------------------------------
INSERT INTO WaitList (Username, ISBN, DatePlaced) VALUES
('mem_alice', '9780345391803', '2025-03-10 09:00:00'),
('mem_bob', '9780345391803', '2025-03-10 09:05:00');  -- FIFO order

------------------------------------------------------------
-- DONE
------------------------------------------------------------
SELECT 'Seed data inserted successfully.' AS Status;
