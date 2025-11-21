drop database LibraryManagementSystem;

create database LibraryManagementSystem;

use LibraryManagementSystem;

##########    Stand Alone Relations    ##########

#Added 'auto_increment' after seeing it in Erik's DB
create table Author(
Id int auto_increment primary key,
FirstName varchar(50) not null,
LastName varchar(50) not null,
Email varchar(255) unique
);

create table Publisher(
Id int auto_increment primary key,
Name varchar(255) not null unique,
Address varchar(255)
);

create table Series(
SeriesId int auto_increment primary key,
SeriesName varchar(100) not null unique
);

##########    Book Hierarchy    ##########

#Availability attribute removed after viewing Erik's DB and seeing a way you can check availability
create table Book(
ISBN varchar(20) primary key,
Quantity int not null,
Title varchar(255) not null,
PublishDate date,
Pages int,
Edition varchar(100),
Genre varchar(100),
pId int,
foreign key (pId) references Publisher(Id) on delete set null on update cascade
);

#ISBN is varchar in case it has the letter 'X' to represent the number 10 at the final position
#enum rule so the 'covertype' attribute only has two options
create table Physical(
ISBN varchar(20) primary key,
Covertype enum('Hard Cover', 'Paper Cover') not null,
foreign key (ISBN) references Book(ISBN) on delete cascade on update cascade
);

#Filesize data type changed to match Erik's version
create table Digital(
ISBN varchar(20) primary key,
Filetype varchar(30) not null,
FilesizeMB decimal(10,2) not null,
foreign key (ISBN) references Book(ISBN) on delete cascade on update cascade
);

#Volume is int, 1 means first volume, 2 is second volume, etc.
create table Volume(
ISBN varchar(20) primary key,
SeriesId int not null,
VolNum int not null,
foreign key (ISBN) references Book(ISBN) on delete cascade on update cascade,
foreign key (SeriesId) references Series(SeriesId) on delete cascade on update cascade
);

##########    Account Hierarchy    ##########

#Changed password data type and added salt after researching the purpose it has for the DB
#Enum rule so there's a limit to what role you can be (can always add more)
create table AppAccount(
Username varchar(50) primary key,
Password varbinary(256) not null,
Salt varbinary(256) not null,
Email varchar(100) unique not null,
PhoneNum varchar(30), 
FirstName varchar(50) not null, 
LastName varchar(50) not null, 
Address varchar(255), 
Role enum('Admin', 'Librarian', 'Assistant Librarian', 'Manager', 'Janitor', 'Computer Technician', 'Member') not null
);

create table EmployeeAcc(
Username varchar(50) unique not null,
StaffId int auto_increment primary key,
JobTitle varchar(100) not null,
foreign key (Username) references AppAccount(Username) on delete cascade on update cascade
);

create table MemberAcc(
Username varchar(50) unique not null,
CardNum int auto_increment primary key,
Displayname varchar(50),
foreign key (Username) references AppAccount(Username) on delete cascade on update cascade
);

##########    "Weak" Relations    ##########

create table CheckOut(
Username varchar(50) not null,
ISBN varchar(20) not null,
OrderId int auto_increment primary key,
DateCheckedOut datetime not null default current_timestamp(),
ReturnDate datetime default current_timestamp(),
foreign key (Username) references AppAccount(Username) on delete cascade on update cascade,
foreign key (ISBN) references Book(ISBN) on delete cascade on update cascade
);

#Changed DatePlaced data type to match Erik's
#Already removed WaitlistPosition attribute before Erik's version, but they match well
create table WaitList(
Username varchar(50) not null,
ISBN varchar(20) not null,
DatePlaced datetime not null default current_timestamp(),
foreign key (Username) references AppAccount(Username) on delete cascade on update cascade,
foreign key (ISBN) references Book(ISBN) on delete cascade on update cascade,
primary key (Username, ISBN)
);

#AuthorNth is int, 1 means single author or first author, 2 is second author, etc.
create table BookAuthor(
ISBN varchar(20) not null,
aId int not null,
AuthorNth int not null,
foreign key (ISBN) references Book(ISBN) on delete cascade on update cascade,
foreign key (aId) references Author(Id) on delete cascade on update cascade,
primary key (ISBN, aId)
);


#######################################################################################

#Inserting information

Insert into appaccount values
('FunkyMonk', HEX('Blue'), HEX('some_random_salt_1'), 'funkymonk@example.com', '111-111-1111', 'Eve', 'Car', '1234 Road rd, City1, State 70000', 'Admin'),
('MintoMan', HEX('Pink'), HEX('some_random_salt_2'), 'mintoman@example.com', '222-222-2222', 'John', 'Meow', '1234 Street st, City2, State 70001', 'Member');

Insert into publisher values
(1, 'Knopf Doubleday Publishing Group', '1745 Broadway, New York City, U.S.'),
(2, 'Digital Fire', ''),
(3, 'Random House Worlds', 'Random House Tower, 1745 Broadway, New York City
, U.S.');

Insert into series values
(1, 'The Handmaid''s Tale'),
(2, 'Founders Trilogy');

Insert into author values
(1, 'Margarat', 'Atwood', 'matwood@example.com'),
(2, 'Sun', 'Tzu', ''),
(3, 'Robert', 'Bennett', 'rbennett@example.com');

Insert into book values
('9780099740919', 2, 'The Handmaid''s Tale', '2019-08-01', 320, 'Vintage Books Edition', 'Classics', 1),
('9789388760560', 1, 'The Art of War', '2019-03-29', 114, 'Kindle Edition', 'Military Strategy', 2),
('9781524760373', 1, 'Foundryside', '2018-08-21', 512, 'Kindle Edition', 'Fantasy', 3);

Insert into digital values
('9789388760560', 'AZW', 1.7),
('9781524760373', 'eBook', 2);

Insert into physical values
('9780099740919', 'Paper Cover');

Insert into volume values
('9780099740919', 1, 1),
('9781524760373', 2, 1);

Insert into employeeacc values
('FunkyMonk', '1', 'Database Loser');

Insert into memberacc values
('MintoMan', 1, 'JohnMan');

Insert into bookauthor values
('9780099740919', 1, 1);

Insert into waitlist values
();

Insert into checkout values
();


#######################################################################################

#Viewing tables

#View digital books
select b.ISBN, Title, PublishDate, Pages, Edition, Genre, Filetype, Filesize
from book as b join digital as d on b.ISBN=d.ISBN;

#View physical books
select b.ISBN, Title, PublishDate, Pages, Edition, Genre, Covertype
from book as b join physical as p on b.ISBN=p.ISBN;

#View books that are volumes
select b.ISBN, b.Title, b.PublishDate, b.Pages, b.Edition, b.Genre, v.VolNum, s.seriesName
from book as b join volume as v on b.ISBN=v.ISBN 
join series as s on s.SeriesId=v.SeriesId;

#View physical books that are part of a series
select b.ISBN, Title, PublishDate, Pages, Edition, Genre, Covertype, VolNum, s.SeriesName
from book as b join volume as v on b.ISBN=v.ISBN
join physical as p on v.ISBN=p.ISBN 
join series as s on s.SeriesId=v.SeriesId;

#View digital books that are part of a series
select b.ISBN, Title, PublishDate, Pages, Edition, Genre, Filetype, Filesize, s.SeriesName
from book as b join volume as v on b.ISBN=v.ISBN 
join digital as d on v.ISBN=d.ISBN
join series as s on s.seriesid=v.seriesid;

#View all accounts
select firstname, lastname, email, phonenum, address, role, username, password, salt
from appaccount;

#View full employee accounts (including the regular account information)
select firstname, lastname, email, phonenum, address, role, aa.username, staffid, jobtitle
from appaccount as aa join employeeacc as ea on aa.Username=ea.Username;

#View full member accounts (including the regular account information)
select firstname, lastname, email, phonenum, address, role, aa.username, cardnum, displayname
from appaccount as aa join memberacc as ma on aa.Username=ma.Username;

#View if there is availability for a book
select b.Title, b.Quantity, coalesce(co.CurrentlyCheckedOut, 0) as CurrentlyCheckedOut, (b.Quantity - coalesce(co.CurrentlyCheckedOut, 0)) as AvailableCopies
from book as b left join (
	select ISBN, count(case when ReturnDate is null then 1 end) as CurrentlyCheckedOut
    from CheckOut
    group by ISBN) as co on b.ISBN = co.ISBN;