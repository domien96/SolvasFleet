DROP TABLE IF EXISTS overwritten_taxes;
DROP TABLE IF EXISTS commissions;


CREATE TABLE commissions (
  commission_id SERIAL NOT NULL,
  company_id INT,
  vehicle_type_id,
  fleet_id INT,
  vehicle_id INT,
  value NUMERIC NOT NULL,
  created_at TIMESTAMP ,
  updated_at TIMESTAMP ,
  PRIMARY KEY (commission_id) ,
  FOREIGN KEY (company_id) REFERENCES companies(company_id),
  FOREIGN KEY (vehicle_type_id) REFERENCES vehicle_types(vehicle_type_id),
  FOREIGN KEY (fleet_id) REFERENCES fleets(fleet_id),
  FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id),
  UNIQUE (company_id,vehicle_type_id,fleet_id,vehicle_id)

);


CREATE TRIGGER update_updated_at BEFORE UPDATE
  ON commissions FOR EACH ROW EXECUTE PROCEDURE  update_updated_at_column();

CREATE TRIGGER update_created_at BEFORE INSERT
  ON commissions FOR EACH ROW EXECUTE PROCEDURE  update_created_at_column();