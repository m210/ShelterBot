-- liquibase formatted sql

--changeset makarov:1
CREATE TABLE if not exists bot_response (
     id SERIAL,
     key_message TEXT,
     response_message TEXT
);

--changeset makarov:2
INSERT INTO bot_response (key_message, response_message) VALUES ('Главное меню', 'Возврат в главное меню');
INSERT INTO bot_response (key_message, response_message) VALUES ('/unknown', 'Неизвестное сообщение. Воспользуйтесь кнопкой меню "Позвать волонтера"');
INSERT INTO bot_response (key_message, response_message) VALUES ('Приют для кошек', 'Добро пожаловать в приют для кошек!');
INSERT INTO bot_response (key_message, response_message) VALUES ('Приют для собак', 'Добро пожаловать в приют для собак!');
INSERT INTO bot_response (key_message, response_message) VALUES ('/cat_shelter_address', 'Бот может выдать расписание работы приюта для кошек и адрес, схему проезда');
INSERT INTO bot_response (key_message, response_message) VALUES ('/cat_shelter_contacts',  'Кошки: Бот может выдать контактные данные охраны для оформления пропуска на машину');
INSERT INTO bot_response (key_message, response_message) VALUES ('/cat_shelter_description',  'Бот может рассказать о приюте для кошек');
INSERT INTO bot_response (key_message, response_message) VALUES ('/cat_shelter_info',  'Бот приветствует пользователя (Этап 1 - кошки)');
INSERT INTO bot_response (key_message, response_message) VALUES ('/cat_shelter_recommends',  'Бот может выдать общие рекомендации о технике безопасности на территории приюта для кошек');
INSERT INTO bot_response (key_message, response_message) VALUES ('/contact',  'Ваша заявка на привлечение волонтера зарегистрирована под номером:');
INSERT INTO bot_response (key_message, response_message) VALUES ('/dog_shelter_address',  'Бот может выдать расписание работы приюта для собак и адрес, схему проезда');
INSERT INTO bot_response (key_message, response_message) VALUES ('/dog_shelter_contacts',  'Собаки: Бот может выдать контактные данные охраны для оформления пропуска на машину');
INSERT INTO bot_response (key_message, response_message) VALUES ('/dog_shelter_description',  'Бот может рассказать о приюте для собак');
INSERT INTO bot_response (key_message, response_message) VALUES ('/dog_shelter_info',  'Бот приветствует пользователя (Этап 1 - собаки)');
INSERT INTO bot_response (key_message, response_message) VALUES ('/dog_shelter_recommends',  'Бот может выдать общие рекомендации о технике безопасности на территории приюта для собак');
INSERT INTO bot_response (key_message, response_message) VALUES ('/how_to_adopt_cat',  'Бот приветствует пользователя (Этап 2 - кошки)');
INSERT INTO bot_response (key_message, response_message) VALUES ('/how_to_adopt_cat_adult',  'Бот может  выдать список рекомендаций по обустройству дома для взрослой кошки');
INSERT INTO bot_response (key_message, response_message) VALUES ('/how_to_adopt_cat_dating',  'Бот может выдать правила знакомства с кошкой до того, как забрать её из приюта');
INSERT INTO bot_response (key_message, response_message) VALUES ('/how_to_adopt_cat_docs',  'Бот может выдать список документов, необходимых для того, чтобы взять животное из приюта');
INSERT INTO bot_response (key_message, response_message) VALUES ('/how_to_adopt_cat_handicapped',  'Бот может  выдать список рекомендаций по обустройству дома для кошки с ограниченными возможностями (зрение, передвижения)');
INSERT INTO bot_response (key_message, response_message) VALUES ('/how_to_adopt_cat_refusal',  'Бот может выдать список причин отказа в заборе кошки из приюта');
INSERT INTO bot_response (key_message, response_message) VALUES ('/how_to_adopt_cat_transp',  'Бот может  выдать список рекомендаций по транспортировке кошки');
INSERT INTO bot_response (key_message, response_message) VALUES ('/how_to_adopt_cat_young',   'Бот может  выдать список рекомендаций по обустройству дома для котенка');
INSERT INTO bot_response (key_message, response_message) VALUES ('/how_to_adopt_dog',  'Бот приветствует пользователя (Этап 2 - собаки)');
INSERT INTO bot_response (key_message, response_message) VALUES ('/how_to_adopt_dog_adult',  'Бот может  выдать список рекомендаций по обустройству дома для взрослую собаку');
INSERT INTO bot_response (key_message, response_message) VALUES ('/how_to_adopt_dog_dating',  'Бот может выдать правила знакомства с собакой до того, как забрать её из приюта');
INSERT INTO bot_response (key_message, response_message) VALUES ('/how_to_adopt_dog_docs',  'Бот может выдать список документов, необходимых для того, чтобы взять собаку из приюта');
INSERT INTO bot_response (key_message, response_message) VALUES ('/how_to_adopt_dog_handicapped',  'Бот может  выдать список рекомендаций по обустройству дома для собаки с ограниченными возможностями (зрение, передвижения)');
INSERT INTO bot_response (key_message, response_message) VALUES ('/how_to_adopt_dog_ken_advice',  'Бот может выдать рекомендации по проверенным кинологам для дальнейшего обращения к ним');
INSERT INTO bot_response (key_message, response_message) VALUES ('/how_to_adopt_dog_ken_rec',  'Бот может выдать советы кинолога по первичному общению с собакой');
INSERT INTO bot_response (key_message, response_message) VALUES ('/how_to_adopt_dog_refusal',  'Бот может выдать список причин отказа в заборе собаки из приюта');
INSERT INTO bot_response (key_message, response_message) VALUES ('/how_to_adopt_dog_transp',  'Бот может  выдать список рекомендаций по транспортировке собаки');
INSERT INTO bot_response (key_message, response_message) VALUES ('/how_to_adopt_dog_young',  'Бот может  выдать список рекомендаций по обустройству дома для щенка');
INSERT INTO bot_response (key_message, response_message) VALUES ('/newuser_unknown',  'Выберите интересующий Вас приют для животных');
INSERT INTO bot_response (key_message, response_message) VALUES ('/report_behavior',  'Пожалуйста, пришлите данные об изменениях в поступках животного. Не менее 10 символов');
INSERT INTO bot_response (key_message, response_message) VALUES ('/report_complete',  'Спасибо за отправленный отчет');
INSERT INTO bot_response (key_message, response_message) VALUES ('/report_health',  'Пожалуйста, пришлите данные о самочувствии животного. Не менее 10 символов');
INSERT INTO bot_response (key_message, response_message) VALUES ('/report_pic',  'Пожалуйста, пришлите фотографию животного');
INSERT INTO bot_response (key_message, response_message) VALUES ('/report_ration',  'Пожалуйста, пришлите данные о рационе животного. Не менее 10 символов');
INSERT INTO bot_response (key_message, response_message) VALUES ('/report_start',  'Для начала пришлите фотографию животного');
INSERT INTO bot_response (key_message, response_message) VALUES ('/start',  'Приветствую, новый пользователь!');