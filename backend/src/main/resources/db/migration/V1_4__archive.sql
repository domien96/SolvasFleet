ALTER TABLE companies ADD COLUMN archived BOOLEAN DEFAULT FALSE;
ALTER TABLE roles ADD COLUMN archived BOOLEAN DEFAULT FALSE;
ALTER TABLE users ADD COLUMN archived BOOLEAN DEFAULT FALSE;
ALTER TABLE vehicles ADD COLUMN archived BOOLEAN DEFAULT FALSE;
ALTER TABLE fleets ADD COLUMN archived BOOLEAN DEFAULT FALSE;
ALTER TABLE subfleets ADD COLUMN archived BOOLEAN DEFAULT FALSE;
ALTER TABLE fleet_subscriptions ADD COLUMN archived BOOLEAN DEFAULT FALSE;
ALTER TABLE permissions ADD COLUMN archived BOOLEAN DEFAULT FALSE;