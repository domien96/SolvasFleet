ALTER TABLE vehicles ADD UNIQUE (chassis_number);
ALTER TABLE vehicles ADD UNIQUE (license_plate);
ALTER TABLE users ADD UNIQUE (vat);
ALTER TABLE roles ADD UNIQUE (company_id, function);
ALTER TABLE fleets ADD UNIQUE (company_id, name);
ALTER TABLE permissions ADD UNIQUE (name);