package sky.pro.shelterbot.message;

import com.pengrad.telegrambot.model.User;
import sky.pro.shelterbot.handler.UserMessage;
import sky.pro.shelterbot.model.ShelterType;
import sky.pro.shelterbot.model.ShelterUser;
import sky.pro.shelterbot.service.UserService;

public class ChosenMessage extends AbstractMessage {

    private UserService userService;

    private final ShelterType type;
    public ChosenMessage(ShelterType type) {
        this.type = type;
    }

    @Override
    public String getMessageText() {
        return "Добро пожаловать в приют для " + ((type == ShelterType.CAT_SHELTER) ? "кошек!" : "собак!");
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean send(UserMessage userMessage) {
        registerNewUser(userMessage.getUser());
        return super.send(userMessage);
    }

    private ShelterUser registerNewUser(User telegramUser) {
        ShelterUser user = new ShelterUser();
        user.setFirstName(telegramUser.firstName());
        user.setLastName(telegramUser.lastName());
        user.setTelegramId(telegramUser.id());
        user.setType(type);
        return userService.saveUser(user);
    }

}
