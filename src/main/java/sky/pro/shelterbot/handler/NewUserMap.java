package sky.pro.shelterbot.handler;

import java.util.HashMap;

import sky.pro.shelterbot.message.MessageConstants;
import sky.pro.shelterbot.response.ResponseMessage;

public class NewUserMap extends HashMap<String, ResponseMessage> {

	public void init() {
        put(MessageConstants.BOT_START, ResponseMessage.WELCOME_MESSAGE);
		put(MessageConstants.CAT_SHELTER, ResponseMessage.CAT_SHELTER_CHOSEN);
		put(MessageConstants.DOG_SHELTER, ResponseMessage.DOG_SHELTER_CHOSEN);
	}

}
