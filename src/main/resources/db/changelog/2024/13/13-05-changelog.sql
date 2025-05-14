-- liquibase formatted sql

-- changeset victorambiel:1747181104000-1

ALTER TABLE cart ALTER COLUMN consumer_id TYPE VARCHAR(15) USING consumer_id::VARCHAR(15);

ALTER TABLE orders ALTER COLUMN consumer_id TYPE VARCHAR(15) USING consumer_id::VARCHAR(15);

-- rollback ALTER TABLE cart ALTER COLUMN consumer_id TYPE BIGINT USING consumer_id::BIGINT;
-- rollback ALTER TABLE orders ALTER COLUMN consumer_id TYPE BIGINT USING consumer_id::BIGINT;
