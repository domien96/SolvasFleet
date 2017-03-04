----------------------
-- BINARY RELATIONS --
----------------------

----- 1 to 1 -----

--fleet_subscription 0..1 : 1 vehicle
ALTER TABLE fleet_subscriptions ADD COLUMN vehicle_id int NOT NULL; -- total participation of a subscription
ALTER TABLE fleet_subscriptions ADD FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id);

----- 1 to n -----

--fleet_subscriptions n : 1 fleet
ALTER TABLE fleet_subscriptions ADD COLUMN fleet_id int NOT NULL;
ALTER TABLE fleet_subscriptions ADD FOREIGN KEY (fleet_id) REFERENCES fleets(fleet_id);

--fleets n : company 1
ALTER TABLE fleets ADD COLUMN company_id int NOT NULL;
ALTER TABLE fleets ADD FOREIGN KEY (company_id) REFERENCES companies(company_id);

--company 0..1 : n vehicles
ALTER TABLE vehicles ADD COLUMN company_id int;
ALTER TABLE vehicles ADD FOREIGN KEY (company_id) REFERENCES companies(company_id);