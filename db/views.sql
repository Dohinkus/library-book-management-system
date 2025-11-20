CREATE OR REPLACE VIEW BookAvailability AS
SELECT 
    b.ISBN,
    b.Title,
    b.Quantity,
    (b.Quantity - COUNT(c.ISBN)) AS AvailableCopies
FROM Book b
LEFT JOIN CheckOut c 
    ON b.ISBN = c.ISBN AND c.ReturnDate IS NULL
GROUP BY b.ISBN;

CREATE OR REPLACE VIEW ActiveCheckouts AS
SELECT *
FROM CheckOut
WHERE ReturnDate IS NULL;
