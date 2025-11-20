CREATE DATABASE IF NOT EXISTS LBMS
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_general_ci;

USE LBMS;

CREATE TABLE Publisher (
    Id          INT AUTO_INCREMENT PRIMARY KEY,
    Name        VARCHAR(255) UNIQUE,
    Address     VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE Book (
    ISBN            VARCHAR(20) PRIMARY KEY,
    Quantity        INT NOT NULL CHECK (Quantity >= 0),
    Title           VARCHAR(255) NOT NULL,
    PublishDate     DATE,
    Pages           INT,
    Edition         VARCHAR(50),
    Genre           VARCHAR(100),
    pId             INT,
    FOREIGN KEY (pId) REFERENCES Publisher(Id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE TABLE PhysicalBook (
    ISBN        VARCHAR(20) PRIMARY KEY,
    CoverType   VARCHAR(50),
    FOREIGN KEY (ISBN) REFERENCES Book(ISBN)
        ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE DigitalBook (
    ISBN        VARCHAR(20) PRIMARY KEY,
    FileType    VARCHAR(50),
    FileSizeMB  DECIMAL(10,2),
    FOREIGN KEY (ISBN) REFERENCES Book(ISBN)
        ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE Series (
    Id      INT AUTO_INCREMENT PRIMARY KEY,
    Name    VARCHAR(255) UNIQUE NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Volume (
    ISBN        VARCHAR(20) PRIMARY KEY,
    SeriesId    INT NOT NULL,
    VolNum      INT NOT NULL,
    FOREIGN KEY (ISBN) REFERENCES Book(ISBN)
        ON DELETE CASCADE,
    FOREIGN KEY (SeriesId) REFERENCES Series(Id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

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
        ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE MemberAcc (
    Username    VARCHAR(50) PRIMARY KEY,
    CardNum     INT UNIQUE NOT NULL,
    DisplayName VARCHAR(100),
    FOREIGN KEY (Username) REFERENCES AppAccount(Username)
        ON DELETE CASCADE
) ENGINE=InnoDB;

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
        ON DELETE CASCADE,
    FOREIGN KEY (aId) REFERENCES Author(Id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE CheckOut (
    OrderId         INT AUTO_INCREMENT PRIMARY KEY,
    Username        VARCHAR(50) NOT NULL,
    ISBN            VARCHAR(20) NOT NULL,
    DateCheckedOut  DATE NOT NULL,
    ReturnDate      DATE,
    FOREIGN KEY (Username) REFERENCES AppAccount(Username)
        ON DELETE CASCADE,
    FOREIGN KEY (ISBN) REFERENCES Book(ISBN)
        ON DELETE CASCADE
) ENGINE=InnoDB;

-- Improves checkout availability queries
CREATE INDEX idx_checkout_active 
    ON CheckOut(ISBN, ReturnDate);

CREATE TABLE WaitList (
    Username        VARCHAR(50) NOT NULL,
    ISBN            VARCHAR(20) NOT NULL,
    DatePlaced      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (Username, ISBN),
    FOREIGN KEY (Username) REFERENCES AppAccount(Username)
        ON DELETE CASCADE,
    FOREIGN KEY (ISBN) REFERENCES Book(ISBN)
        ON DELETE CASCADE
) ENGINE=InnoDB;

-- Helpful for querying FIFO waitlist order
CREATE INDEX idx_waitlist_order
    ON WaitList(ISBN, DatePlaced);
