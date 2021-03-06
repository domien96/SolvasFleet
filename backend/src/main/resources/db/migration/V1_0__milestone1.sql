
-- Drop tables if they exist --

DROP TABLE IF EXISTS companies CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS vehicles CASCADE;
DROP TABLE IF EXISTS fleets CASCADE;
DROP TABLE IF EXISTS permissions CASCADE;
DROP TABLE IF EXISTS subfleets CASCADE;
DROP TABLE IF EXISTS vehicle_types CASCADE;
DROP TABLE IF EXISTS fleet_subscriptions CASCADE;

-- Create tables --

CREATE TABLE companies (
  company_id SERIAL NOT NULL,
  name VARCHAR(255) NOT NULL,
  vat VARCHAR(255) NOT NULL,
  phone VARCHAR(255) NOT NULL,
  address_city VARCHAR(255) NOT NULL,
  address_country VARCHAR(255) NOT NULL,
  address_house_number VARCHAR(255) NOT NULL,
  address_postalCode VARCHAR(255) NOT NULL,
  address_street VARCHAR(255) NOT NULL,
  updated_at TIMESTAMP,
  created_at TIMESTAMP,
  PRIMARY KEY (company_id)
);

CREATE TABLE users (
  user_id SERIAL NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  updated_at TIMESTAMP,
  created_at TIMESTAMP,
  PRIMARY KEY (user_id)
);

CREATE TABLE vehicle_types (
  vehicletype_id SERIAL NOT NULL,
  name varchar(255) NOT NULL,
  PRIMARY KEY (vehicletype_id)
);

CREATE TABLE vehicles (
  vehicle_id SERIAL NOT NULL,
  license_plate VARCHAR(255),
  chassis_number VARCHAR(255) NOT NULL,
  model VARCHAR(255) NOT NULL,
  kilometer_count INT NOT NULL,
  year INT NOT NULL,
  leasing_company_id INT REFERENCES companies(company_id),
  value INT,
  brand VARCHAR(255) NOT NULL,
  vehicletype_id INT NOT NULL REFERENCES vehicle_types(vehicletype_id),
  updated_at TIMESTAMP,
  created_at TIMESTAMP,
  PRIMARY KEY (vehicle_id)
);

CREATE TABLE roles (
  role_id SERIAL NOT NULL,
  function VARCHAR(255) NOT NULL, --Todo change to function?
  company_id INT NOT NULL REFERENCES companies(company_id),
  user_id INT NOT NULL REFERENCES users(user_id), -- ToDo add endate, startDate
  updated_at TIMESTAMP,
  created_at TIMESTAMP,
  PRIMARY KEY (role_id)
);

CREATE TABLE fleets (
  fleet_id SERIAL NOT NULL,
  company_id INT NOT NULL REFERENCES companies(company_id),
  name VARCHAR(255),
  updated_at TIMESTAMP,
  created_at TIMESTAMP,
  PRIMARY KEY (fleet_id)
);

CREATE TABLE permissions (
  permission_id SERIAL NOT NULL,
  name VARCHAR(255) NOT NULL,
  updated_at TIMESTAMP,
  created_at TIMESTAMP,
  PRIMARY KEY (permission_id)
);

CREATE TABLE subfleets (
  subfleet_id SERIAL NOT NULL,
  fleet_id INT NOT NULL REFERENCES fleets(fleet_id),
  vehicletype_id INT NOT NULL REFERENCES vehicle_types(vehicletype_id),
  updated_at TIMESTAMP,
  created_at TIMESTAMP,
  PRIMARY KEY (subfleet_id)
);

CREATE TABLE fleet_subscriptions (
  fleet_subscription_id SERIAL NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE,
  vehicle_id INT NOT NULL REFERENCES vehicles(vehicle_id),
  subfleet_id INT NOT NULL REFERENCES subfleets(subfleet_id),
  updated_at TIMESTAMP,
  created_at TIMESTAMP,
  PRIMARY KEY (fleet_subscription_id)
);


