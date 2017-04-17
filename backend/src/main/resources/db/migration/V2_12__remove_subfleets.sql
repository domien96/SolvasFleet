ALTER TABLE  fleet_subscriptions ADD COLUMN fleet_id INT REFERENCES fleets(fleet_id);
-- now fill in the fleet id using the subfleet id
UPDATE fleet_subscriptions f SET fleet_id = (SELECT sf.fleet_id FROM subfleets sf JOIN fleets USING (fleet_id) WHERE sf.subfleet_id = f.subfleet_id );

ALTER TABLE fleet_subscriptions ALTER COLUMN fleet_id SET NOT NULL;

ALTER TABLE  fleet_subscriptions DROP COLUMN subfleet_id;

DROP TABLE subfleets;