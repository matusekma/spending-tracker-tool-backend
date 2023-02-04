--liquibase formatted sql
--changeset matusekma:drop_enums
ALTER TABLE transactions ALTER COLUMN category TYPE text;
ALTER TABLE transactions ALTER COLUMN currency TYPE text;
DROP TYPE category;
DROP TYPE currency;
--rollback DROP TABLE transactions
--rollback CREATE TYPE category AS ENUM ('HOUSING', 'TRAVEL', 'FOOD', 'UTILITIES', 'INSURANCE', 'HEALTHCARE', 'FINANCIAL', 'LIFESTYLE', 'ENTERTAINMENT', 'MISCELLANEOUS');
--rollback CREATE TYPE currency AS ENUM ('HUF', 'USD', 'EUR');
--rollback ALTER TABLE transactions ALTER COLUMN category TYPE category;
--rollback ALTER TABLE transactions ALTER COLUMN currency TYPE currency;