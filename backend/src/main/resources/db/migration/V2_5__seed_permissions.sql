INSERT INTO permissions (action, resource) VALUES ('READ', 'vehicle'), ('WRITE', 'vehicle'),  ('READ', 'company'), ('WRITE', 'company'),
 ('READ', 'fleet'), ('WRITE', 'fleet'),  ('READ', 'user'), ('WRITE', 'user'),  ('READ', 'role'), ('WRITE', 'role'),  ('READ', 'function'), ('WRITE', 'function'), ('READ', 'permission');

INSERT INTO roles (function) VALUES ('administrator');

INSERT INTO role_permissions (role_id, permission_id) SELECT role_id, permission_id FROM roles FULL JOIN permissions ON 1=1 WHERE function = 'administrator';