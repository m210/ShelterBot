package sky.pro.shelterbot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Knowledge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codeId;
    private String question;
    private String answer;
    private Integer version;
    private boolean hasAnswered;
    private boolean hasApproved;

    public Knowledge() {
    }

    public Knowledge(Long id, String codeId, String question, String answer, Integer version, boolean hasAnswered, boolean hasApproved) {
        this.id = id;
        this.codeId = codeId;
        this.question = question;
        this.answer = answer;
        this.version = version;
        this.hasAnswered = hasAnswered;
        this.hasApproved = hasApproved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public boolean isHasAnswered() {
        return hasAnswered;
    }

    public void setHasAnswered(boolean hasAnswered) {
        this.hasAnswered = hasAnswered;
    }

    public boolean isHasApproved() {
        return hasApproved;
    }

    public void setHasApproved(boolean hasApproved) {
        this.hasApproved = hasApproved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Knowledge knowledge = (Knowledge) o;
        return hasAnswered == knowledge.hasAnswered && hasApproved == knowledge.hasApproved && id.equals(knowledge.id) && Objects.equals(codeId, knowledge.codeId) && Objects.equals(question, knowledge.question) && Objects.equals(answer, knowledge.answer) && Objects.equals(version, knowledge.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codeId, question, answer, version, hasAnswered, hasApproved);
    }

    @Override
    public String toString() {
        return "Knowledge{" +
                "id=" + id +
                ", codeId='" + codeId + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", version=" + version +
                ", hasAnswered=" + hasAnswered +
                ", hasApproved=" + hasApproved +
                '}';
    }
}
