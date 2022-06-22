package sky.pro.shelterbot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;

public class MessageHandler {

    private final TelegramBot telegramBot;

    public MessageHandler(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    // Метод обработчика входящих сообещний
    public void process(Message message) {
        if(message == null) {
            return;
        }

        String text = message.text();
        long id = message.chat().id();

        if (text.equals("/start")) {
            telegramBot.execute(new SendMessage(id, welcomeMessage()));
            return;
        }
    }

    // Приветственное сообщение
    public String welcomeMessage() {
        return "Здарофф";
    }

}
