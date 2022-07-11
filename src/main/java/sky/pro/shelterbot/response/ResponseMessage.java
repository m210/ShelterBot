package sky.pro.shelterbot.response;

import com.pengrad.telegrambot.TelegramBot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sky.pro.shelterbot.message.*;
import sky.pro.shelterbot.message.report.*;
import sky.pro.shelterbot.service.BotResponseService;

/**
 * Класс, который хранит в себе все используемые ботом ответы
 */
public enum ResponseMessage {
	MAIN_MENU_MESSAGE(new MainMenuMessage().setMenu(ResponseMenu.MAIN.getKeyboard())),
	WELCOME_MESSAGE(new WelcomeMessage().setMenu(ResponseMenu.MAIN.getKeyboard())),
	UNKNOWN_MESSAGE(new UnknownMessage().setMenu(ResponseMenu.UNKNOWN.getKeyboard())),
	
	SHELTER_INFO_MESSAGE(new ShelterInfoMessage().setMenu(ResponseMenu.SHELTER_INFO.getKeyboard())),
	HOW_TO_ADOPT_MESSAGE(new HowToAdoptMessage()),
	SEND_REPORT_MESSAGE(new ReportMessage()),
	CALL_VOLUNTEER_MESSAGE(new CallVolunteerMessage()),

	// Report messages
	PHOTO_REPORTMESSAGE(new PhotoReportMessage()),
	RATION_REPORTMESSAGE(new RationReportMessage()),
	HEALTH_REPORTMESSAGE(new HealthReportMessage()),
	BEHAVIOR_REPORTMESSAGE(new BehaviorReportMessage()),
	COMPLETE_REPORTMESSAGE(new CompleteReportMessage().setMenu(ResponseMenu.MAIN.getKeyboard())),
	;




	private final Logger logger = LoggerFactory.getLogger(ResponseMessage.class);


	private final AbstractMessage message;
	
	ResponseMessage(AbstractMessage message) {
		this.message = message;
	}
	
	/**
	 * @param telegramBot объект, необходимый для отправки ответа пользователю
	 */
	public void setBot(TelegramBot telegramBot) {
		message.setBot(telegramBot);
	}

	/**
	 * @param service сервис для обращения к базе данных, откуда берется
	 *                текст для ответа
	 */
	public void setMessageService(BotResponseService service) {
		message.setMessageService(service);
	}

	/**
	 * @param id отправка ответа пользователю
	 */
	public void send(long id) {
		logger.info("Send " + this);
		message.send(id);
	}

	public AbstractMessage getMessage() {
		return message;
	}
}
