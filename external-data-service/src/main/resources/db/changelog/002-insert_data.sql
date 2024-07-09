--liquibase formatted sql
--changeset elias:002-insert_data.sql
--preconditions onFail:CONTINUE onError:CONTINUE
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM dealer_warehouse
INSERT INTO dealer_warehouse (id, manufacturer, model, color, engine, price, created_at, updated_at)
VALUES ('6451628d-f47c-4b0b-bff1-adfa86274e9b', 'Toyota', 'Corolla', 'White', '1.8L I4', 20000, NOW(), NOW()),
       ('d6d96e03-24ed-4d37-a94b-5f0cbe66b991', 'Honda', 'Civic', 'Black', '2.0L I4', 22000, NOW(), NOW()),
       ('69c17ab3-d108-45f8-99c2-f653067354c0', 'Ford', 'Focus', 'Red', '2.0L I4', 21000, NOW(), NOW()),
       ('e57513ab-0755-44c8-825f-4ae354fc2b2b', 'Chevrolet', 'Malibu', 'Blue', '2.5L I4', 23000, NOW(), NOW()),
       ('bacf5f45-9c56-4efd-8d8d-e79a94cfa569', 'Nissan', 'Sentra', 'Grey', '1.8L I4', 19000, NOW(), NOW());

--rollback DELETE FROM dealer_warehouse WHERE id IN ('6451628d-f47c-4b0b-bff1-adfa86274e9b', 'd6d96e03-24ed-4d37-a94b-5f0cbe66b991', '69c17ab3-d108-45f8-99c2-f653067354c0', 'e57513ab-0755-44c8-825f-4ae354fc2b2b', 'bacf5f45-9c56-4efd-8d8d-e79a94cfa569');