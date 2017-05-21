
INSERT INTO permissions (scope) VALUES ('write:taxes');

INSERT INTO role_permissions (role_id, permission_id)
  SELECT role_id, permission_id FROM roles FULL JOIN permissions ON permissions.scope = 'write:taxes' WHERE function = 'administrator';