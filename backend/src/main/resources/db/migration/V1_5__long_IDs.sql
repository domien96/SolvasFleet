-- Basic tables
ALTER TABLE companies ALTER COLUMN company_id TYPE BIGSERIAL;

ALTER TABLE users ALTER COLUMN user_id TYPE BIGSERIAL;

ALTER TABLE vehicle_types ALTER COLUMN vehicletype_id TYPE BIGSERIAL;

ALTER TABLE vehicles ALTER COLUMN vehicle_id TYPE BIGSERIAL;

ALTER TABLE roles ALTER COLUMN role_id TYPE BIGSERIAL;

ALTER TABLE fleets ALTER COLUMN fleet_id TYPE BIGSERIAL;

ALTER TABLE permissions ALTER COLUMN permission_id TYPE BIGSERIAL;

ALTER TABLE subfleets ALTER COLUMN subfleet_id TYPE BIGSERIAL;

ALTER TABLE fleet_subscriptions ALTER COLUMN fleet_subscription_id TYPE BIGSERIAL;

-- Relation tables
ALTER TABLE company_users ALTER COLUMN company_id TYPE BIGINT;
ALTER TABLE company_users ALTER COLUMN user_id TYPE BIGINT;


-- roles m : n permissions
ALTER TABLE role_permissions role_id TYPE BIGINT;
ALTER TABLE role_permissions ALTER COLUMN permission_id TYPE BIGINT;