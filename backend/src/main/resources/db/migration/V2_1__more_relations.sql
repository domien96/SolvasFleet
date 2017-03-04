ALTER TABLE companies RENAME COLUMN id TO company_id;
ALTER TABLE roles RENAME COLUMN id TO role_id;
ALTER TABLE users RENAME COLUMN id TO user_id;
ALTER TABLE vehicles RENAME COLUMN id TO vehicle_id;

DROP TABLE IF EXISTS fleets CASCADE;
DROP TABLE IF EXISTS permissions CASCADE;
DROP TABLE IF EXISTS vehicletype CASCADE;
DROP TABLE IF EXISTS vehicle_types CASCADE;
DROP TABLE IF EXISTS fleet_subscriptions CASCADE;

-- Weak entity: a fleet always belongs to one company.
CREATE TABLE fleets (
  fleet_id SERIAL NOT NULL,
  company_id INT NOT NULL,
  name VARCHAR(255),
  updated_at TIMESTAMP,
  created_at TIMESTAMP,
  PRIMARY KEY (fleet_id),
  FOREIGN KEY(company_id) REFERENCES companies(company_id)
);

CREATE TABLE permissions (
  permission_id SERIAL NOT NULL,
  name VARCHAR(255) NOT NULL,
  updated_at TIMESTAMP,
  created_at TIMESTAMP,
  PRIMARY KEY (permission_id)
);

CREATE TABLE vehicletypes (
  vehicletype_id SERIAL NOT NULL,
  name varchar(30) NOT NULL ,
  updated_at TIMESTAMP,
  created_at TIMESTAMP,
  PRIMARY KEY (vehicletype_id)
);

CREATE TABLE fleet_subscriptions (
  fleet_subscription_id SERIAL NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  updated_at TIMESTAMP,
  created_at TIMESTAMP,
  PRIMARY KEY (fleet_subscription_id)
);