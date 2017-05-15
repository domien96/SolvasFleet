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
  (null,1,null,null,null,0.17),
  (null,5,null,null,null,0.19),
  (null,2,null,null,null,0.25),
  (null,3,null,null,null,0.25),
  (null,4,null,null,null,0.19);