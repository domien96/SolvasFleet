DROP TABLE IF EXISTS transaction_costs;
DROP TABLE IF EXISTS invoices;

CREATE TABLE transaction_costs (
  transaction_costs_id SERIAL NOT NULL,
  forfaitair DOUBLE PRECISION NOT NULL ,
  taxes DOUBLE PRECISION NOT NULL ,
  commission DOUBLE PRECISION NOT NULL ,
  startDate TIMESTAMP NOT NULL ,
  endDate TIMESTAMP NOT NULL ,
  fleet_id INT NOT NULL ,
  insurance_type_id INT NOT NULL ,
  created_at TIMESTAMP ,
  updated_at TIMESTAMP ,
  archived BOOLEAN DEFAULT FALSE ,
  PRIMARY KEY (transaction_costs_id),
  FOREIGN KEY (fleet_id) REFERENCES fleets(fleet_id),
  FOREIGN KEY (insurance_type_id) REFERENCES insurance_types(insurance_type_id)
);

CREATE TRIGGER update_updated_at BEFORE UPDATE
  ON transaction_costs FOR EACH ROW EXECUTE PROCEDURE  update_updated_at_column();

CREATE TRIGGER update_created_at BEFORE INSERT
  ON transaction_costs FOR EACH ROW EXECUTE PROCEDURE  update_created_at_column();


CREATE TABLE invoices (
  invoice_id SERIAL NOT NULL,
  type INT NOT NULL ,
  amount NUMERIC NOT NULL ,
  paid BOOLEAN NOT NULL ,
  company_id INT NOT NULL ,
  fleet_id INT NOT NULL ,
  startDate TIMESTAMP NOT NULL ,
  endDate TIMESTAMP NOT NULL ,
  created_at TIMESTAMP ,
  updated_at TIMESTAMP ,
  archived BOOLEAN DEFAULT FALSE ,
  PRIMARY KEY (invoice_id) ,
  FOREIGN KEY (fleet_id) REFERENCES fleets(fleet_id) ,
  FOREIGN KEY (company_id) REFERENCES companies(company_id)
);

CREATE TRIGGER update_updated_at BEFORE UPDATE
  ON invoices FOR EACH ROW EXECUTE PROCEDURE  update_updated_at_column();

CREATE TRIGGER update_created_at BEFORE INSERT
  ON invoices FOR EACH ROW EXECUTE PROCEDURE  update_created_at_column();
