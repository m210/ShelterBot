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
		WELCOME_MESSAGE(new WelcomeMessage().setMenu(ResponseMenu.EMPTY.getKeyboard())),
		NEWUSER_MESSAGE(new RepositoryResponseMessage(MessageConstants.NEWUSER_UNKNOWN_MESSAGE).setMenu(ResponseMenu.NEWUSER.getKeyboard())),
		CAT_SHELTER_CHOSEN(new ChosenMessage(ShelterType.CAT_SHELTER).setMenu(ResponseMenu.MAIN.getKeyboard())),
		DOG_SHELTER_CHOSEN(new ChosenMessage(ShelterType.DOG_SHELTER).setMenu(ResponseMenu.MAIN.getKeyboard())),

	// Basic messages
		MAIN_MENU_MESSAGE(new MainMenuMessage()),
		UNKNOWN_MESSAGE(new RepositoryResponseMessage(MessageConstants.UNKNOWN_MESSAGE)),
		INTERNAL_ERROR_MESSAGE(new InternalErrorMessage()),
		SEND_REPORT_MESSAGE(new ReportMessage()),
		TAKE_CONTACT(new TakeContactMessage()),

	// Report messages
		PHOTO_REPORTMESSAGE(new PhotoReportMessage()),
		RATION_REPORTMESSAGE(new RationReportMessage()),
		HEALTH_REPORTMESSAGE(new HealthReportMessage()),
		BEHAVIOR_REPORTMESSAGE(new BehaviorReportMessage()),
		COMPLETE_REPORTMESSAGE(new RepositoryResponseMessage("/report_complete").setMenu(ResponseMenu.PARENT_MAIN.getKeyboard())),

	// Cat shelter messages
	CAT_SHELTER_INFO_MESSAGE(new RepositoryResponseMessage("/cat_shelter_info").setMenu(ResponseMenu.CAT_SHELTER_INFO.getKeyboard())),
	CAT_SHELTER_DESCRIPTION(new RepositoryResponseMessage("/cat_shelter_description")),
	CAT_SHELTER_ADDRESS(new RepositoryResponseMessage("/cat_shelter_address")),
	CAT_SHELTER_SECURITY_CONTACTS(new RepositoryResponseMessage("/cat_shelter_contacts")),
	CAT_SHELTER_RECOMMENDS(new RepositoryResponseMessage("/cat_shelter_recommends")),
	CAT_SHELTER_CONTACTS(new RepositoryResponseMessage("/cat_shelter_contacts")),

	CAT_HOW_TO_ADOPT_MESSAGE(new RepositoryResponseMessage("/how_to_adopt_cat").setMenu(ResponseMenu.HOW_TO_ADOPT_CAT.getKeyboard())),
	CAT_HOW_TO_DATING_RULES(new RepositoryResponseMessage("/how_to_adopt_cat_dating")),
	CAT_HOW_TO_DOCUMENTS(new RepositoryResponseMessage("/how_to_adopt_cat_docs")),
	CAT_HOW_TO_TRANSPORTING(new RepositoryResponseMessage("/how_to_adopt_cat_transp")),
	CAT_HOW_TO_IMPOVEMENT_YOUNG(new RepositoryResponseMessage("/how_to_adopt_cat_young")),
	CAT_HOW_TO_IMPOVEMENT_ADULT(new RepositoryResponseMessage("/how_to_adopt_cat_adult")),
	CAT_HOW_TO_IMPOVEMENT_HANDICAPPED(new RepositoryResponseMessage("/how_to_adopt_cat_handicapped")),
	CAT_HOW_TO_REFUSAL(new RepositoryResponseMessage("/how_to_adopt_cat_refusal")),

	// Dog shelter messages
	DOG_SHELTER_INFO_MESSAGE(new RepositoryResponseMessage("/dog_shelter_info").setMenu(ResponseMenu.DOG_SHELTER_INFO.getKeyboard())),
	DOG_SHELTER_DESCRIPTION(new RepositoryResponseMessage("/dog_shelter_description")),
	DOG_SHELTER_ADDRESS(new RepositoryResponseMessage("/dog_shelter_address")),
	DOG_SHELTER_SECURITY_CONTACTS(new RepositoryResponseMessage("/dog_shelter_contacts")),
	DOG_SHELTER_RECOMMENDS(new RepositoryResponseMessage("/dog_shelter_recommends")),
	DOG_SHELTER_CONTACTS(new RepositoryResponseMessage("/dog_shelter_contacts")),

	DOG_HOW_TO_ADOPT_MESSAGE(new RepositoryResponseMessage("/how_to_adopt_dog").setMenu(ResponseMenu.HOW_TO_ADOPT_DOG.getKeyboard())),
	DOG_HOW_TO_DATING_RULES(new RepositoryResponseMessage("/how_to_adopt_dog_dating")),
	DOG_HOW_TO_DOCUMENTS(new RepositoryResponseMessage("/how_to_adopt_dog_docs")),
	DOG_HOW_TO_TRANSPORTING(new RepositoryResponseMessage("/how_to_adopt_dog_transp")),
	DOG_HOW_TO_IMPOVEMENT_YOUNG(new RepositoryResponseMessage("/how_to_adopt_dog_young")),
	DOG_HOW_TO_IMPOVEMENT_ADULT(new RepositoryResponseMessage("/how_to_adopt_dog_adult")),
	DOG_HOW_TO_IMPOVEMENT_HANDICAPPED(new RepositoryResponseMessage("/how_to_adopt_dog_handicapped")),
	DOG_HOW_TO_KENOLOGIST_RECOMMENDS(new RepositoryResponseMessage("/how_to_adopt_dog_ken_rec")),
	DOG_HOW_TO_KENOLOGIST_ADVICE(new RepositoryResponseMessage("/how_to_adopt_dog_ken_advice")),
	DOG_HOW_TO_REFUSAL(new RepositoryResponseMessage("/how_to_adopt_dog_refusal")),
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
			ResponseMessage.INTERNAL_ERROR_MESSAGE.send(userMessage);
			return this;
		}

		return this;
	}

	public AbstractMessage getMessage() {
		return message;
	}
}
