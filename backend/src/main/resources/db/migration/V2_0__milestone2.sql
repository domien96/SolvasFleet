-- Drop tables if they exist --

DROP TABLE IF EXISTS insurance_period CASCADE;
DROP TABLE IF EXISTS insurance CASCADE;
DROP TABLE IF EXISTS insurance_type CASCADE;

-- Create tables --

CREATE TABLE insurances (
  insurance_id SERIAL NOT NULL,
  risk_premium BIGINT NOT NULL,-- risicopremie
  applicable_exemption BIGINT NOT NULL,
  company_id INT REFERENCES companies(company_id),
  PRIMARY KEY (insurance_id)
);

CREATE TABLE insurance_types (
  insurance_type_id SERIAL NOT NULL,
  standard_fixed_rate BIGINT NOT NULL,
  standard_tax BIGINT NOT NULL,
  standard_commision BIGINT NOT NULL,
  name VARCHAR (255) NOT NULL,
  PRIMARY KEY (insurance_type_id)
);

CREATE TABLE insurance_periods (
  insurance_period_id SERIAL NOT NULL,
  beginDate DATE NOT NULL,
  endDate DATE,
  PRIMARY KEY (insurance_period_id)
);

-- Ternary relation

CREATE TABLE insurance_coverages (
  insurance_coverage_id SERIAL NOT NULL,
  fleet_subscription_id INT NOT NULL REFERENCES fleet_subscriptions(fleet_subscription_id),
  insurance_period_id INT NOT NULL REFERENCES insurance_periods(insurance_period_id),
  insurance_type_id INT NOT NULL REFERENCES insurance_types(insurance_type_id),
  PRIMARY KEY (insurance_coverage_id),
  UNIQUE (fleet_subscription_id,insurance_period_id,insurance_type_id)
);
