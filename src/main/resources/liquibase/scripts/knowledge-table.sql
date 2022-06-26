-- liquibase formatted sql

--changeset yperunov:1
CREATE TABLE if not exists knowledge (
    id SERIAL,
    code_id TEXT,
    question TEXT,
    answer TEXT,
    version SERIAL,
    has_answered BOOLEAN,
    has_approved BOOLEAN
);
