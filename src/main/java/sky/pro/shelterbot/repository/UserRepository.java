package sky.pro.shelterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.shelterbot.model.ShelterUser;

public interface UserRepository extends JpaRepository<ShelterUser, Long>  {

    ShelterUser findUserByTelegramId(long telegramId);

}
