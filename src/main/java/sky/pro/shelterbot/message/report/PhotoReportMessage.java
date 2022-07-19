package sky.pro.shelterbot.message.report;

import sky.pro.shelterbot.handler.UserMessage;

import java.time.LocalDate;

public class PhotoReportMessage extends ReportMessage {

    @Override
    public String getMessageText() {
        return "Отправьте фотографию животного";
    }

    @Override
    public boolean processReport(UserMessage userMessage) {
        if(userMessage.getPicture() == null) {
            return false;
        }

        service.addPhoto(userMessage.getPicture(), userMessage.getUserTelegramId(), LocalDate.now());
        return true;
    }
}
