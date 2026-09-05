-- Dental Clinic Management System Database Schema

-- Create database
CREATE DATABASE IF NOT EXISTS dental_clinic_db;
USE dental_clinic_db;

-- Admins table
CREATE TABLE IF NOT EXISTS admins (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100),
    email VARCHAR(100)
);

-- Receptionists table
CREATE TABLE IF NOT EXISTS receptionists (
    receptionist_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100),
    contact_number VARCHAR(20),
    email VARCHAR(100)
);

-- Users table (backward compatibility fallback)
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Patients table
CREATE TABLE IF NOT EXISTS patients (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_name VARCHAR(100) NOT NULL,
    age INT,
    gender VARCHAR(20),
    contact_number VARCHAR(20),
    email VARCHAR(100),
    address VARCHAR(255)
);

-- Dentists table
CREATE TABLE IF NOT EXISTS dentists (
    dentist_id INT AUTO_INCREMENT PRIMARY KEY,
    dentist_name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100),
    contact_number VARCHAR(20),
    email VARCHAR(100)
);

-- Treatments table
CREATE TABLE IF NOT EXISTS treatments (
    treatment_id INT AUTO_INCREMENT PRIMARY KEY,
    treatment_name VARCHAR(100) NOT NULL
);

-- Appointments table
CREATE TABLE IF NOT EXISTS appointments (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    dentist_id INT,
    treatment_id INT,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (dentist_id) REFERENCES dentists(dentist_id),
    FOREIGN KEY (treatment_id) REFERENCES treatments(treatment_id)
);

-- Bills table
CREATE TABLE IF NOT EXISTS bills (
    bill_id INT AUTO_INCREMENT PRIMARY KEY,
    appointment_id INT,
    patient_id INT,
    amount DECIMAL(10, 2),
    bill_date DATE,
    status VARCHAR(50),
    FOREIGN KEY (appointment_id) REFERENCES appointments(appointment_id),
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id)
);

-- Insert sample data
INSERT INTO admins (username, password, name, email) VALUES 
('admin', 'admin123', 'System Administrator', 'admin@dentalclinic.com')
ON DUPLICATE KEY UPDATE username=username;

INSERT INTO receptionists (username, password, name, contact_number, email) VALUES 
('receptionist', 'recep123', 'Emily Davis', '555-0201', 'emily@dentalclinic.com')
ON DUPLICATE KEY UPDATE username=username;

INSERT INTO users (username, password, role) VALUES 
('admin', 'admin123', 'admin'),
('receptionist', 'recep123', 'receptionist')
ON DUPLICATE KEY UPDATE username=username;

INSERT INTO dentists (dentist_name, specialization, contact_number, email) VALUES 
('Dr. John Smith', 'Orthodontist', '555-0101', 'john.smith@clinic.com'),
('Dr. Sarah Johnson', 'General Dentist', '555-0102', 'sarah.johnson@clinic.com'),
('Dr. Michael Brown', 'Oral Surgeon', '555-0103', 'michael.brown@clinic.com');

INSERT INTO treatments (treatment_name) VALUES 
('Teeth Cleaning'),
('Root Canal'),
('Tooth Extraction'),
('Dental Implant'),
('Orthodontic Treatment'),
('Teeth Whitening'),
('Dental Crown'),
('Dental Bridge');
