TRUNCATE TABLE role_permissions CASCADE;
DROP TABLE IF EXISTS permissions CASCADE;

CREATE TABLE permissions (
  permission_id SERIAL NOT NULL,
  scope VARCHAR(255) NOT NULL,
  PRIMARY KEY (permission_id)
);

INSERT INTO permissions (scope) VALUES ('read:companies'), ('read:company'), ('write:company'), ('create:company'), ('archive:company'), ('read:vehicles'), ('read:vehicles:types'), ('read:company:fleets'), ('write:company:fleets'), ('read:contracts'), ('read:contracts:types'), ('read:company:contracts'), ('write:company:contracts'), ('read:auth:roles'), ('write:auth:roles'), ('read:auth:permissions'), ('read:users'), ('read:user'), ('write:user'), ('create:users'), ('archive:users'), ('write:users:roles');

INSERT INTO role_permissions (role_id, permission_id) SELECT role_id, permission_id FROM roles FULL JOIN permissions ON 1=1 WHERE function = 'administrator';
