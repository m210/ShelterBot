package sky.pro.shelterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.shelterbot.model.ShelterType;
import sky.pro.shelterbot.model.ShelterUser;

import java.util.List;

public interface UserRepository extends JpaRepository<ShelterUser, Long>  {

    ShelterUser findUserByTelegramId(long telegramId);

    List<ShelterUser> findAllByType(ShelterType type);

}
