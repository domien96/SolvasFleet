ALTER TABLE companies RENAME COLUMN id TO company_id;
ALTER TABLE roles RENAME COLUMN id TO role_id;
ALTER TABLE users RENAME COLUMN id TO user_id;
ALTER TABLE vehicles RENAME COLUMN id TO vehicle_id;

drop table if exists fleets cascade;
drop table if exists permissions cascade;
drop type if exists vehicletype cascade;
drop table if exists vehicle_types cascade;
drop table if exists fleet_subscriptions cascade;

-- Weak entity: a fleet always belongs to one company.
CREATE TABLE fleets (
  fleet_id  serial NOT NULL ,
  company_id int NOT NULL ,
  name varchar(255),
  updated_at timestamp,
  created_at timestamp,
  url varchar(255),
  PRIMARY KEY (fleet_id),
  FOREIGN KEY(company_id) REFERENCES companies(company_id)
);

CREATE TABLE permissions (
  permission_id  serial not null,
  name varchar(255) NOT NULL,
  updated_at timestamp,
  created_at timestamp,
  PRIMARY KEY (permission_id)
);

CREATE TYPE vehicletype AS ENUM('car','truck');

CREATE TABLE vehicle_types (
  vehicle_type_id  serial not null,
  type vehicletype NOT NULL ,
  updated_at timestamp,
  created_at timestamp,
  PRIMARY KEY (vehicle_type_id)
);

CREATE TABLE fleet_subscriptions (
  fleet_subscription_id  serial not null,
  start DATE NOT NULL,
  "end" DATE NOT NULL,
  updated_at timestamp,
  created_at timestamp,
  PRIMARY KEY (fleet_subscription_id)
);