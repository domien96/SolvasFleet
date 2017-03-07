----------------------
-- BINARY RELATIONS --
----------------------

----- 1 to n -----

--vehicle n : 1 vehicletype
ALTER TABLE vehicles ADD COLUMN vehicletype_id INT NOT NULL;
ALTER TABLE vehicles ADD FOREIGN KEY (vehicletype_id) REFERENCES vehicletypes(vehicletype_id);

-- An integrity constraint has to be added later to make sure no vehicles belong to multiple fleets at the same moment.

--fleet_subscription n : 1 vehicle
ALTER TABLE fleet_subscriptions ADD COLUMN vehicle_id INT NOT NULL; -- total participation of a subscription
ALTER TABLE fleet_subscriptions ADD FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id);

--fleet_subscriptions n : 1 fleet
ALTER TABLE fleet_subscriptions ADD COLUMN fleet_id INT NOT NULL;
ALTER TABLE fleet_subscriptions ADD FOREIGN KEY (fleet_id) REFERENCES fleets(fleet_id);

--fleets n : company 1
--ALTER TABLE fleets ADD COLUMN company_id int NOT NULL; -- already done in V2.1
ALTER TABLE fleets ADD FOREIGN KEY (company_id) REFERENCES companies(company_id);

--company 0..1 : n vehicles (already exists, see V2)
--ALTER TABLE vehicles ADD COLUMN company_id int;
--ALTER TABLE vehicles ADD FOREIGN KEY (company_id) REFERENCES companies(company_id);