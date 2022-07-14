package sky.pro.shelterbot.handler;

import com.pengrad.telegrambot.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sky.pro.shelterbot.message.MessageConstants;
import sky.pro.shelterbot.model.ShelterType;
import sky.pro.shelterbot.response.ResponseMessage;
import sky.pro.shelterbot.service.BotResponseService;
import sky.pro.shelterbot.service.ReportService;

import java.util.Map;

public class MessageHandler {

    private final DogMap dogMap = new DogMap();
    private final CatMap catMap = new CatMap();
    private final ReportHandler reportHandler = new ReportHandler();

    private final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    /**
     * Метод инициализации всех сообщений-ответов бота
     * @param telegramBot экземпляр бота, через которого будут отправляться ответы пользователю
     * @param shelterService сервис, с помощью которого сообщения получают текст ответа пользователю
     */
    public void init(TelegramBot telegramBot, BotResponseService shelterService, ReportService reportService) {
        for (ResponseMessage message : ResponseMessage.values()) {
            // каждому сообщению присваиваем экземпляр бота, через которого будем отправлять ответы
            message.setBot(telegramBot);

            // каждому сообщению присваиваем сервис-ответчик, который будет брать текст ответа из БД
            message.setMessageService(shelterService);

            logger.info("Initializing message: " + message);
        }

        dogMap.init();
        catMap.init();
        reportHandler.init(reportService);
    }

    /**
     * Обработчик сообщений
     * @param userMessage сообщение пользователя, от которого пришло сообщение
     */
    public void processMessage(UserMessage userMessage) {
        // Временный код. ShelterType будет браться из БД о пользователе
        Map<String, ResponseMessage> currentShelter = getMap(ShelterType.CAT_SHELTER);

        if(reportHandler.requireReport()) {
            if(!reportHandler.processReport(userMessage)) {
                currentShelter.getOrDefault(MessageConstants.MAIN_MENU, ResponseMessage.UNKNOWN_MESSAGE).send(userMessage.getUserId());
            }
        } else {
            if(currentShelter.getOrDefault(userMessage.getMessage(), ResponseMessage.UNKNOWN_MESSAGE)
                    .send(userMessage.getUserId()) == ResponseMessage.SEND_REPORT_MESSAGE) {
                reportHandler.startReport();
            }
        }
    }

    private Map<String, ResponseMessage> getMap(ShelterType type) {
        if(type == ShelterType.CAT_SHELTER) {
            return catMap;
        }
        return dogMap;
    }
}
