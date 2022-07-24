package sky.pro.shelterbot.response;

import com.pengrad.telegrambot.TelegramBot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sky.pro.shelterbot.handler.UserMessage;
import sky.pro.shelterbot.message.*;
import sky.pro.shelterbot.message.report.*;
import sky.pro.shelterbot.model.ShelterType;
import sky.pro.shelterbot.service.BotResponseService;

/**
 * Класс, который хранит в себе все используемые ботом ответы
 */
public enum ResponseMessage {
	// New user messages
		WELCOME_MESSAGE(new WelcomeMessage().setMenu(ResponseMenu.NEWUSER.getKeyboard())),
		NEWUSER_MESSAGE(new NewUserMessage().setMenu(ResponseMenu.NEWUSER.getKeyboard())),
		CAT_SHELTER_CHOSEN(new ChosenMessage(ShelterType.CAT_SHELTER).setMenu(ResponseMenu.MAIN.getKeyboard())),
		DOG_SHELTER_CHOSEN(new ChosenMessage(ShelterType.DOG_SHELTER).setMenu(ResponseMenu.MAIN.getKeyboard())),

	// Basic messages
		MAIN_MENU_MESSAGE(new MainMenuMessage().setMenu(ResponseMenu.MAIN.getKeyboard())),
		UNKNOWN_MESSAGE(new UnknownMessage().setMenu(ResponseMenu.UNKNOWN.getKeyboard())),

		HOW_TO_ADOPT_MESSAGE(new HowToAdoptMessage()),
		SEND_REPORT_MESSAGE(new ReportMessage()),
		CALL_VOLUNTEER_MESSAGE(new CallVolunteerMessage()),
		TAKE_CONTACT(new TakeContactMessage()),

	// Report messages
		PHOTO_REPORTMESSAGE(new PhotoReportMessage()),
		RATION_REPORTMESSAGE(new RationReportMessage()),
		HEALTH_REPORTMESSAGE(new HealthReportMessage()),
		BEHAVIOR_REPORTMESSAGE(new BehaviorReportMessage()),
		COMPLETE_REPORTMESSAGE(new CompleteReportMessage().setMenu(ResponseMenu.MAIN.getKeyboard())),

	// Cat shelter messages
		CAT_SHELTER_INFO_MESSAGE(new ShelterInfoMessage(ShelterType.CAT_SHELTER).setMenu(ResponseMenu.CAT_SHELTER_INFO.getKeyboard())),
		CAT_SHELTER_DESCRIPTION(new ShelterDescriptionMessage(ShelterType.CAT_SHELTER)),

	// Dog shelter messages
		DOG_SHELTER_INFO_MESSAGE(new ShelterInfoMessage(ShelterType.DOG_SHELTER).setMenu(ResponseMenu.DOG_SHELTER_INFO.getKeyboard())),
		DOG_SHELTER_DESCRIPTION(new ShelterDescriptionMessage(ShelterType.DOG_SHELTER)),
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
	 * @param userMessage класс с информацией о принятом сообщении от пользователя
	 */
	public ResponseMessage send(UserMessage userMessage) {
		logger.info("Send " + this);
		if(!message.send(userMessage)) {
			logger.error("The message was not be sent: " + this);
			return ResponseMessage.UNKNOWN_MESSAGE.send(userMessage);
		}

		return this;
	}

	public AbstractMessage getMessage() {
		return message;
	}
}
