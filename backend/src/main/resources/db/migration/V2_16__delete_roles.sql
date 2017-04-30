-- First we drop the foreign key, since we can't add the cascade.
ALTER TABLE functions
  DROP CONSTRAINT functions_role_id_fkey;

-- Add the constraint again
ALTER TABLE functions
  ADD CONSTRAINT functions_role_id_fkey
FOREIGN KEY (role_id)
REFERENCES roles(role_id)
ON DELETE CASCADE;

-- Drop the archived columns
ALTER TABLE roles DROP COLUMN archived;
