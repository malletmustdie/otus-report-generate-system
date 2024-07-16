--liquibase formatted sql
--changeset elias:001-init_schema.sql
CREATE TABLE IF NOT EXISTS configs
(
    report_name        UUID PRIMARY KEY,
    extension          TEXT NOT NULL,
    created_at         TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at         TIMESTAMP WITH TIME ZONE NOT NULL
);
--rollback DROP TABLE configs;