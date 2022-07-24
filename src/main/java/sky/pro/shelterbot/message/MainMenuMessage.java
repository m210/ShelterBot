package sky.pro.shelterbot.message;

import sky.pro.shelterbot.handler.UserMessage;
import sky.pro.shelterbot.response.ResponseMenu;
import sky.pro.shelterbot.service.UserService;

public class MainMenuMessage extends AbstractMessage {

    private UserService userService;

    @Override
    public String getMessageText() {
        return getMessageService().getResponseMessage(MessageConstants.MAIN_MENU);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean send(UserMessage userMessage) {
        if(userService.findParentByTelegramId(userMessage.getUserTelegramId()) == null) {
            setMenu(ResponseMenu.MAIN.getKeyboard());
        } else  setMenu(ResponseMenu.PARENT_MAIN.getKeyboard());

        return super.send(userMessage);
    }
}
