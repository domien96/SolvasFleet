ALTER TABLE roles DROP COLUMN IF EXISTS company_id;
ALTER TABLE roles DROP COLUMN IF EXISTS user_id;

create table users_companies_roles (
 company_id INT NOT NULL,
 user_id INT NOT NULL,
 role_id INT NOT NULL,
 PRIMARY KEY (company_id, user_id, role_id),
 FOREIGN KEY (company_id) REFERENCES companies(company_id),
 FOREIGN KEY (user_id) REFERENCES users(user_id),
 FOREIGN KEY (role_id) REFERENCES roles(role_id)
);
