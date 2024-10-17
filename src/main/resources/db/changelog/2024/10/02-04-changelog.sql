-- liquibase formatted sql

-- changeset fabiosiqueira:1727877376669-1
ALTER TABLE cart
    ALTER COLUMN status SET NOT NULL;

