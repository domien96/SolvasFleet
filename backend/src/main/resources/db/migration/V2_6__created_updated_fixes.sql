-- Adding missing created_At and Updated_at columns
ALTER TABLE vehicle_types ADD COLUMN created_at TIMESTAMP;
ALTER TABLE vehicle_types ADD COLUMN updated_at TIMESTAMP;
ALTER TABLE insurance_types ADD COLUMN created_at TIMESTAMP;
ALTER TABLE insurance_types ADD COLUMN updated_at TIMESTAMP;

CREATE TRIGGER update_updated_at BEFORE UPDATE
  ON vehicle_types FOR EACH ROW EXECUTE PROCEDURE  update_updated_at_column();

CREATE TRIGGER update_created_at BEFORE INSERT
  ON vehicle_types FOR EACH ROW EXECUTE PROCEDURE  update_created_at_column();

CREATE TRIGGER update_updated_at BEFORE UPDATE
  ON insurance_types FOR EACH ROW EXECUTE PROCEDURE  update_updated_at_column();

CREATE TRIGGER update_created_at BEFORE INSERT
  ON insurance_types FOR EACH ROW EXECUTE PROCEDURE  update_created_at_column();