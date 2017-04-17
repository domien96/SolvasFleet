-- enum values in caps
CREATE TYPE invoice_type_caps AS ENUM ('BILLING','PAYMENT');
ALTER TABLE invoices RENAME COLUMN type TO old_type;
ALTER TABLE invoices ADD COLUMN type invoice_type_caps;
UPDATE invoices SET type = 'BILLING' WHERE old_type = 'billing';
UPDATE invoices SET type = 'PAYMENT' WHERE old_type = 'payment';
ALTER TABLE invoices ALTER COLUMN type SET NOT NULL;
ALTER TABLE invoices DROP COLUMN old_type;
DROP TYPE invoice_type;


-- renaming
ALTER TABLE invoices RENAME COLUMN startDate TO start_date;
ALTER TABLE invoices RENAME COLUMN endDate TO end_date;