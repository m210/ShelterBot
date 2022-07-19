package sky.pro.shelterbot.handler;

import com.pengrad.telegrambot.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sky.pro.shelterbot.message.MessageConstants;
import sky.pro.shelterbot.model.ParentUser;
import sky.pro.shelterbot.model.ReportStage;
import sky.pro.shelterbot.model.ShelterType;
import sky.pro.shelterbot.model.ShelterUser;
import sky.pro.shelterbot.response.ResponseMessage;
import sky.pro.shelterbot.service.BotResponseService;
import sky.pro.shelterbot.service.ReportService;
import sky.pro.shelterbot.service.UserService;

import java.time.LocalDate;
import java.util.Map;

public class MessageHandler {

    private final DogMap dogMap = new DogMap();
    private final CatMap catMap = new CatMap();
    private final NewUserMap newUserMap = new NewUserMap();
    private final UserService userService;

    private final ReportHandler reportHandler = new ReportHandler();

    private final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    public MessageHandler(UserService userService) {
        this.userService = userService;
    }

    /**
     * Метод инициализации всех сообщений-ответов бота
     *
     * @param telegramBot    экземпляр бота, через которого будут отправляться ответы пользователю
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

        newUserMap.init();
        dogMap.init();
        catMap.init();
        reportHandler.init(reportService);
    }

    private ShelterUser registerNewUser(com.pengrad.telegrambot.model.User telegramUser, ShelterType type) {
        ShelterUser user = new ShelterUser();
        user.setFirstName(telegramUser.firstName());
        user.setLastName(telegramUser.lastName());
        user.setTelegramId(telegramUser.id());
        user.setType(type);
        return userService.saveUser(user);
    }

    /**
     * Обработчик сообщений
     *
     * @param userMessage сообщение пользователя, от которого пришло сообщение
     */
    public void processMessage(UserMessage userMessage) {
        ShelterUser user = userService.findUserByTelegramId(userMessage.getUserTelegramId());
        if (user == null) {
            ResponseMessage message = newUserMap.getOrDefault(userMessage.getMessage(), ResponseMessage.NEWUSER_MESSAGE)
                    .send(userMessage.getUserTelegramId());

            switch (message) {
                default:
                    return;
                case CAT_SHELTER_CHOSEN:
                    registerNewUser(userMessage.getUser(), ShelterType.CAT_SHELTER);
                    return;
                case DOG_SHELTER_CHOSEN:
                    registerNewUser(userMessage.getUser(), ShelterType.DOG_SHELTER);
                    return;
            }
        }

        Map<String, ResponseMessage> currentShelter = getMap(user.getType());
        if (reportHandler.requireReport()) {
            ReportStage stage = reportHandler.processReport(userMessage);
            if (stage == ReportStage.CANCELED) {
                currentShelter.getOrDefault(MessageConstants.MAIN_MENU, ResponseMessage.UNKNOWN_MESSAGE).send(userMessage.getUserTelegramId());
            } else if(stage == ReportStage.COMPLETE) {
                ParentUser parent = userService.findParentByTelegramId(userMessage.getUserTelegramId());
                parent.setLastReportDate(LocalDate.now());
                userService.saveParent(parent);
            }
        } else {
            if (currentShelter.getOrDefault(userMessage.getMessage(), ResponseMessage.UNKNOWN_MESSAGE)
                    .send(userMessage.getUserTelegramId()) == ResponseMessage.SEND_REPORT_MESSAGE) {
                if(userService.findParentByTelegramId(userMessage.getUserTelegramId()) == null) {
                    ResponseMessage.NEWUSER_MESSAGE.send(userMessage.getUserTelegramId()); //TODO: сообщение о необходимости взять животное
                } else reportHandler.startReport();
            }
        }
    }

    private Map<String, ResponseMessage> getMap(ShelterType type) {
        if (type == ShelterType.CAT_SHELTER) {
            return catMap;
        }
        return dogMap;
    }
}
