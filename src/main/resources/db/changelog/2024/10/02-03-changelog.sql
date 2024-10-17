-- liquibase formatted sql

-- changeset fabiosiqueira:1727877112837-7
CREATE UNIQUE INDEX uk_cart_item_cart_id_product_id ON cart_item (cart_id, product_id);

-- changeset fabiosiqueira:1727877112837-1
ALTER TABLE cart_item
    ALTER COLUMN cart_id SET NOT NULL;

-- changeset fabiosiqueira:1727877112837-2
ALTER TABLE cart_item
    ALTER COLUMN category SET NOT NULL;

-- changeset fabiosiqueira:1727877112837-3
ALTER TABLE cart
    ALTER COLUMN created_at SET NOT NULL;

-- changeset fabiosiqueira:1727877112837-4
ALTER TABLE cart_item
    ALTER COLUMN price SET NOT NULL;

-- changeset fabiosiqueira:1727877112837-5
ALTER TABLE cart_item
    ALTER COLUMN product_id SET NOT NULL;

-- changeset fabiosiqueira:1727877112837-6
ALTER TABLE cart_item
    ALTER COLUMN quantity SET NOT NULL;

