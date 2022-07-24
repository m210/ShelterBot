package sky.pro.shelterbot.response;

import com.pengrad.telegrambot.model.request.*;

import sky.pro.shelterbot.message.MessageConstants;

/**
 * Класс, который хранит в себе все используемые ботом меню
 */
public enum ResponseMenu {

	EMPTY(),

	MAIN(MessageConstants.SHELTER_INFO,
			MessageConstants.HOW_TO_ADOPT,
			MessageConstants.SEND_REPORT,
			MessageConstants.CALL_VOLUNTEER),

	DOG_SHELTER_INFO(MessageConstants.MAIN_MENU,
			MessageConstants.SHELTER_DESCRIPTION,
			MessageConstants.SHELTER_ADDRESS,
			MessageConstants.SHELTER_RECOMMENDS,
			MessageConstants.SHELTER_CONTACTS),

	CAT_SHELTER_INFO(MessageConstants.MAIN_MENU,
			MessageConstants.SHELTER_DESCRIPTION,
			MessageConstants.SHELTER_ADDRESS,
			MessageConstants.SHELTER_RECOMMENDS,
			MessageConstants.SHELTER_CONTACTS),
	
	UNKNOWN(new InlineKeyboardButton("Да").callbackData(MessageConstants.CALL_VOLUNTEER)),
	NEWUSER(new InlineKeyboardButton(MessageConstants.CAT_SHELTER).callbackData(MessageConstants.CAT_SHELTER),
			new InlineKeyboardButton(MessageConstants.DOG_SHELTER).callbackData(MessageConstants.DOG_SHELTER));
	
	
	
	
	
	
	
	private final Keyboard keyboard;
	
	/**
	 * Конструктор inline кнопок (кнопки в самом чате)
	 * @param buttons кнопки из которых формируется меню
	 */
	ResponseMenu(InlineKeyboardButton... buttons) {
		this.keyboard = new InlineKeyboardMarkup(buttons);
	}

	/**
	 * Конструктор без параметров удаляет меню
	 */
	ResponseMenu() {
		this.keyboard = new ReplyKeyboardRemove();
	}

	/**
	 * Конструктор reply кнопок (раскрывающееся меню с кнопками)
	 * @param buttons кнопки из которых формируется меню
	 */
	ResponseMenu(String... buttons) {
		ReplyKeyboardMarkup keyboard = null;
		for(String button : buttons) {
			if(keyboard == null) {
				keyboard = new ReplyKeyboardMarkup(button);
			} else {
				if(button.equals(MessageConstants.CALL_VOLUNTEER)) {
					keyboard.addRow(new KeyboardButton(button).requestContact(true));
				} else keyboard.addRow(button);
			}
		}

		if(keyboard != null) {
			keyboard.selective(true);
			keyboard.resizeKeyboard(true);
			keyboard.oneTimeKeyboard(false);
		}
		
		this.keyboard = keyboard;
	}

	/**
	 * Получение кнопок для подключения их к ответу для пользователя
	 * @return экземпляр сформированного меню
	 */
	public Keyboard getKeyboard() {
		return this.keyboard;
	}
}
