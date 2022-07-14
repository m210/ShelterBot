package sky.pro.shelterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.shelterbot.model.BotResponse;

public interface BotResponseRepository extends JpaRepository<BotResponse, Long> {

    BotResponse findBotResponseByKeyMessage(String keyMessage);

}
