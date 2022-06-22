package sky.pro.shelterbot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sky.pro.shelterbot.MessageHandler;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class BotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(BotUpdatesListener.class);

    // Инжект экземляра бота, созданного в BotConfiguration
    @Autowired
    private TelegramBot telegramBot;

    // Обработчик сообщений
    private MessageHandler handler;

    // Настройка в качестве листинера (обработчика сообщений) объект данного класса
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
        handler = new MessageHandler(telegramBot);
    }

    // Обработчик приходящих сообщений (реализация метода интерфейса UpdatesListener)
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            // передача принимаемого сообщения в обработчик
            handler.process(update.message());
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}

