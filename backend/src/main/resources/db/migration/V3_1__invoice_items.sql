ALTER TABLE invoices DROP COLUMN amount;

CREATE TYPE invoice_item_type AS ENUM ('PAYMENT', 'REPAYMENT');

CREATE TABLE invoice_items (
  invoice_item_id SERIAL NOT NULL,
  type invoice_item_type NOT NULL,
  invoice_id INT NOT NULL,
  contract_id INT NOT NULL,
  start_date DATE NOT NULL ,
  end_date DATE NOT NULL ,
  created_at TIMESTAMP ,
  updated_at TIMESTAMP,
  PRIMARY KEY (invoice_item_id),
  FOREIGN KEY (invoice_id) REFERENCES invoices(invoice_id),
  FOREIGN KEY (contract_id) REFERENCES contracts(contract_id)
);