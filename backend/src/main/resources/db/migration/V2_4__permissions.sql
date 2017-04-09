DROP TABLE IF EXISTS permissions CASCADE;

CREATE TABLE permissions (
  permission_id SERIAL NOT NULL,
  action VARCHAR(255) NOT NULL,
  resource VARCHAR(255) NOT NULL,
  PRIMARY KEY (permission_id)
);