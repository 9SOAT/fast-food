-- liquibase formatted sql

-- changeset fabiosiqueira:1727726893231-1
ALTER TABLE product
    ADD status VARCHAR(255);

