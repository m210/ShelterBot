package sky.pro.shelterbot.handler;

import java.util.HashMap;

import sky.pro.shelterbot.message.MessageConstants;
import sky.pro.shelterbot.response.ResponseMessage;

public class DogMap extends HashMap<String, ResponseMessage> {
	
	public void init() {
		// инициализация карты сообщений
        put(MessageConstants.MAIN_MENU, ResponseMessage.MAIN_MENU_MESSAGE);

        // Basic
            put(MessageConstants.USER_CONTACTS, ResponseMessage.TAKE_CONTACT);

        // In main menu
        put(MessageConstants.SHELTER_INFO, ResponseMessage.DOG_SHELTER_INFO_MESSAGE);
        put(MessageConstants.HOW_TO_ADOPT, ResponseMessage.DOG_HOW_TO_ADOPT_MESSAGE);
        put(MessageConstants.SEND_REPORT, ResponseMessage.SEND_REPORT_MESSAGE);
        
        // In Shelter info
        put(MessageConstants.SHELTER_DESCRIPTION, ResponseMessage.DOG_SHELTER_DESCRIPTION);
        put(MessageConstants.SHELTER_ADDRESS, ResponseMessage.DOG_SHELTER_ADDRESS);
        put(MessageConstants.SHELTER_SUCURITY_CONTACTS, ResponseMessage.DOG_SHELTER_CONTACTS);
        put(MessageConstants.SHELTER_RECOMMENDS, ResponseMessage.DOG_SHELTER_RECOMMENDS);

        // How to adopt a dog

            put(MessageConstants.HOW_TO_DATING_RULES, ResponseMessage.DOG_HOW_TO_DATING_RULES);
            put(MessageConstants.HOW_TO_DOCUMENTS, ResponseMessage.DOG_HOW_TO_DOCUMENTS);
            put(MessageConstants.HOW_TO_TRANSPORTING, ResponseMessage.DOG_HOW_TO_TRANSPORTING);
            put(MessageConstants.HOW_TO_IMPOVEMENT_YOUNG, ResponseMessage.DOG_HOW_TO_IMPOVEMENT_YOUNG);
            put(MessageConstants.HOW_TO_IMPOVEMENT_ADULT, ResponseMessage.DOG_HOW_TO_IMPOVEMENT_ADULT);
            put(MessageConstants.HOW_TO_IMPOVEMENT_HANDICAPPED, ResponseMessage.DOG_HOW_TO_IMPOVEMENT_HANDICAPPED);
            put(MessageConstants.HOW_TO_KENOLOGIST_RECOMMENDS, ResponseMessage.DOG_HOW_TO_KENOLOGIST_RECOMMENDS);
            put(MessageConstants.HOW_TO_KENOLOGIST_ADVICE, ResponseMessage.DOG_HOW_TO_KENOLOGIST_ADVICE);
            put(MessageConstants.HOW_TO_REFUSAL, ResponseMessage.DOG_HOW_TO_REFUSAL);
	}

}
