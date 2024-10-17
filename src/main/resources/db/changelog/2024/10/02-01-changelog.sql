-- liquibase formatted sql

-- changeset fabiosiqueira:1727875792869-1
ALTER TABLE product
    ALTER COLUMN category SET NOT NULL;

-- changeset fabiosiqueira:1727875792869-2
ALTER TABLE product
    ALTER COLUMN name SET NOT NULL;

-- changeset fabiosiqueira:1727875792869-3
ALTER TABLE product
    ALTER COLUMN price SET NOT NULL;

-- changeset fabiosiqueira:1727875792869-4
ALTER TABLE product
    ALTER COLUMN status SET NOT NULL;

