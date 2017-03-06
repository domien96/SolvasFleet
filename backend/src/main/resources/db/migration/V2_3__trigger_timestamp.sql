CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = now();
  RETURN NEW;
END;
$$ language 'plpgsql';


CREATE OR REPLACE FUNCTION update_created_at_column()
  RETURNS TRIGGER AS $$
BEGIN
  NEW.created_at = now();
  NEW.updated_at = now();
  RETURN NEW;
END;
$$ language 'plpgsql';


-- create
CREATE TRIGGER update_updated_at BEFORE UPDATE
  ON companies FOR EACH ROW EXECUTE PROCEDURE  update_updated_at_column();

CREATE TRIGGER update_updated_at BEFORE UPDATE
  ON fleets FOR EACH ROW EXECUTE PROCEDURE  update_updated_at_column();

CREATE TRIGGER update_updated_at BEFORE UPDATE
  ON fleet_subscriptions FOR EACH ROW EXECUTE PROCEDURE  update_updated_at_column();

CREATE TRIGGER update_updated_at BEFORE UPDATE
  ON permissions FOR EACH ROW EXECUTE PROCEDURE  update_updated_at_column();

CREATE TRIGGER update_updated_at BEFORE UPDATE
  ON roles FOR EACH ROW EXECUTE PROCEDURE  update_updated_at_column();

CREATE TRIGGER update_updated_at BEFORE UPDATE
  ON users FOR EACH ROW EXECUTE PROCEDURE  update_updated_at_column();

CREATE TRIGGER update_updated_at BEFORE UPDATE
  ON vehicles FOR EACH ROW EXECUTE PROCEDURE  update_updated_at_column();

CREATE TRIGGER update_updated_at BEFORE UPDATE
  ON vehicletypes FOR EACH ROW EXECUTE PROCEDURE  update_updated_at_column();


-- update

CREATE TRIGGER update_created_at BEFORE INSERT
  ON companies FOR EACH ROW EXECUTE PROCEDURE  update_created_at_column();

CREATE TRIGGER update_created_at BEFORE INSERT
  ON fleets FOR EACH ROW EXECUTE PROCEDURE  update_created_at_column();
CREATE TRIGGER update_created_at BEFORE INSERT

  ON fleet_subscriptions FOR EACH ROW EXECUTE PROCEDURE  update_created_at_column();

CREATE TRIGGER update_created_at BEFORE INSERT
  ON permissions FOR EACH ROW EXECUTE PROCEDURE  update_created_at_column();
CREATE TRIGGER update_created_at BEFORE INSERT
  ON roles FOR EACH ROW EXECUTE PROCEDURE  update_created_at_column();
CREATE TRIGGER update_created_at BEFORE INSERT
  ON users FOR EACH ROW EXECUTE PROCEDURE  update_created_at_column();
CREATE TRIGGER update_created_at BEFORE INSERT
  ON vehicletypes FOR EACH ROW EXECUTE PROCEDURE  update_created_at_column();



