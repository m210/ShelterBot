package sky.pro.shelterbot.message;

import sky.pro.shelterbot.model.ShelterType;

public class ChosenMessage extends AbstractMessage {

    private final ShelterType type;
    public ChosenMessage(ShelterType type) {
        this.type = type;
    }

    @Override
    public String getMessageText() {
        return "Добро пожаловать в приют для " + ((type == ShelterType.CAT_SHELTER) ? "кошек!" : "собак!");
    }

}
