-- liquibase formatted sql

-- changeset fabiosiqueira:1747257416602-5
ALTER TABLE product_images DROP CONSTRAINT fk_product_images_on_product_entity;

-- changeset fabiosiqueira:1747257416602-6
DROP TABLE product CASCADE;

-- changeset fabiosiqueira:1747257416602-7
DROP TABLE product_images CASCADE;

-- changeset fabiosiqueira:1747257416602-1
DROP INDEX uk_cart_item_cart_id_product_id;
ALTER TABLE cart_item DROP COLUMN product_id;

-- changeset fabiosiqueira:1747257416602-2
ALTER TABLE cart_item
    ADD product_id VARCHAR(255) NOT NULL;


-- changeset fabiosiqueira:1747257416602-3
ALTER TABLE order_item DROP COLUMN product_id;

-- changeset fabiosiqueira:1747257416602-4
ALTER TABLE order_item
    ADD product_id VARCHAR(255) NOT NULL;

