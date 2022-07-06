package sky.pro.shelterbot.service;

import sky.pro.shelterbot.model.BotResponse;

public interface BotResponseService {

    String getResponseMessage(String message);

    BotResponse saveResponseMessage(String keyMessage, String responseMessage);
}
