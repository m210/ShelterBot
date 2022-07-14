package sky.pro.shelterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.pro.shelterbot.model.Knowledge;

@Repository
public interface KnowledgeRepository extends JpaRepository<Knowledge,Long> {
    Knowledge findKnowledgeByQuestionContainingIgnoreCase(String question);

    Knowledge findKnowledgeByCodeId(String codeId);

}
