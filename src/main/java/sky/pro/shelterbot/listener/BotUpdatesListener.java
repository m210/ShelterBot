package sky.pro.shelterbot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sky.pro.shelterbot.handler.MessageHandler;
import sky.pro.shelterbot.handler.UserMessage;
import sky.pro.shelterbot.service.BotResponseService;
import sky.pro.shelterbot.service.ReportService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class BotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(BotUpdatesListener.class);

    // Инжект экземляра бота, созданного в BotConfiguration
    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private BotResponseService botResponseService;

    @Autowired
    private ReportService reportService;

    // Обработчик сообщений
    private MessageHandler handler;

    // Настройка в качестве листинера (обработчика сообщений) объект данного класса
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
        handler = new MessageHandler();
        handler.init(telegramBot, botResponseService, reportService);
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
            UserMessage userMessage = new UserMessage();
            if (message == null) {
                // Если пришло не сообщение, проверяем, что сообщение - нажатая кнопка inline меню

                logger.info("Message == null, trying to check callbackQuery");
                CallbackQuery query = update.callbackQuery();
                if (query != null) {
                    userMessage.setUserId(query.from().id());
                    userMessage.setMessage(query.data());
                }
            } else if (hasTextMessage(message)) {
                logger.info("The message has a text");

                userMessage.setUserId(message.chat().id());
                userMessage.setMessage(message.text());
            } else if (hasPictureMessage(message)) {
                logger.info("The message has a photo");

                userMessage.setUserId(message.chat().id());
                userMessage.setMessage(message.caption());

                PhotoSize current = null;
                for(PhotoSize size : message.photo()) {
                    if(current == null || current.fileSize() < size.fileSize()) {
                        current = size;
                    }
                }

                if(current != null) {
                    GetFile pic = new GetFile(current.fileId());
                    GetFileResponse response = telegramBot.execute(pic);
                    try {
                        byte[] data = telegramBot.getFileContent(response.file());
                        userMessage.setPicture(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (userMessage.getUserId() != -1) { // если сообщение имеет текст, отправляем его в обработчик
                handler.processMessage(userMessage);
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

