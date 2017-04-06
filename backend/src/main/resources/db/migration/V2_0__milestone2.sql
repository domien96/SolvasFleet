-- Drop tables if they exist --

DROP TABLE IF EXISTS contracts CASCADE;
DROP TABLE IF EXISTS insurance_types CASCADE;

-- Create tables --

CREATE TABLE insurance_types (
  insurance_type_id SERIAL NOT NULL,
  name VARCHAR (255) NOT NULL,
  PRIMARY KEY (insurance_type_id)
);

CREATE TABLE contracts (
  contract_id SERIAL NOT NULL,
  fleet_subscription_id INT NOT NULL REFERENCES fleet_subscriptions(fleet_subscription_id),
  company_id INT NOT NULL REFERENCES companies(company_id),
  insurance_type_id INT NOT NULL REFERENCES insurance_types(insurance_type_id),
  premium INT NOT NULL,
  franchise INT NOT NULL,
  startDate TIMESTAMP NOT NULL,
  endDate TIMESTAMP NOT NULL,
  archived BOOLEAN DEFAULT FALSE,
  updated_at TIMESTAMP,
  created_at TIMESTAMP,
  PRIMARY KEY (contract_id),
  UNIQUE (startDate,endDate,fleet_subscription_id,contract_id,insurance_type_id)
);

CREATE TRIGGER update_updated_at BEFORE UPDATE
  ON contracts FOR EACH ROW EXECUTE PROCEDURE  update_updated_at_column();

CREATE TRIGGER update_created_at BEFORE INSERT
  ON contracts FOR EACH ROW EXECUTE PROCEDURE  update_created_at_column();
