TRUNCATE TABLE permissions CASCADE;

INSERT INTO permissions (scope) VALUES ( 'write:companies:fleets'),( 'write:company:fleets'),( 'read:company:fleets'),( 'read:companies:fleets'),( 'create:company'),( 'write:company'),( 'write:companies'),( 'archive:company'),( 'archive:companies'),( 'read:company'),( 'read:companies'),( 'read:company:contracts'),( 'read:companies:contracts'),( 'write:company:contracts'),( 'write:companies:contracts'),( 'write:users:roles'),( 'read:auth:permissions'),( 'write:auth:permissions'),( 'read:auth:roles'),( 'write:auth:roles'),( 'read:users'),( 'create:users'),( 'write:users');
INSERT INTO role_permissions (role_id, permission_id) SELECT role_id, permission_id FROM roles FULL JOIN permissions ON 1=1 WHERE function = 'administrator';
