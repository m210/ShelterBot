package sky.pro.shelterbot.message;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import sky.pro.shelterbot.service.BotResponseService;

/**
 * Абстрактный класс с сообщениями
 * Описывает правила обработки сообщений от пользователя
 */
public abstract class AbstractMessage {

	private Keyboard keyboard;
	private TelegramBot telegramBot;
	private BotResponseService service;
	
	/**
	 * Метод, который будет обращаться к репозиторию базы данных для получения текста ответа
	 * @return возвращает текст, которым будет отвечать бот на сообщение пользователя.
	 */
	public abstract String getMessageText();
	
	/**
	 * @param keyboard присвоение экземпляра меню к сообщению для дальнейших действий
	 */
	public AbstractMessage setMenu(Keyboard keyboard) {
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
	 * @param id идентификатор пользователя, которому отправляется ответ
	 */
	public void send(long id) {
		SendMessage message = new SendMessage(id, getMessageText());
		if(keyboard != null) {
			message.replyMarkup(keyboard);
		}
		telegramBot.execute(message);
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
