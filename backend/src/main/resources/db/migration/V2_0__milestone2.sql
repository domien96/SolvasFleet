-- Drop tables if they exist --

DROP TABLE IF EXISTS insurance_coverages CASCADE;
DROP TABLE IF EXISTS insurances CASCADE;
DROP TABLE IF EXISTS insurance_types CASCADE;

-- Create tables --

CREATE TABLE insurance_types (
  insurance_type_id SERIAL NOT NULL,
  standard_fixed_rate BIGINT NOT NULL,
  standard_tax BIGINT NOT NULL,
  standard_commission BIGINT NOT NULL,
  name VARCHAR (255) NOT NULL,
  PRIMARY KEY (insurance_type_id)
);

CREATE TABLE insurances (
  insurance_id SERIAL NOT NULL,
  risk_premium BIGINT NOT NULL,-- risicopremie
  applicable_exemption BIGINT NOT NULL,
  company_id INT REFERENCES companies(company_id),
  insurance_type_id INT REFERENCES insurance_types(insurance_type_id),
  PRIMARY KEY (insurance_id)
);

-- binary relation m:n

CREATE TABLE contracts (
  contract_id SERIAL NOT NULL,
  fleet_subscription_id INT NOT NULL REFERENCES fleet_subscriptions(fleet_subscription_id),
  insurance_id INT NOT NULL REFERENCES insurances(insurance_id),
  beginDate DATE NOT NULL,
  endDate DATE NOT NULL,
  PRIMARY KEY (contract_id),
  UNIQUE (beginDate,endDate,fleet_subscription_id,insurance_id)

);
