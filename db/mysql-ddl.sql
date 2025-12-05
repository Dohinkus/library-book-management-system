CREATE DATABASE IF NOT EXISTS LBMS
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_general_ci;

USE LBMS;

------------------------------------------------------------
-- PUBLISHERS & BOOKS
------------------------------------------------------------

CREATE TABLE Publisher (
    Id          INT AUTO_INCREMENT PRIMARY KEY,
    Name        VARCHAR(255) NOT NULL UNIQUE,
    Address     VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE Book (
    ISBN            VARCHAR(20) PRIMARY KEY,
    Quantity        INT NOT NULL CHECK (Quantity >= 0),
    Title           VARCHAR(255) NOT NULL,
    PublishDate     DATE,
    Pages           INT,
    Edition         VARCHAR(100),
    Genre           VARCHAR(100),
    pId             INT,
    FOREIGN KEY (pId) REFERENCES Publisher(Id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
) ENGINE=InnoDB;

-- Physical copy of a book
CREATE TABLE PhysicalBook (
    ISBN        VARCHAR(20) PRIMARY KEY,
    CoverType   VARCHAR(50),
    FOREIGN KEY (ISBN) REFERENCES Book(ISBN)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB;

-- Digital copy of a book
CREATE TABLE DigitalBook (
    ISBN        VARCHAR(20) PRIMARY KEY,
    FileType    VARCHAR(50),
    FileSizeMB  DECIMAL(10,2),
    FOREIGN KEY (ISBN) REFERENCES Book(ISBN)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB;

------------------------------------------------------------
-- SERIES / VOLUMES
------------------------------------------------------------

CREATE TABLE Series (
    Id      INT AUTO_INCREMENT PRIMARY KEY,
    Name    VARCHAR(255) UNIQUE NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Volume (
    ISBN        VARCHAR(20) PRIMARY KEY,
    SeriesId    INT NOT NULL,
    VolNum      INT NOT NULL,
    FOREIGN KEY (ISBN) REFERENCES Book(ISBN)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (SeriesId) REFERENCES Series(Id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB;

------------------------------------------------------------
-- ACCOUNTS (APP, EMPLOYEE, MEMBER)
------------------------------------------------------------

CREATE TABLE AppAccount (
    Username    VARCHAR(50) PRIMARY KEY,
    Password    VARBINARY(256) NOT NULL,
    Salt        VARBINARY(256) NOT NULL,
    Email       VARCHAR(255) UNIQUE NOT NULL,
    PhoneNum    VARCHAR(50),
    FirstName   VARCHAR(100) NOT NULL,
    LastName    VARCHAR(100) NOT NULL,
    Address     VARCHAR(255),
    Role        VARCHAR(20) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE EmployeeAcc (
    Username    VARCHAR(50) PRIMARY KEY,
    StaffId     INT UNIQUE NOT NULL,
    JobTitle    VARCHAR(100),
    FOREIGN KEY (Username) REFERENCES AppAccount(Username)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE MemberAcc (
    Username    VARCHAR(50) PRIMARY KEY,
    CardNum     INT UNIQUE NOT NULL,
    DisplayName VARCHAR(100),
    FOREIGN KEY (Username) REFERENCES AppAccount(Username)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB;

------------------------------------------------------------
-- AUTHORS & BOOK–AUTHOR RELATION
------------------------------------------------------------

CREATE TABLE Author (
    Id          INT AUTO_INCREMENT PRIMARY KEY,
    FirstName   VARCHAR(100) NOT NULL,
    LastName    VARCHAR(100) NOT NULL,
    Email       VARCHAR(255) UNIQUE
) ENGINE=InnoDB;

CREATE TABLE BookAuthor (
    ISBN        VARCHAR(20),
    aId         INT,
    AuthorNth   INT NOT NULL,
    PRIMARY KEY (ISBN, aId),
    FOREIGN KEY (ISBN) REFERENCES Book(ISBN)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (aId) REFERENCES Author(Id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB;

------------------------------------------------------------
-- CHECKOUTS & WAITLIST
------------------------------------------------------------

CREATE TABLE CheckOut (
    OrderId         INT AUTO_INCREMENT PRIMARY KEY,
    Username        VARCHAR(50) NOT NULL,
    ISBN            VARCHAR(20) NOT NULL,
    -- DATETIME for richer timestamp data,
    -- but still relies on ReturnDate IS NULL for "active" checkouts
    DateCheckedOut  DATETIME NOT NULL,
    ReturnDate      DATETIME DEFAULT NULL,
    FOREIGN KEY (Username) REFERENCES AppAccount(Username)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (ISBN) REFERENCES Book(ISBN)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB;

-- Improves checkout availability queries (current & view-compatible)
CREATE INDEX idx_checkout_active 
    ON CheckOut(ISBN, ReturnDate);

CREATE TABLE WaitList (
    Username        VARCHAR(50) NOT NULL,
    ISBN            VARCHAR(20) NOT NULL,
    DatePlaced      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (Username, ISBN),
    FOREIGN KEY (Username) REFERENCES AppAccount(Username)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (ISBN) REFERENCES Book(ISBN)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB;

-- Helpful for querying FIFO waitlist order
CREATE INDEX idx_waitlist_order
    ON WaitList(ISBN, DatePlaced);
