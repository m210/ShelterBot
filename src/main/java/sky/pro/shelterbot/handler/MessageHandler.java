package sky.pro.shelterbot.handler;

import com.pengrad.telegrambot.TelegramBot;
import sky.pro.shelterbot.message.MessageConstants;
import sky.pro.shelterbot.response.ResponseMessage;

import java.util.HashMap;
import java.util.Map;

public class MessageHandler {

    private final Map<String, ResponseMessage> map = new HashMap<>();

    public void init(TelegramBot telegramBot) {
        for (ResponseMessage message : ResponseMessage.values()) {
            // каждому сообщению присваиваем экземпляр бота, через которого будем отправлять ответы
            message.setBot(telegramBot);

            // TODO: возможно, сюда нужно будет передавать экземпляр REST сервиса, через который каждое
            // сообщение будет брать текст в методе getMessageText()
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
    }

    /**
     * Обработчик сообщений
     * @param id идентификатор пользователя, от которого пришло сообщение
     * @param text текст, который пришел от пользователя
     */
    public boolean processMessage(long id, String text) {
        map.getOrDefault(text, ResponseMessage.UNKNOWN_MESSAGE).send(id);
        return true;
    }

}
