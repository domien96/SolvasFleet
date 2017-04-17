ALTER TABLE invoices DROP COLUMN type;

DROP TYPE invoice_type;
CREATE TYPE invoice_type AS ENUM ('BILLING','PAYMENT');

ALTER TABLE invoices ADD COLUMN type invoice_type NOT NULL;

ALTER TABLE invoices RENAME COLUMN startDate TO start_date;
ALTER TABLE invoices RENAME COLUMN endDate TO end_date;