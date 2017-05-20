INSERT INTO permissions (scope) VALUES ('read:company:greencard'); -- permission for a specific company
INSERT INTO permissions (scope) VALUES ('read:companies:greencard'); -- global permission

-- Give the admin this new permission
INSERT INTO role_permissions (role_id, permission_id) VALUES((SELECT role_id FROM roles where function = 'administrator'),
                                                             (SELECT permission_id FROM permissions WHERE scope ='read:company:greencard'));
INSERT INTO role_permissions (role_id, permission_id) VALUES((SELECT role_id FROM roles where function = 'administrator'),
                                                             (SELECT permission_id FROM permissions WHERE scope ='read:companies:greencard'));