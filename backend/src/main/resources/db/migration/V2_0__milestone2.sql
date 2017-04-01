-- Drop tables if they exist --

DROP TABLE IF EXISTS contracts CASCADE;
DROP TABLE IF EXISTS insurances CASCADE;
DROP TABLE IF EXISTS insurance_types CASCADE;

-- Create tables --

CREATE TABLE insurance_types (
  insurance_type_id SERIAL NOT NULL,
  standard_fixed_rate BIGINT NOT NULL,
  standard_tax BIGINT NOT NULL,
  standard_commission BIGINT NOT NULL,
  name VARCHAR (255) NOT NULL,
  archived BOOLEAN DEFAULT FALSE,
  updated_at TIMESTAMP,
  created_at TIMESTAMP,
  PRIMARY KEY (insurance_type_id)
);



CREATE TABLE contracts (
  contract_id SERIAL NOT NULL,
  fleet_subscription_id INT NOT NULL REFERENCES fleet_subscriptions(fleet_subscription_id),
  company_id INT REFERENCES companies(company_id),
  insurance_type CHAR(255) NOT NULL,
  premium BIGINT NOT NULL,
  franchise BIGINT NOT NULL,
  beginDate DATE NOT NULL,
  endDate DATE NOT NULL,
  archived BOOLEAN DEFAULT FALSE,
  updated_at TIMESTAMP,
  created_at TIMESTAMP,
  PRIMARY KEY (contract_id)

);
