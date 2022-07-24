package sky.pro.shelterbot.message;

import sky.pro.shelterbot.handler.UserMessage;
import sky.pro.shelterbot.response.ResponseMessage;

public class WelcomeMessage extends AbstractMessage {

	@Override
	public String getMessageText() {
		return getMessageService().getResponseMessage(MessageConstants.BOT_START);
	}

	@Override
	public boolean send(UserMessage userMessage) {
		if(super.send(userMessage)) {
			ResponseMessage.NEWUSER_MESSAGE.send(userMessage);
			return true;
		}
		return false;
	}
	
}
