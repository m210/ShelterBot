package sky.pro.shelterbot.message.report;

import sky.pro.shelterbot.handler.UserMessage;

import java.time.LocalDate;

public class RationReportMessage extends ReportMessage {

    @Override
    public String getMessageText() {
        return "Пожалуйста, пришлите данные о рационе животного. Не менее 10 символов";
    }

    @Override
    public boolean processReport(UserMessage userMessage) {
        if(userMessage.getMessage() == null || userMessage.getMessage().length() < 10) {
            return false;
        }

        service.addRation(userMessage.getMessage(), userMessage.getUserTelegramId(), LocalDate.now());
        return true;
    }

}
