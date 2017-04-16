DROP TABLE IF EXISTS taxes;


CREATE TABLE taxes (
  tax_id SERIAL NOT NULL,
  tax NUMERIC NOT NULL ,
  vehicletype_id INT NOT NULL,
  insurancetype_id INT NOT NULL,
  created_at TIMESTAMP ,
  updated_at TIMESTAMP ,
  archived BOOLEAN DEFAULT FALSE ,
  PRIMARY KEY (tax_id),
  FOREIGN KEY (vehicletype_id) REFERENCES vehicle_types(vehicletype_id),
  FOREIGN KEY (insurancetype_id) REFERENCES insurance_types(insurance_type_id),
  UNIQUE (vehicletype_id,insuranceType_id)
);

ALTER TABLE insurance_types ADD UNIQUE (name);