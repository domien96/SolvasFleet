----------------------
-- BINARY RELATIONS --
----------------------

----- m to n -----

-- companies m : n users
DROP TABLE IF EXISTS company_users;
CREATE TABLE company_users (
 company_id INT NOT NULL,
 user_id INT NOT NULL,
 PRIMARY KEY (company_id,user_id),
 FOREIGN KEY (company_id) REFERENCES companies(company_id),
 FOREIGN KEY (user_id) REFERENCES users(user_id)
);


-- roles m : n permissions
--DROP TABLE IF EXISTS role_permissions;
--CREATE TABLE role_permissions (
 -- role_id INT NOT NULL,
  --permission_id INT NOT NULL,
 -- PRIMARY KEY (role_id,permission_id),
--  FOREIGN KEY (role_id) REFERENCES roles(role_id),
 -- FOREIGN KEY (permission_id) REFERENCES permissions(permission_id)
--)--;

DROP TABLE IF EXISTS role_permissions;
