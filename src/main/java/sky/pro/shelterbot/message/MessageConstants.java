package sky.pro.shelterbot.message;

/**
 * В этом классе хранится весь текст,
 * который используется в коде программы (являются командами)
 */
public class MessageConstants {
	
	public static final String BOT_START = "/start"; // БД WelcomeMessage
	
	public static final String MAIN_MENU = "Главное меню"; //БД MainMenuMessage
	public static final String USER_CONTACTS = "/contact"; //БД TakeContactMessage
	public static final String UNKNOWN_MESSAGE = "/unknown"; //БД UnknownMessage
	public static final String NEWUSER_UNKNOWN_MESSAGE = "/newuser_unknown"; // БД NewUserMessage

	public static final String VOLUNTEER_MESSAGE = "Сообщение от волонтера: "; //ReportServiceImpl

	// New user
	public static final String CAT_SHELTER = "Приют для кошек"; //БД ChosenMessage
	public static final String DOG_SHELTER = "Приют для собак"; //БД ChosenMessage

	// Main Menu
	public static final String SHELTER_INFO = "Узнать информацию о приюте"; //БД ShelterInfoMessage
	public static final String HOW_TO_ADOPT = "Как взять животное из приюта"; //БД HowToAdoptMessage
	public static final String SEND_REPORT = "Прислать отчет о питомце"; //БД ReportMessage
	public static final String CALL_VOLUNTEER = "Позвать волонтера"; //Telegram API message (sendContact)
	public static final String SEND_CONTACTS = "Оставить контакты для связи"; //Telegram API message (sendContact)


	// Shelter info menu
	public static final String SHELTER_DESCRIPTION = "Описание приюта";
	public static final String SHELTER_ADDRESS = "Расписание работы приюта и адрес, схему проезда";
	public static final String SHELTER_RECOMMENDS = "Общие рекомендации о технике безопасности на территории приюта";
	public static final String SHELTER_SUCURITY_CONTACTS = "Контактные данные охраны для оформления пропуска на машину";

	// How to adopt animal menu
	public static final String HOW_TO_DATING_RULES = "Правила знакомства с животным";
	public static final String HOW_TO_DOCUMENTS = "Cписок документов, необходимых для того, чтобы взять животное из приюта";
	public static final String HOW_TO_TRANSPORTING = "Рекомендаций по транспортировке";
	public static final String HOW_TO_IMPOVEMENT_YOUNG = "Список рекомендаций по обустройству дома для молодого животного";
	public static final String HOW_TO_IMPOVEMENT_ADULT = "Список рекомендаций по обустройству дома для взрослого животного";
	public static final String HOW_TO_IMPOVEMENT_HANDICAPPED = "Список рекомендаций по обустройству дома для животного с ограниченными возможностями";
	public static final String HOW_TO_REFUSAL = "Cписок причин отказа в заборе животного из приюта";

	// Adopt a dog
	public static final String HOW_TO_KENOLOGIST_RECOMMENDS = "Рекомендации по проверенным кинологам для дальнейшего обращения к ним";
	public static final String HOW_TO_KENOLOGIST_ADVICE = "Советы кинолога по первичному общению с собакой";

}
