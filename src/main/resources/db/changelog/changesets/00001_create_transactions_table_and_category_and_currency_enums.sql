--liquibase formatted sql
--changeset matusekma:create_transactions_table_and_category_and_currency_enums
CREATE TYPE category AS ENUM ('HOUSING', 'TRAVEL', 'FOOD', 'UTILITIES', 'INSURANCE', 'HEALTHCARE', 'FINANCIAL', 'LIFESTYLE', 'ENTERTAINMENT', 'MISCELLANEOUS');
CREATE TYPE currency AS ENUM ('HUF', 'USD', 'EUR');
CREATE TABLE transactions (
    id serial primary key,
    summary text not null,
    category category not null,
    sum numeric(14,2) not null,
    currency currency not null,
    paid timestamp with time zone not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);
--rollback DROP TABLE transactions
--rollback DROP TYPE category
--rollback DROP TYPE currency