package sky.pro.shelterbot.message.report;

import com.pengrad.telegrambot.request.SendMessage;
import sky.pro.shelterbot.handler.UserMessage;
import sky.pro.shelterbot.message.AbstractMessage;
import sky.pro.shelterbot.model.ParentUser;
import sky.pro.shelterbot.service.ReportService;

public class ReportMessage extends AbstractMessage {

	protected ReportService service;

	public ReportService getService() {
		return service;
	}

	public void setService(ReportService service) {
		this.service = service;
	}

	@Override
	public String getMessageText() {
		return "Для начала отправьте фотографию животного";
	}

	@Override
	public boolean send(UserMessage userMessage) {
		if(!service.isReportingAllowed(userMessage.getUserTelegramId())) {
			return true;
		}

		return super.send(userMessage);
	}

	public boolean processReport(UserMessage userMessage) {
		return true;
	}

}
