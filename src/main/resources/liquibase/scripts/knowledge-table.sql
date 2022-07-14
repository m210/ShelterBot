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

--changeset yperunov:2
INSERT INTO knowledge (code_id, question, answer, version, has_answered, has_approved)
VALUES ('A-1', 'Описание приюта',
        'Твой Питомец - это приют для кошек и собак, в котором только лучшие хозяева получают возможность взять питомца',
        1, TRUE, TRUE);

--changeset yperunov:3
INSERT INTO knowledge (code_id, question, answer, version, has_answered, has_approved)
VALUES ('A-2', 'Расписание работы приюта и адрес, схему проезда',
        'Рабочие часы пн-пт 10:00-18:00. Адрес: Рябиновая,12, 10мин от м.Рябиновая',
        1, TRUE, TRUE);

INSERT INTO knowledge (code_id, question, answer, version, has_answered, has_approved)
VALUES ('A-3', 'Общие рекомендации о технике безопасности на территории приюта',
        '1. Будьте аккуратны и осознанны 2. Запрещен прямой контакт с животными без сотрудника приюта 3. Не уверенны - спросите сотрудника',
        1, TRUE, TRUE);


