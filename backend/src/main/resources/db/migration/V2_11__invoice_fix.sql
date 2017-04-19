-- enum values in caps
DROP TYPE IF EXISTS invoice_type_caps;
ALTER TABLE invoices RENAME COLUMN type TO old_type;
ALTER TABLE invoices ADD COLUMN type INTEGER;
UPDATE invoices SET type = 1 WHERE old_type = 'billing';
UPDATE invoices SET type = 2 WHERE old_type = 'payment';
ALTER TABLE invoices ALTER COLUMN type SET NOT NULL;
ALTER TABLE invoices DROP COLUMN old_type;
DROP TYPE invoice_type;


-- renaming
ALTER TABLE invoices RENAME COLUMN startDate TO start_date;
ALTER TABLE invoices RENAME COLUMN endDate TO end_date;