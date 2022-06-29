package sky.pro.shelterbot.response;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.Keyboard;

import sky.pro.shelterbot.message.AbstractMessage;
import sky.pro.shelterbot.message.CallVolunteerMessage;
import sky.pro.shelterbot.message.HowToAdoptMessage;
import sky.pro.shelterbot.message.MainMenuMessage;
import sky.pro.shelterbot.message.ReportMessage;
import sky.pro.shelterbot.message.ShelterInfoMessage;
import sky.pro.shelterbot.message.UnknownMessage;
import sky.pro.shelterbot.message.WelcomeMessage;

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
	;
	
	
	

	
	
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
	 * @param id отправка ответа пользователю
	 */
	public void send(long id) {
		message.send(id);
	}
}
