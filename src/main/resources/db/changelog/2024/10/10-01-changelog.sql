-- liquibase formatted sql

-- changeset fabiosiqueira:1728568996884-1
CREATE INDEX idx_product_category_status ON product (category, status);

-- changeset fabiosiqueira:1728568996884-2
CREATE INDEX idx_product_status ON product (status);

