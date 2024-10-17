-- liquibase formatted sql

-- changeset fabiosiqueira:1727994927827-1
ALTER TABLE cart_item
    ADD sub_total DECIMAL;

-- changeset fabiosiqueira:1727994927827-2
ALTER TABLE cart_item
    ALTER COLUMN sub_total SET NOT NULL;

