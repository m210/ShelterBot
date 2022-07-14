-- liquibase formatted sql

--changeset makarov:1
CREATE TABLE if not exists bot_response (
     id SERIAL,
     key_message TEXT,
     response_message TEXT
);
