DROP TABLE IF EXISTS companies CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS vehicles CASCADE;

CREATE TABLE companies (
  company_id  SERIAL NOT NULL,
  name VARCHAR(255),
  vat VARCHAR(255),
  phone VARCHAR(255),
  address VARCHAR(255),
  updated_at TIMESTAMP,
  created_at TIMESTAMP,
  PRIMARY KEY (company_id));

CREATE TABLE users (
  user_id  SERIAL NOT NULL,
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  email VARCHAR(255),
  password VARCHAR(255),
  updated_at TIMESTAMP,
  created_at TIMESTAMP,
  PRIMARY KEY (user_id));

CREATE TABLE vehicles (
  vehicle_id  SERIAL NOT NULL,
  license_plate VARCHAR(255),
  chassis_number VARCHAR(255),
  model VARCHAR(255),
  type VARCHAR(255),
  kilometer_count INT,
  year INT,
  leasing_company_id INT REFERENCES companies(company_id),
  vat INT,
  company_id INT REFERENCES companies(company_id),
  updated_at TIMESTAMP,
  created_at TIMESTAMP,
  PRIMARY KEY (vehicle_id));

CREATE TABLE roles (
  role_id  SERIAL NOT NULL,
  function VARCHAR(255),
  company_id INT REFERENCES companies(company_id),
  user_id INT REFERENCES users(user_id),
  updated_at TIMESTAMP,
  created_at TIMESTAMP,
  PRIMARY KEY (role_id));