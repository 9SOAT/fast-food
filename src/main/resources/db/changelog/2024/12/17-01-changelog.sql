-- liquibase formatted sql

-- changeset victorambiel:1734480387800-1
ALTER TABLE orders ADD COLUMN created_at TIMESTAMP WITHOUT TIME ZONE;
-- rollback ALTER TABLE orders DROP COLUMN created_at;

-- changeset victorambiel:1734480409000-2
UPDATE orders
   SET created_at = now();

