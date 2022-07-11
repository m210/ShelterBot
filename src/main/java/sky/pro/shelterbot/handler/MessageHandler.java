package sky.pro.shelterbot.handler;

import com.pengrad.telegrambot.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sky.pro.shelterbot.message.MessageConstants;
import sky.pro.shelterbot.message.report.ReportMessage;
import sky.pro.shelterbot.model.ReportStage;
import sky.pro.shelterbot.response.ResponseMessage;
import sky.pro.shelterbot.service.BotResponseService;
import sky.pro.shelterbot.service.ReportService;

import java.util.HashMap;
import java.util.Map;

public class MessageHandler {

    private final Map<String, ResponseMessage> map = new HashMap<>();
    private final Map<ReportStage, ResponseMessage> reportMap = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    private ReportStage reportStage = ReportStage.COMPLETE;

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
            if(message.getMessage() instanceof ReportMessage) {
                ((ReportMessage) message.getMessage()).setService(reportService);
            }

            logger.info("Initializing message: " + message);
        }

        // инициализация карты сообщений
        map.put(MessageConstants.BOT_START, ResponseMessage.WELCOME_MESSAGE);
        map.put(MessageConstants.MAIN_MENU, ResponseMessage.MAIN_MENU_MESSAGE);

        // In main menu
        map.put(MessageConstants.SHELTER_INFO, ResponseMessage.SHELTER_INFO_MESSAGE);
        map.put(MessageConstants.HOW_TO_ADOPT, ResponseMessage.HOW_TO_ADOPT_MESSAGE);
        map.put(MessageConstants.SEND_REPORT, ResponseMessage.SEND_REPORT_MESSAGE);
        map.put(MessageConstants.CALL_VOLUNTEER, ResponseMessage.CALL_VOLUNTEER_MESSAGE);

        // In Shelter info
        map.put(MessageConstants.SHELTER_DESCRIPTION, ResponseMessage.UNKNOWN_MESSAGE); //TODO: ResponseMessage
        map.put(MessageConstants.SHELTER_ADDRESS, ResponseMessage.UNKNOWN_MESSAGE); //TODO: ResponseMessage
        map.put(MessageConstants.SHELTER_RECOMMENDS, ResponseMessage.UNKNOWN_MESSAGE); //TODO: ResponseMessage
        map.put(MessageConstants.SHELTER_USER_CONTACTS, ResponseMessage.UNKNOWN_MESSAGE); //TODO: ResponseMessage

        reportMap.put(ReportStage.PHOTO, ResponseMessage.PHOTO_REPORTMESSAGE);
        reportMap.put(ReportStage.RATION, ResponseMessage.RATION_REPORTMESSAGE);
        reportMap.put(ReportStage.HEALTH, ResponseMessage.HEALTH_REPORTMESSAGE);
        reportMap.put(ReportStage.BEHAVIOR, ResponseMessage.BEHAVIOR_REPORTMESSAGE);
        reportMap.put(ReportStage.COMPLETE, ResponseMessage.COMPLETE_REPORTMESSAGE);
    }

    /**
     * Обработчик сообщений
     * @param userMessage сообщение пользователя, от которого пришло сообщение
     */
    public void processMessage(UserMessage userMessage) {
        if(reportStage != ReportStage.COMPLETE) {
            if(userMessage.getMessage() != null && userMessage.getMessage().equals("Отмена")) {
                reportStage = ReportStage.COMPLETE;
                map.getOrDefault(MessageConstants.MAIN_MENU, ResponseMessage.UNKNOWN_MESSAGE).send(userMessage.getUserId());
                return;
            }

            ReportMessage message = (ReportMessage) reportMap.get(reportStage).getMessage();
            if(!message.processReport(userMessage)) {
                message.send(userMessage.getUserId());
            } else {
                reportStage = ReportStage.values()[reportStage.ordinal() + 1]; // next state
                reportMap.get(reportStage).send(userMessage.getUserId());
            }
        } else {
            ResponseMessage message = map.getOrDefault(userMessage.getMessage(), ResponseMessage.UNKNOWN_MESSAGE);
            if(message == ResponseMessage.SEND_REPORT_MESSAGE) {
                reportStage = ReportStage.PHOTO;
            }
            message.send(userMessage.getUserId());
        }
    }
}
