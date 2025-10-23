-- Run this in MySQL Workbench or phpMyAdmin
CREATE DATABASE hospital_db;
USE hospital_db;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('admin', 'receptionist') NOT NULL
);

CREATE TABLE doctors (
    doc_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100),
    phone VARCHAR(15)
);

CREATE TABLE patients (
    pat_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT,
    gender VARCHAR(10),
    phone VARCHAR(15),
    address TEXT
);

-- Default login: admin / admin123
INSERT INTO users (username, password, role) 
VALUES ('admin', 'admin123', 'admin');