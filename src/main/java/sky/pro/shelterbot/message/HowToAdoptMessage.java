package sky.pro.shelterbot.message;

public class HowToAdoptMessage extends AbstractMessage {

	@Override
	public String getMessageText() {
		return getMessageService().getResponseMessage(MessageConstants.HOW_TO_ADOPT);
	}
}
