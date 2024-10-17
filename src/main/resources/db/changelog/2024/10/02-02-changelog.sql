-- liquibase formatted sql

-- changeset fabiosiqueira:1727876009930-1
CREATE UNIQUE INDEX uk_product_name ON product (name);

