-- liquibase formatted sql

-- changeset victorambiel:1737211036000-1

ALTER TABLE webhook_payment
    RENAME COLUMN payment_id TO transaction_id;

ALTER TABLE webhook_payment
    ALTER COLUMN transaction_id TYPE VARCHAR(255) USING transaction_id::VARCHAR(255);

