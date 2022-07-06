package sky.pro.shelterbot.service;

import org.springframework.stereotype.Service;
import sky.pro.shelterbot.model.BotResponse;
import sky.pro.shelterbot.repository.BotResponseRepository;

@Service
public class BotResponseServiceImpl implements BotResponseService {

    private final BotResponseRepository shelterRepository;

    public BotResponseServiceImpl(BotResponseRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    public String getResponseMessage(String message) {
        BotResponse response = shelterRepository.findBotResponseByKeyMessage(message);
        if(response == null) {
            return null;
        }
        return response.getResponseMessage();
    }

    public BotResponse saveResponseMessage(String keyMessage, String responseMessage) {
        return shelterRepository.save(new BotResponse(keyMessage, responseMessage));
    }
}
