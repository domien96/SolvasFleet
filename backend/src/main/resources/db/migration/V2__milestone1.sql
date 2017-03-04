drop table if exists companies cascade;
drop table if exists roles cascade;
drop table if exists users cascade;
drop table if exists vehicles cascade;

create table companies (
  id  serial not null,
  name varchar(255),
  vat varchar(255),
  phone varchar(255),
  address varchar(255),
  updated_at timestamp,
  created_at timestamp,
  url varchar(255),
  primary key (id));

create table users (
  id  serial not null,
  first_name varchar(255),
  last_name varchar(255),
  email varchar(255),
  password varchar(255),
  updated_at timestamp,
  created_at timestamp,
  url varchar(255),
  primary key (id));

create table vehicles (
  id  serial not null,
  license_plate varchar(255),
  chassis_number varchar(255),
  model varchar(255),
  type varchar(255),
  kilometer_count int4,
  year int4,
  leasing_company_id int4 REFERENCES companies(id),
  vat int4,
  company_id int4 REFERENCES companies(id),
  updated_at timestamp,
  created_at timestamp,
  url varchar(255),
  primary key (id));

create table roles (
  id  serial not null,
  function varchar(255),
  company_id int4 REFERENCES companies(id),
  user_id int4 REFERENCES users(id),
  updated_at timestamp,
  created_at timestamp,
  url varchar(255),
  primary key (id));