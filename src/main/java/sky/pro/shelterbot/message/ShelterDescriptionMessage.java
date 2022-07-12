package sky.pro.shelterbot.message;

import sky.pro.shelterbot.model.ShelterType;

public class ShelterDescriptionMessage extends AbstractMessage {

    private final ShelterType type;
    public ShelterDescriptionMessage(ShelterType type) {
        this.type = type;
    }

    @Override
    public String getMessageText() {
        if(type == ShelterType.CAT_SHELTER) {
            return "Информация из БД о кошачьем приюте";
        }
        return "Информация из БД о собачьем приюте";
    }

}
