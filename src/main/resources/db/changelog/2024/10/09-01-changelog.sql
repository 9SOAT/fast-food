-- liquibase formatted sql

-- changeset fabiosiqueira:1728481199666-1
ALTER TABLE payment
    ADD approved_at TIMESTAMP WITHOUT TIME ZONE;

-- changeset fabiosiqueira:1728481199666-2
CREATE INDEX idx_payment_transaction_id ON payment (transaction_id);

