--liquibase formatted sql
--changeset matusekma:default_transaction_timestamps
ALTER TABLE transactions
    ALTER COLUMN created_at set default clock_timestamp(),
    ALTER COLUMN updated_at set default clock_timestamp();
--rollback ALTER TABLE transactions ALTER COLUMN created_at drop default, ALTER COLUMN updated_at drop default;