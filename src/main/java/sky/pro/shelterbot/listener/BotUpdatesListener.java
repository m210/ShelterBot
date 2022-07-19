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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sky.pro.shelterbot.handler.MessageHandler;
import sky.pro.shelterbot.handler.UserMessage;
import sky.pro.shelterbot.model.ParentUser;
import sky.pro.shelterbot.service.BotResponseService;
import sky.pro.shelterbot.service.ReportService;
import sky.pro.shelterbot.service.UserService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
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

    @Autowired
    private UserService userService;

    // Обработчик сообщений
    private MessageHandler handler;

    // Настройка в качестве листинера (обработчика сообщений) объект данного класса
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
        handler = new MessageHandler(userService);
        handler.init(telegramBot, botResponseService, reportService);
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void run() {
        List<ParentUser> list = userService.findAllParents();
        for(ParentUser user : list) {
            LocalDate now = LocalDate.now();

            Period probationPeriod = Period.between(now, user.getProbation());
            if(probationPeriod.getDays() > 0) {
                Period period = Period.between(user.getLastReportDate(), now);
                int diff = period.getDays();

                if (diff >= 1) {
                    long telegramId = userService.findTelegramIdByParent(user);
                    telegramBot.execute(new SendMessage(telegramId, "Напоминаем Вам об отправке отчета"));
                    if(diff >= 3) {
                        //TODO: позвать волонтера
                        telegramBot.execute(new SendMessage(telegramId, "Вы не отправляли отчет больше 3х дней, зову волонтера!"));
                    }
                }
            } else {
                // TODO: Поздравление об окончании
            }
        }
    }

    /**
     * Метод является обработчиком входящих сообщений
     *
     * @param updates лист приходящих сообщений
     * @return CONFIRMED_UPDATES_ALL
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
                    userMessage.setUser(query.from());
                }
            } else if (hasTextMessage(message)) {
                logger.info("The message has a text");

                userMessage.setUserId(message.chat().id());
                userMessage.setMessage(message.text());
                userMessage.setUser(message.from());
            } else if (hasPictureMessage(message)) {
                logger.info("The message has a photo");

                userMessage.setUserId(message.chat().id());
                userMessage.setMessage(message.caption());
                userMessage.setUser(message.from());

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

            if (userMessage.getUserTelegramId() != -1) { // если сообщение имеет текст, отправляем его в обработчик
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

