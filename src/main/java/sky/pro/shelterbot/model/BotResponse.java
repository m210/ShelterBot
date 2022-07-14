package sky.pro.shelterbot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class BotResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // идентификатор
    private String keyMessage; // вопрос или команда боту
    private String responseMessage; // ответ, который отправляет бот пользователю на вопрос keyMessage

    public BotResponse() {
    }

    public BotResponse(String keyMessage, String responseMessage) {
        this.keyMessage = keyMessage;
        this.responseMessage = responseMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String response) {
        this.responseMessage = response;
    }

    public void setKeyMessage(String key) {
        this.keyMessage = key;
    }

    public String getKeyMessage() {
        return keyMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BotResponse that = (BotResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(keyMessage, that.keyMessage) && Objects.equals(responseMessage, that.responseMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, keyMessage, responseMessage);
    }

    @Override
    public String toString() {
        return "BotResponse{" +
                "id=" + id +
                ", keyMessage='" + keyMessage + '\'' +
                ", responseMessage='" + responseMessage + '\'' +
                '}';
    }
}
