package sky.pro.shelterbot.message;

import sky.pro.shelterbot.model.ShelterType;

public class ShelterInfoMessage extends AbstractMessage {

	private final ShelterType type;
	public ShelterInfoMessage(ShelterType type) {
		this.type = type;
	}

	@Override
	public String getMessageText() {
		return "Сюда будет передаваться информация о приюте через БД о " + ((type == ShelterType.CAT_SHELTER) ? "кошках" : "собаках");
	}
	
}
