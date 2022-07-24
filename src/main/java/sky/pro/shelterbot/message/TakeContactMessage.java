package sky.pro.shelterbot.message;

import com.pengrad.telegrambot.request.SendMessage;
import sky.pro.shelterbot.handler.UserMessage;
import sky.pro.shelterbot.model.CallVolunteer;
import sky.pro.shelterbot.service.CallVolunteerService;

public class TakeContactMessage extends AbstractMessage {

    private CallVolunteerService callVolunteerService;

    @Override
    public String getMessageText() {
        return "Ваша заявка на привлечение волонтера зарегистрирована под номером: ";
    }

    public void setCallVolunteerService(CallVolunteerService callVolunteerService) {
        this.callVolunteerService = callVolunteerService;
    }

    @Override
    public boolean send(UserMessage userMessage) {
        CallVolunteer call = callVolunteerService.saveCall(userMessage);
        if(call != null) {
            telegramBot.execute(new SendMessage(userMessage.getUserTelegramId(), getMessageText() + call.getId()));
            return true;
        }
        return false;
    }

}
