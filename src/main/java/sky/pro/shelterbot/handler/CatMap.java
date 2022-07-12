package sky.pro.shelterbot.handler;

import java.util.HashMap;

import sky.pro.shelterbot.message.MessageConstants;
import sky.pro.shelterbot.response.ResponseMessage;

public class CatMap extends HashMap<String, ResponseMessage> {

	public void init() {
		// инициализация карты сообщений
        put(MessageConstants.BOT_START, ResponseMessage.WELCOME_MESSAGE);
        put(MessageConstants.MAIN_MENU, ResponseMessage.MAIN_MENU_MESSAGE);
        
        
        // In main menu
        put(MessageConstants.SHELTER_INFO, ResponseMessage.CAT_SHELTER_INFO_MESSAGE);
        put(MessageConstants.HOW_TO_ADOPT, ResponseMessage.HOW_TO_ADOPT_MESSAGE);
        put(MessageConstants.SEND_REPORT, ResponseMessage.SEND_REPORT_MESSAGE);
        put(MessageConstants.CALL_VOLUNTEER, ResponseMessage.CALL_VOLUNTEER_MESSAGE);
        
        // In Shelter info
        put(MessageConstants.SHELTER_DESCRIPTION, ResponseMessage.CAT_SHELTER_DESCRIPTION);
        put(MessageConstants.SHELTER_ADDRESS, ResponseMessage.UNKNOWN_MESSAGE); //TODO: ResponseMessage
        put(MessageConstants.SHELTER_RECOMMENDS, ResponseMessage.UNKNOWN_MESSAGE); //TODO: ResponseMessage
        put(MessageConstants.SHELTER_USER_CONTACTS, ResponseMessage.UNKNOWN_MESSAGE); //TODO: ResponseMessage
	}
}
