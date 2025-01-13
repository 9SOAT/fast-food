-- liquibase formatted sql

-- changeset vambiel:1736705220000-1
CREATE TABLE webhook_payment
(
    id              BIGINT,
    action          VARCHAR(255),
    api_version     VARCHAR(255),
    data            JSONB DEFAULT '{}'::jsonb,
    date_created    TIMESTAMP,
    live_mode       BOOLEAN,
    type            VARCHAR(255),
    user_id         BIGINT,
    CONSTRAINT pk_webhook_payment PRIMARY KEY (id)
);
