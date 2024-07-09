--liquibase formatted sql
--changeset elias:001-init_schema.sql
CREATE TABLE IF NOT EXISTS dealer_warehouse
(
    id                 UUID PRIMARY KEY,
    manufacturer       TEXT NOT NULL,
    model              TEXT NOT NULL,
    color              TEXT NOT NULL,
    engine             TEXT NOT NULL,
    price              NUMERIC NOT NULL,
    created_at         TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at         TIMESTAMP WITH TIME ZONE NOT NULL
);
--rollback DROP TABLE dealer_warehouse;