
DROP TABLE IF EXISTS revisions;
CREATE TABLE revisions (
  revision_id SERIAL NOT NULL,
  entity_type VARCHAR NOT NULL, -- enum in java, example Vehicle Company
  user_id INT NOT NULL REFERENCES users(user_id),
  entity_id INT NOT NULL,
  logDate TIMESTAMP , -- trigger?
  method INT NOT NULL, -- enum in java, example Insert, update,...
  payload VARCHAR,
  PRIMARY KEY (revision_id)
);


-- Insert scope for logs
INSERT INTO permissions(scope) VALUES ('read:revisions');

-- Add permission for administrator
INSERT INTO role_permissions (role_id, permission_id)
  SELECT role_id, permission_id FROM roles FULL JOIN permissions ON permissions.scope = 'read:revisions' WHERE function = 'administrator';