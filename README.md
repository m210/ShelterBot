# ShelterBot
<p>
Подключённая зависимость для ТГ-бота:

```java
<dependency>
            <groupId>com.github.pengrad</groupId>
            <artifactId>java-telegram-bot-api</artifactId>
            <version>6.0.1</version>
        </dependency>
```
## Создание бота

Бот создаётся вызовом конструктора у класс `TelegramBot`:

```java
TelegramBot bot = new TelegramBot(token);
```

## Обработка входящих сообщений

Для обработки входящих сообщений регистрируем обработчик:

```java
updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            // передача принимаемого сообщения в обработчик
            handler.process(update.message());
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
```

## Отправка сообщений

```java
String text = message.text();
        long id = message.chat().id();

        if (text.equals("/start")) {
            telegramBot.execute(new SendMessage(id, welcomeMessage()));
            return;
        }
```
---
Параметр `welcomeMessage` создаём в отдельном методе:
```java
public String welcomeMessage() {
        return "Здарофф";
    }
```
