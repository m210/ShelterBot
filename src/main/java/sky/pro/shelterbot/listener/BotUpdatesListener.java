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
import sky.pro.shelterbot.message.MessageConstants;
import sky.pro.shelterbot.model.ParentUser;
import sky.pro.shelterbot.service.BotResponseService;
import sky.pro.shelterbot.service.CallVolunteerService;
import sky.pro.shelterbot.service.ReportService;
import sky.pro.shelterbot.service.UserService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Service
public class BotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(BotUpdatesListener.class);

    // Инжект экземляра бота, созданного в BotConfiguration
    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private UserService userService;

    @Autowired
    private CallVolunteerService callVolunteerService;

    // Обработчик сообщений
    @Autowired
    private MessageHandler handler;

    // Настройка в качестве листинера (обработчика сообщений) объект данного класса
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    //@Scheduled(cron = "*/1 * * * * *") //sec
    //@Scheduled(cron = "0 0/1 * * * *") //minute
    @Scheduled(cron = "0 0 10-22 * * *") //since 10 to 22 o'clock of every day.
    public void run() {
        List<ParentUser> list = userService.findAllParents();
        for(ParentUser parent : list) {
            LocalDateTime now = LocalDateTime.now();

            Period probationPeriod = Period.between(now.toLocalDate(), parent.getProbation().toLocalDate());
            if(probationPeriod.getDays() > 0) {
                Period period = Period.between(parent.getLastReportDate().toLocalDate(), now.toLocalDate());
                int diff = period.getDays();

                if (diff >= 1 && (parent.getLastNotification() == null || now.isAfter(parent.getLastNotification()))) {
                    long telegramId = userService.findTelegramIdByParent(parent);
                    telegramBot.execute(new SendMessage(telegramId, "Напоминаем Вам об отправке отчета"));
                    if(diff >= 3) {
                        telegramBot.execute(new SendMessage(telegramId, "Вы не отправляли отчет больше 3х дней, зову волонтера!"));
                        callVolunteerService.saveCall(parent);
                    }
                    parent.setLastNotification(now.plusHours(6));
                    userService.saveParent(parent);
                }
            } else {
                telegramBot.execute(new SendMessage(userService.findTelegramIdByParent(parent), "Поздравляем! Вы прошли испытательный срок."));
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
            } else if (hasContacts(message)) {
                logger.info("The message has a contact");

                userMessage.setUserId(message.chat().id());
                userMessage.setMessage(MessageConstants.USER_CONTACTS);
                userMessage.setContact(message.contact());
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

    private boolean hasContacts(Message message) {
        return message.contact() != null;
    }

    private boolean hasPictureMessage(Message message) {
        return message.photo() != null;
    }
}

