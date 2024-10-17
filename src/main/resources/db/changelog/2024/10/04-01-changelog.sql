-- liquibase formatted sql

-- changeset fabiosiqueira:1728045287032-6
ALTER TABLE product_entity_images
    DROP CONSTRAINT fk_productentity_images_on_product_entity;

-- changeset fabiosiqueira:1728045287032-4
CREATE TABLE product_images
(
    product_entity_id BIGINT NOT NULL,
    images            VARCHAR(255)
);

-- changeset fabiosiqueira:1728045287032-5
ALTER TABLE product_images
    ADD CONSTRAINT fk_product_images_on_product_entity FOREIGN KEY (product_entity_id) REFERENCES product (id);

-- changeset fabiosiqueira:1728045287032-7
DROP TABLE product_entity_images CASCADE;

-- changeset fabiosiqueira:1728045287032-1
ALTER TABLE order_item
    DROP COLUMN product_category;

-- changeset fabiosiqueira:1728045287032-2
ALTER TABLE order_item
    ADD product_category VARCHAR(255) NOT NULL;

-- changeset fabiosiqueira:1728045287032-3
ALTER TABLE order_item
    ALTER COLUMN product_category SET NOT NULL;

