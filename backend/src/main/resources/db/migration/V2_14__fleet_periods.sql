
ALTER TABLE  fleets ADD COLUMN facturation_period INT; -- In months

ALTER TABLE  fleets ADD COLUMN payment_period INT ; -- In months

UPDATE fleets f SET payment_period = 1;
UPDATE fleets f SET facturation_period = 1;

ALTER TABLE fleets ALTER COLUMN facturation_period SET NOT NULL;

ALTER TABLE fleets ALTER COLUMN payment_period SET NOT NULL;


ALTER TABLE companies ADD COLUMN company_type INT ;

UPDATE companies f SET company_type = 0;

ALTER TABLE companies ALTER COLUMN company_type SET NOT NULL;