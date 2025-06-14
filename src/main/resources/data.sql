--borrower
INSERT INTO borrower (borrower_id, borrower_email, borrower_pass, borrower_phone_no, borrower_username)
VALUES
    (2, 'ainzafrany@gmail.com', 'ain123', '0176487529', 'Ain Zafrany'),
    (3, 'angel@gmail.com', 'angel987', '01882324', 'Angel'),
    (4, 'zul@gmail.com', 'zul000', '0122387589', 'zul');

--book
insert into book (book_isbn, book_title, book_author)
values ('978-3-16-148410-0', 'Harry Potter', 'JK Rowling');
insert into book (book_isbn, book_title, book_author)
values ('978-3-16-156710-0', 'REM', 'John Doe');
insert into book (book_isbn, book_title, book_author)
values ('978-3-16-140920-0', 'Flowers', 'Anne Belle');

--borrow
-- Ain borrowed "Harry Potter" 5 days ago, not yet returned
INSERT INTO borrow ( borrower_id, book_isbn, borrow_date, return_date)
VALUES ( 2, '978-3-16-148410-0', DATE '2025-06-06', NULL);

-- Angel borrowed "REM" 10 days ago, returned yesterday
INSERT INTO borrow (borrower_id, book_isbn, borrow_date, return_date)
VALUES ( 3, '978-3-16-156710-0', DATE '2025-06-01', DATE '2025-06-10');

-- Zul borrowed "Flowers" 15 days ago, still not returned (Overdue)
INSERT INTO borrow ( borrower_id, book_isbn, borrow_date, return_date)
VALUES (4, '978-3-16-140920-0', DATE '2025-05-27', NULL);