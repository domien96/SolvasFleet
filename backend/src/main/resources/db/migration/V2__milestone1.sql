drop table if exists companies cascade;
drop table if exists roles cascade;
drop table if exists users cascade;
drop table if exists vehicles cascade;

create table companies (
  company_id  serial not null,
  name varchar(255),
  vat varchar(255),
  phone varchar(255),
  address varchar(255),
  updated_at timestamp,
  created_at timestamp,
  primary key (company_id));

create table users (
  user_id  serial not null,
  first_name varchar(255),
  last_name varchar(255),
  email varchar(255),
  password varchar(255),
  updated_at timestamp,
  created_at timestamp,
  primary key (user_id));

create table vehicles (
  vehicle_id  serial not null,
  license_plate varchar(255),
  chassis_number varchar(255),
  model varchar(255),
  type varchar(255),
  kilometer_count int4,
  year int4,
  leasing_company_id int4 REFERENCES companies(company_id),
  vat int4,
  company_id int4 REFERENCES companies(company_id),
  updated_at timestamp,
  created_at timestamp,
  primary key (vehicle_id));

create table roles (
  role_id  serial not null,
  function varchar(255),
  company_id int4 REFERENCES companies(company_id),
  user_id int4 REFERENCES users(user_id),
  updated_at timestamp,
  created_at timestamp,
  primary key (role_id));