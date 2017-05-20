INSERT INTO permissions (scope) VALUES ( 'write:companies:invoices'), ( 'write:company:invoices');

INSERT INTO role_permissions (role_id, permission_id)
SELECT role_id, permission_id FROM roles FULL JOIN permissions ON 1=1
WHERE function = 'administrator'
AND (scope = 'write:companies:invoices' OR scope = 'write:companies:invoices');
