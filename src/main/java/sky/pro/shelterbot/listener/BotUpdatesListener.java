package sky.pro.shelterbot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sky.pro.shelterbot.handler.MessageHandler;
import sky.pro.shelterbot.model.Report;
import sky.pro.shelterbot.service.BotResponseService;
import sky.pro.shelterbot.service.ReportService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
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
            } else if (hasPictureMessage(message)) {
                logger.info("The message has a photo");

                PhotoSize current = null;
                for(PhotoSize size : message.photo()) {
                    if(current == null || current.fileSize() < size.fileSize()) {
                        current = size;
                    }
                }

                if(current != null) {
                    id = message.chat().id();
                    text = message.caption();

                    GetFile pic = new GetFile(current.fileId());
                    GetFileResponse response = telegramBot.execute(pic);
                    try {
                        byte[] data = telegramBot.getFileContent(response.file());
                        Report report = reportService.parseReport(text, id, LocalDate.now());
                        report.setPhoto(data);

                        if(report.getBehavior() != null && report.getHealth() != null && report.getRation() != null) {
                            telegramBot.execute(new SendMessage(id, "Отчет принят, все ок!"));
                        } else {
                            if (report.getBehavior() == null) {
                                telegramBot.execute(new SendMessage(id, "Пожалуйста, пришлите данные об изменениях в поступках животного"));
                            }
                            if (report.getHealth() == null) {
                                telegramBot.execute(new SendMessage(id, "Пожалуйста, пришлите данные о самочувствии животного"));
                            }
                            if (report.getRation() == null) {
                                telegramBot.execute(new SendMessage(id, "Пожалуйста, пришлите данные рационе животного"));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
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

