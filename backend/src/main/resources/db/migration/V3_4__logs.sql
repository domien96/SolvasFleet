
DROP TABLE IF EXISTS revisions;
CREATE TABLE revisions (
  revision_id SERIAL NOT NULL,
  entity_type VARCHAR NOT NULL, -- enum in java, example Vehicle Company
  user_id INT NOT NULL REFERENCES users(user_id),
  entity_id INT NOT NULL,
  logDate TIMESTAMP , -- trigger?
  method INT NOT NULL, -- enum in java, example Insert, update,...
  PRIMARY KEY (revision_id)
);


