CREATE TABLE IF NOT EXISTS book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    publication_year INT,
    isbn VARCHAR(20) UNIQUE
);

CREATE TABLE IF NOT EXISTS patron (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    contact_information VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS borrowing_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    borrow_date DATE,
    return_date DATE,
    book_id BIGINT,
    patron_id BIGINT,
    FOREIGN KEY (book_id) REFERENCES book(id),
    FOREIGN KEY (patron_id) REFERENCES patron(id)
);
