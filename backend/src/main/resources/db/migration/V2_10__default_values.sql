INSERT INTO vehicle_types (name) VALUES
  ('PersonalVehicle'),
  ('SemiHeavyTruck'),
  ('Truck'),
  ('Truck+12'),
  ('Van');






INSERT INTO insurance_types (name) VALUES
  ('CivilLiability'),
  ('LegalAid'),
  ('DriverInsurance'),
  ('TravelInsurance'),
  ('Omnium');


--PersonalVehicle

INSERT INTO taxes (tax,vehicletype_id,insurancetype_id)
  SELECT 0.2710,vehicletype_id,insurance_type_id FROM vehicle_types CROSS JOIN insurance_types
  WHERE vehicle_types.name like 'PersonalVehicle' AND insurance_types.name like 'CivilLiability';


INSERT INTO taxes (tax,vehicletype_id,insurancetype_id)
  SELECT 0.1675,vehicletype_id,insurance_type_id FROM vehicle_types CROSS JOIN insurance_types
  WHERE vehicle_types.name like 'PersonalVehicle' AND insurance_types.name like 'DriverInsurance';

INSERT INTO taxes (tax,vehicletype_id,insurancetype_id)
  SELECT 0.1675,vehicletype_id,insurance_type_id FROM vehicle_types CROSS JOIN insurance_types
  WHERE vehicle_types.name like 'PersonalVehicle' AND insurance_types.name like 'LegalAid';

INSERT INTO taxes (tax,vehicletype_id,insurancetype_id)
  SELECT 0.2675,vehicletype_id,insurance_type_id FROM vehicle_types CROSS JOIN insurance_types
  WHERE vehicle_types.name like 'PersonalVehicle' AND insurance_types.name like 'Omnium';


--SemiHeavyTruck

INSERT INTO taxes (tax,vehicletype_id,insurancetype_id)
  SELECT 0.1425,vehicletype_id,insurance_type_id FROM vehicle_types CROSS JOIN insurance_types
  WHERE vehicle_types.name like 'SemiHeavyTruck' AND insurance_types.name like 'CivilLiability';


INSERT INTO taxes (tax,vehicletype_id,insurancetype_id)
  SELECT 0.1675,vehicletype_id,insurance_type_id FROM vehicle_types CROSS  JOIN insurance_types
  WHERE vehicle_types.name like 'SemiHeavyTruck' AND insurance_types.name like 'LegalAid';

INSERT INTO taxes (tax,vehicletype_id,insurancetype_id)
  SELECT 0.1390,vehicletype_id,insurance_type_id FROM vehicle_types CROSS JOIN insurance_types
  WHERE vehicle_types.name like 'SemiHeavyTruck' AND insurance_types.name like 'Omnium';


--Truck

INSERT INTO taxes (tax,vehicletype_id,insurancetype_id)
  SELECT 0.1425,vehicletype_id,insurance_type_id FROM vehicle_types CROSS JOIN insurance_types
  WHERE vehicle_types.name like 'Truck' AND insurance_types.name like 'CivilLiability';


INSERT INTO taxes (tax,vehicletype_id,insurancetype_id)
  SELECT 0.1675,vehicletype_id,insurance_type_id FROM vehicle_types CROSS JOIN insurance_types
  WHERE vehicle_types.name like 'Truck' AND insurance_types.name like 'LegalAid';

INSERT INTO taxes (tax,vehicletype_id,insurancetype_id)
  SELECT 0.1390,vehicletype_id,insurance_type_id FROM vehicle_types CROSS JOIN insurance_types
  WHERE vehicle_types.name like 'Truck' AND insurance_types.name like 'Omnium';


--Truck+12

INSERT INTO taxes (tax,vehicletype_id,insurancetype_id)
  SELECT 0.1285,vehicletype_id,insurance_type_id FROM vehicle_types CROSS JOIN insurance_types
  WHERE vehicle_types.name like 'Truck+12' AND insurance_types.name like 'CivilLiability';


INSERT INTO taxes (tax,vehicletype_id,insurancetype_id)
  SELECT 0.1675,vehicletype_id,insurance_type_id FROM vehicle_types CROSS JOIN insurance_types
  WHERE vehicle_types.name like 'Truck+12' AND insurance_types.name like 'LegalAid';

INSERT INTO taxes (tax,vehicletype_id,insurancetype_id)
  SELECT 0.1250,vehicletype_id,insurance_type_id FROM vehicle_types CROSS JOIN insurance_types
  WHERE vehicle_types.name like 'Truck+12' AND insurance_types.name like 'Omnium';


--Van

INSERT INTO taxes (tax,vehicletype_id,insurancetype_id)
  SELECT 0.2210,vehicletype_id,insurance_type_id FROM vehicle_types CROSS JOIN insurance_types
  WHERE vehicle_types.name like 'Van' AND insurance_types.name like 'CivilLiability';


INSERT INTO taxes (tax,vehicletype_id,insurancetype_id)
  SELECT 0.1675,vehicletype_id,insurance_type_id FROM vehicle_types CROSS JOIN insurance_types
  WHERE vehicle_types.name like 'Van' AND insurance_types.name like 'LegalAid';

INSERT INTO taxes (tax,vehicletype_id,insurancetype_id)
  SELECT 0.2175,vehicletype_id,insurance_type_id FROM vehicle_types CROSS JOIN insurance_types
  WHERE vehicle_types.name like 'Van' AND insurance_types.name like 'Omnium';

