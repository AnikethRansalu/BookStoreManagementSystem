-- BOOKSTORE INVENTORY SYSTEM DATABASE
-- Create the a connection in mesql workbrench and name it as "bookstore" and in the DBConnection.java update you USER & PASS for your own credentials as per your mysql

DROP DATABASE IF EXISTS bookstore;
CREATE DATABASE bookstore;
USE bookstore;

-- USERS TABLE

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'admin'
);

INSERT INTO users (username, password, role)
VALUES ('admin', 'admin', 'admin');

-- CATEGORY TABLE

CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT
);

-- SUPPLIERS TABLE

CREATE TABLE suppliers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    contact VARCHAR(100),
    address TEXT
);

-- BOOKS TABLE

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    author VARCHAR(100),
    price DOUBLE NOT NULL,
    quantity INT DEFAULT 0,
    category_id INT,
    supplier_id INT,

    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE SET NULL
);

-- SAMPLE DATA (OPTIONAL)

INSERT INTO categories (name, description)
VALUES 
('Fiction', 'Story books'),
('Education', 'School & University'),
('Science', 'Science & Research');

INSERT INTO suppliers (name, contact, address)
VALUES
('ABC Publishers', '0771234567', 'Colombo'),
('XYZ Distributors', '0719876543', 'Kandy');

INSERT INTO books (title, author, price, quantity, category_id, supplier_id)
VALUES
('Book A', 'Author 1', 1200.00, 80, 1, 1),
('Book B', 'Author 2', 900.00, 5, 2, 2),
('Book C', 'Author 3', 1500.00, 3, 3, 1);