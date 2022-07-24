package sky.pro.shelterbot.handler;

import java.util.HashMap;

import sky.pro.shelterbot.message.MessageConstants;
import sky.pro.shelterbot.response.ResponseMessage;

public class CatMap extends HashMap<String, ResponseMessage> {

	public void init() {
		// инициализация карты сообщений
        put(MessageConstants.MAIN_MENU, ResponseMessage.MAIN_MENU_MESSAGE);

        // Basic
        put(MessageConstants.USER_CONTACTS, ResponseMessage.TAKE_CONTACT);
        
        // In main menu
        put(MessageConstants.SHELTER_INFO, ResponseMessage.CAT_SHELTER_INFO_MESSAGE);
        put(MessageConstants.HOW_TO_ADOPT, ResponseMessage.CAT_HOW_TO_ADOPT_MESSAGE);
        put(MessageConstants.SEND_REPORT, ResponseMessage.SEND_REPORT_MESSAGE);
        
        // In Shelter info
        put(MessageConstants.SHELTER_DESCRIPTION, ResponseMessage.CAT_SHELTER_DESCRIPTION);
        put(MessageConstants.SHELTER_ADDRESS, ResponseMessage.CAT_SHELTER_ADDRESS);
        put(MessageConstants.SHELTER_SUCURITY_CONTACTS, ResponseMessage.CAT_SHELTER_CONTACTS);
        put(MessageConstants.SHELTER_RECOMMENDS, ResponseMessage.CAT_SHELTER_RECOMMENDS);

        // How to adopt a cat

            put(MessageConstants.HOW_TO_DATING_RULES, ResponseMessage.CAT_HOW_TO_DATING_RULES);
            put(MessageConstants.HOW_TO_DOCUMENTS, ResponseMessage.CAT_HOW_TO_DOCUMENTS);
            put(MessageConstants.HOW_TO_TRANSPORTING, ResponseMessage.CAT_HOW_TO_TRANSPORTING);
            put(MessageConstants.HOW_TO_IMPOVEMENT_YOUNG, ResponseMessage.CAT_HOW_TO_IMPOVEMENT_YOUNG);
            put(MessageConstants.HOW_TO_IMPOVEMENT_ADULT, ResponseMessage.CAT_HOW_TO_IMPOVEMENT_ADULT);
            put(MessageConstants.HOW_TO_IMPOVEMENT_HANDICAPPED, ResponseMessage.CAT_HOW_TO_IMPOVEMENT_HANDICAPPED);
            put(MessageConstants.HOW_TO_REFUSAL, ResponseMessage.CAT_HOW_TO_REFUSAL);
    }
}
