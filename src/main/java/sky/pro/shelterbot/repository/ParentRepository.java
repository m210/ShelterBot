package sky.pro.shelterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.shelterbot.model.ParentUser;

/**
 * Репозиторий, в котором хранятся все усыновители животных
 */
public interface ParentRepository extends JpaRepository<ParentUser, Long> {

    ParentUser findByShelterUserId(long userId);
}
