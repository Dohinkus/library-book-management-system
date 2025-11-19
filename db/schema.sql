# Notes on design changes/decisions from Erik diff from the ERD/Schema in discord:
  
# Availability removed (derived)
# Books can be physical, digital, or both
# Books can belong to series regardless of format
# Author emails unique
# EmployeeAcc spelling fixed
# Roles stored as VARCHAR
# Publisher name uniqueness enforced
# Waitlist implementation chosen based on simpler option:
  # Removed WaitlistPosition entirely, Ordering is determined by DatePlaced,
  # MUCH simpler to implement and explain, Meets requirements)

# Additional note:
  # All notes sections are to be REMOVED before project submittal


#Book and format tables

Book(
    ISBN            VARCHAR(20) PRIMARY KEY,
    Quantity        INT NOT NULL CHECK(Quantity >= 0),
    Title           VARCHAR(255) NOT NULL,
    PublishDate     DATE,
    Pages           INT,
    Edition         VARCHAR(50),
    Genre           VARCHAR(100),
    pId             INT,
    FOREIGN KEY (pId) REFERENCES Publisher(Id)
)

PhysicalBook(
    ISBN        VARCHAR(20) PRIMARY KEY,
    CoverType   VARCHAR(50),
    FOREIGN KEY (ISBN) REFERENCES Book(ISBN)
)

DigitalBook(
    ISBN        VARCHAR(20) PRIMARY KEY,
    FileType    VARCHAR(50),
    FileSizeMB  DECIMAL(10,2),
    FOREIGN KEY (ISBN) REFERENCES Book(ISBN)
)

# Series tables
  
Series(
    Id      INT AUTO_INCREMENT PRIMARY KEY,
    Name    VARCHAR(255) UNIQUE NOT NULL
)

Volume(
    ISBN        VARCHAR(20) PRIMARY KEY,
    SeriesId    INT NOT NULL,
    VolNum      INT NOT NULL,
    FOREIGN KEY (ISBN) REFERENCES Book(ISBN),
    FOREIGN KEY (SeriesId) REFERENCES Series(Id)
)

# Accounts and User tables
  
AppAccount(
    Username    VARCHAR(50) PRIMARY KEY,
    Password    VARBINARY(256) NOT NULL,
    Salt        VARBINARY(256) NOT NULL,
    Email       VARCHAR(255) UNIQUE NOT NULL,
    PhoneNum    VARCHAR(50),
    FirstName   VARCHAR(100) NOT NULL,
    LastName    VARCHAR(100) NOT NULL,
    Address     VARCHAR(255),
    Role        VARCHAR(20) NOT NULL
)

EmployeeAcc(
    Username    VARCHAR(50) PRIMARY KEY,
    StaffId     INT UNIQUE NOT NULL,
    JobTitle    VARCHAR(100),
    FOREIGN KEY (Username) REFERENCES AppAccount(Username)
)

MemberAcc(
    Username    VARCHAR(50) PRIMARY KEY,
    CardNum     INT UNIQUE NOT NULL,
    DisplayName VARCHAR(100),
    FOREIGN KEY (Username) REFERENCES AppAccount(Username)
)

# Author relations

Author(
    Id          INT AUTO_INCREMENT PRIMARY KEY,
    FirstName   VARCHAR(100) NOT NULL,
    LastName    VARCHAR(100) NOT NULL,
    Email       VARCHAR(255) UNIQUE
)

BookAuthor(
    ISBN        VARCHAR(20),
    aId         INT,
    AuthorNth   INT NOT NULL,
    PRIMARY KEY (ISBN, aId),
    FOREIGN KEY (ISBN) REFERENCES Book(ISBN),
    FOREIGN KEY (aId) REFERENCES Author(Id)
)

# Publisher

Publisher(
    Id      INT AUTO_INCREMENT PRIMARY KEY,
    Name    VARCHAR(255) UNIQUE,
    Address VARCHAR(255)
)

# CheckOut system

CheckOut(
    OrderId         INT AUTO_INCREMENT PRIMARY KEY,
    Username        VARCHAR(50) NOT NULL,
    ISBN            VARCHAR(20) NOT NULL,
    DateCheckedOut  DATE NOT NULL,
    ReturnDate      DATE,
    FOREIGN KEY (Username) REFERENCES AppAccount(Username),
    FOREIGN KEY (ISBN) REFERENCES Book(ISBN)
)

# WaitList

WaitList(
    Username        VARCHAR(50) NOT NULL,
    ISBN            VARCHAR(20) NOT NULL,
    DatePlaced      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (Username, ISBN),
    FOREIGN KEY (Username) REFERENCES AppAccount(Username),
    FOREIGN KEY (ISBN) REFERENCES Book(ISBN)
)
