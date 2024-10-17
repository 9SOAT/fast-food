-- liquibase formatted sql

-- changeset fabiosiqueira:1727992762154-1
ALTER TABLE order_item
    ADD product_category SMALLINT;
ALTER TABLE order_item
    ADD product_name VARCHAR(255);

-- changeset fabiosiqueira:1727992762154-2
ALTER TABLE order_item
    ALTER COLUMN product_category SET NOT NULL;

-- changeset fabiosiqueira:1727992762154-4
ALTER TABLE order_item
    ALTER COLUMN product_name SET NOT NULL;

