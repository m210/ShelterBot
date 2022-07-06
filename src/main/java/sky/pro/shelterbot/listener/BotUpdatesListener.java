package sky.pro.shelterbot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sky.pro.shelterbot.handler.MessageHandler;
import sky.pro.shelterbot.service.BotResponseService;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class BotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(BotUpdatesListener.class);

    // Инжект экземляра бота, созданного в BotConfiguration
    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private BotResponseService botResponseService;

    // Обработчик сообщений
    private MessageHandler handler;

    // Настройка в качестве листинера (обработчика сообщений) объект данного класса
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
        handler = new MessageHandler();
        handler.init(telegramBot, botResponseService);
    }

    /**
     * Метод является обработчиком входящих сообщений
     *
     * @param updates лист приходящих сообщений
     * @return
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            // Получаем присланное сообщение
            Message message = update.message();
            long id = -1;
            String text = null;
            if (message == null) {
                // Если пришло не сообщение, проверяем, что сообщение - нажатая кнопка inline меню

                logger.info("Message == null, trying to check callbackQuery");
                CallbackQuery query = update.callbackQuery();
                if (query != null) {
                    id = query.from().id();
                    text = query.data();
                }
            } else if (hasTextMessage(message)) {
                logger.info("The message has a text");

                id = message.chat().id();
                text = message.text();
            }

            if (id != -1) { // если сообщение имеет текст, отправляем его в обработчик
                handler.processMessage(id, text);
            }
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private boolean hasTextMessage(Message message) {
        return message.text() != null;
    }

    private boolean hasPictureMessage(Message message) {
        return message.photo() != null;
    }
}

