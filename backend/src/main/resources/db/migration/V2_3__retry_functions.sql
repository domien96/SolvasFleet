DROP TABLE functions;

CREATE TABLE functions (
 function_id SERIAL NOT NULL,
 company_id INT NOT NULL,
 user_id INT NOT NULL,
 role_id INT NOT NULL,
 archived BOOLEAN DEFAULT FALSE,
 start_date TIMESTAMP NOT NULL,
 end_date TIMESTAMP,
 FOREIGN KEY (company_id) REFERENCES companies(company_id),
 FOREIGN KEY (user_id) REFERENCES users(user_id),
 FOREIGN KEY (role_id) REFERENCES roles(role_id)
);