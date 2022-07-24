package sky.pro.shelterbot.message;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import sky.pro.shelterbot.handler.UserMessage;
import sky.pro.shelterbot.service.BotResponseService;

/**
 * Абстрактный класс с сообщениями
 * Описывает правила обработки сообщений от пользователя
 */
public abstract class AbstractMessage {

	private Keyboard[] keyboard;
	protected TelegramBot telegramBot;
	private BotResponseService service;
	
	/**
	 * Метод, который будет обращаться к репозиторию базы данных для получения текста ответа
	 * @return возвращает текст, которым будет отвечать бот на сообщение пользователя.
	 */
	public abstract String getMessageText();
	
	/**
	 * @param keyboard присвоение экземпляра меню к сообщению для дальнейших действий
	 */
	public AbstractMessage setMenu(Keyboard... keyboard) {
		this.keyboard = keyboard;
		return this;
	}
	
	/**
	 * @param telegramBot экземпляр бота, через которого будут отправляться ответы пользователю
	 */
	public AbstractMessage setBot(TelegramBot telegramBot) {
		this.telegramBot = telegramBot;
		return this;
	}
	
	/**
	 * Метод отправки ответа пользователю
	 * @param userMessage информация о сообщении пользователя, которому отправляется ответ
	 */
	public boolean send(UserMessage userMessage) {
		String text = getMessageText();
		if(text == null) {
			return false;
		}

		SendMessage message = new SendMessage(userMessage.getUserTelegramId(), text);
		if(keyboard != null) {
			for(Keyboard k : keyboard) {
				message.replyMarkup(k);
			}
		}
		telegramBot.execute(message);
		return true;
	}

	/**
	 * @return сервис, с помощью которого сообщение получает текст ответа пользователю
	 */
	public BotResponseService getMessageService() {
		return service;
	}

	/**
	 * @param service сервис-ответчик, который берет текст ответа из БД
	 */
	public void setMessageService(BotResponseService service) {
		this.service = service;
	}

}
