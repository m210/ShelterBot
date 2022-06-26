package sky.pro.shelterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.shelterbot.model.Knowledge;

public interface KnowledgeRepository extends JpaRepository<Knowledge,Long> {
}
