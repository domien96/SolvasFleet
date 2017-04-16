-- Adding missing created_At and Updated_at columns
ALTER TABLE vehicle_types ADD COLUMN created_at TIMESTAMP;
ALTER TABLE vehicle_types ADD COLUMN updated_at TIMESTAMP;
ALTER TABLE insurance_types ADD COLUMN created_at TIMESTAMP;
ALTER TABLE insurance_types ADD COLUMN updated_at TIMESTAMP;