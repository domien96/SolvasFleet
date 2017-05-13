DROP TABLE IF EXISTS overwritten_taxes;
DROP TABLE IF EXISTS commissions;


CREATE TABLE commissions (
  commission_id SERIAL NOT NULL,
  company_id INT,
  insurance_type_id INT,
  vehicle_type_id INT,
  fleet_id INT,
  vehicle_id INT,
  value NUMERIC NOT NULL,
  created_at TIMESTAMP ,
  updated_at TIMESTAMP ,
  PRIMARY KEY (commission_id) ,
  -- no references as these may not be null
  UNIQUE (company_id,vehicle_type_id,fleet_id,vehicle_id,insurance_type_id)

);


CREATE TRIGGER update_updated_at BEFORE UPDATE
  ON commissions FOR EACH ROW EXECUTE PROCEDURE  update_updated_at_column();

CREATE TRIGGER update_created_at BEFORE INSERT
  ON commissions FOR EACH ROW EXECUTE PROCEDURE  update_created_at_column();



INSERT INTO commissions (company_id,insurance_type_id,vehicle_type_id,fleet_id,vehicle_id,value) VALUES
  (0,1,0,0,0,0.17),
  (0,5,0,0,0,0.19),
  (0,2,0,0,0,0.25),
  (0,3,0,0,0,0.25),
  (0,4,0,0,0,0.19);