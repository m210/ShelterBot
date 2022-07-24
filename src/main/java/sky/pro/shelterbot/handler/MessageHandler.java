package sky.pro.shelterbot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sky.pro.shelterbot.message.CallVolunteerMessage;
import sky.pro.shelterbot.message.MessageConstants;
import sky.pro.shelterbot.message.TakeContactMessage;
import sky.pro.shelterbot.model.ParentUser;
import sky.pro.shelterbot.model.ReportStage;
import sky.pro.shelterbot.model.ShelterType;
import sky.pro.shelterbot.model.ShelterUser;
import sky.pro.shelterbot.response.ResponseMessage;
import sky.pro.shelterbot.service.BotResponseService;
import sky.pro.shelterbot.service.CallVolunteerService;
import sky.pro.shelterbot.service.ReportService;
import sky.pro.shelterbot.service.UserService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Component
public class MessageHandler {

    private final DogMap dogMap = new DogMap();
    private final CatMap catMap = new CatMap();
    private final NewUserMap newUserMap = new NewUserMap();
    private final ReportHandler reportHandler = new ReportHandler();

    private final UserService userService;
    private final TelegramBot telegramBot;
    private final BotResponseService shelterService;
    private final ReportService reportService;
    private final CallVolunteerService callVolunteerService;

    private final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    public MessageHandler(UserService userService,
                          TelegramBot telegramBot,
                          BotResponseService shelterService,
                          ReportService reportService,
                          CallVolunteerService callVolunteerService) {
        this.userService = userService;
        this.telegramBot = telegramBot;
        this.shelterService = shelterService;
        this.reportService = reportService;
        this.callVolunteerService = callVolunteerService;
    }

    @PostConstruct
    public void init() {
        for (ResponseMessage message : ResponseMessage.values()) {
            // каждому сообщению присваиваем экземпляр бота, через которого будем отправлять ответы
            message.setBot(telegramBot);

            // каждому сообщению присваиваем сервис-ответчик, который будет брать текст ответа из БД
            message.setMessageService(shelterService);

            logger.info("Initializing message: " + message);
        }

        newUserMap.init(userService);
        dogMap.init();
        catMap.init();
        reportHandler.init(reportService);

        ((TakeContactMessage) ResponseMessage.TAKE_CONTACT.getMessage()).setCallVolunteerService(callVolunteerService);
    }

    /**
     * Обработчик сообщений
     *
     * @param userMessage сообщение пользователя, от которого пришло сообщение
     */
    public void processMessage(UserMessage userMessage) {
        ShelterUser user = userService.findUserByTelegramId(userMessage.getUserTelegramId());
        Map<String, ResponseMessage> currentShelter = getMap(user.getType());
        if (reportHandler.requireReport()) {
            ReportStage stage = reportHandler.processReport(userMessage);
            if (stage == ReportStage.CANCELED) {
                currentShelter.getOrDefault(MessageConstants.MAIN_MENU, ResponseMessage.UNKNOWN_MESSAGE).send(userMessage);
            } else if(stage == ReportStage.COMPLETE) {
                ParentUser parent = userService.findParentByTelegramId(userMessage.getUserTelegramId());
                parent.setLastReportDate(LocalDateTime.now());
                userService.saveParent(parent);
            }
        } else {
            if(currentShelter == newUserMap) {
                currentShelter.getOrDefault(userMessage.getMessage(), ResponseMessage.NEWUSER_MESSAGE).send(userMessage);
            } else {
                ResponseMessage response = currentShelter.getOrDefault(userMessage.getMessage(), ResponseMessage.UNKNOWN_MESSAGE).send(userMessage);
                if (response == ResponseMessage.SEND_REPORT_MESSAGE) {
                    if(userService.findParentByTelegramId(userMessage.getUserTelegramId()) == null) {
                        telegramBot.execute(new SendMessage(userMessage.getUserTelegramId(), "У Вас нет животного, по которому надо отчитываться"));
                    } else reportHandler.startReport();
                }
            }
        }
    }

    private Map<String, ResponseMessage> getMap(ShelterType type) {
        if (type == ShelterType.CAT_SHELTER) {
            return catMap;
        } else if (type == ShelterType.DOG_SHELTER) {
            return dogMap;
        }
        return newUserMap;
    }
}
