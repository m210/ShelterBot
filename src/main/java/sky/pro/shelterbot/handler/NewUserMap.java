package sky.pro.shelterbot.handler;

import java.util.HashMap;

import sky.pro.shelterbot.message.ChosenMessage;
import sky.pro.shelterbot.message.MessageConstants;
import sky.pro.shelterbot.response.ResponseMessage;
import sky.pro.shelterbot.service.UserService;

public class NewUserMap extends HashMap<String, ResponseMessage> {

	public void init(UserService userService) {
        put(MessageConstants.BOT_START, ResponseMessage.WELCOME_MESSAGE);
		put(MessageConstants.CAT_SHELTER, ResponseMessage.CAT_SHELTER_CHOSEN);
		put(MessageConstants.DOG_SHELTER, ResponseMessage.DOG_SHELTER_CHOSEN);
		put(MessageConstants.USER_CONTACTS, ResponseMessage.TAKE_CONTACT);

		((ChosenMessage) ResponseMessage.CAT_SHELTER_CHOSEN.getMessage()).setUserService(userService);
		((ChosenMessage) ResponseMessage.DOG_SHELTER_CHOSEN.getMessage()).setUserService(userService);
	}

}
