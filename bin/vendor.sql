CREATE DATABASE IF NOT EXISTS VendorConnectDB;

USE VendorConnectDB;

-- Create Vendors Table
CREATE TABLE IF NOT EXISTS Vendors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    address TEXT,
    phone VARCHAR(20)
);

-- Create Users Table
CREATE TABLE IF NOT EXISTS Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, 
    firstName VARCHAR(255),
    lastName VARCHAR(255)
);

-- Insert sample users (replace with actual hashed passwords)
INSERT IGNORE INTO Users (email, password, firstName, lastName) 
VALUES
    ('admin@example.com', 'admin123', 'Admin', 'User'), 
    ('user1@example.com', 'userone', 'User', 'One'); 

-- Insert sample vendors 
INSERT INTO Vendors (name, description, address, phone)
VALUES
    ('Acme Bakery', 'Delicious pastries and breads', '123 Main St', '555-1234'),
    ('Bob\'s Burgers', 'Burgers, fries, and shakes', '456 Oak Ave', '555-5678'),
    ('Cozy Cafe', 'Coffee, tea, and light snacks', '789 Elm St', '555-9012'),
    ('Tech Solutions', 'Computer repair and IT services', '101 Tech Way', '555-4321'),
    ('Green Grocer', 'Fresh fruits and vegetables', '202 Market St', '555-7890');
    
