-- Create the database
CREATE DATABASE csis231_bookstore;

-- Connect to the new database (you'll need to do this manually in pgAdmin)
-- Then run the following:

-- Create the book table
CREATE TABLE book (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    "year" INTEGER,
    price DOUBLE PRECISION,
    isbn VARCHAR(255)
);

-- Create the customer table
CREATE TABLE customer (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE
);

-- Insert sample books
INSERT INTO book (title, author, "year", price, isbn) VALUES 
('The Great Gatsby', 'F. Scott Fitzgerald', 1925, 12.99, '978-0-7432-7356-5'),
('To Kill a Mockingbird', 'Harper Lee', 1960, 14.99, '978-0-06-112008-4'),
('1984', 'George Orwell', 1949, 13.99, '978-0-452-28423-4'),
('Pride and Prejudice', 'Jane Austen', 1813, 11.99, '978-0-14-143951-8'),
('The Catcher in the Rye', 'J.D. Salinger', 1951, 15.99, '978-0-316-76948-0');

-- Insert sample customers
INSERT INTO customer (name, email) VALUES
('John Doe', 'john.doe@email.com'),
('Jane Smith', 'jane.smith@email.com'),
('Bob Johnson', 'bob.johnson@email.com'),
('Alice Brown', 'alice.brown@email.com'),
('Charlie Wilson', 'charlie.wilson@email.com');