package sky.pro.shelterbot.message;

public class RepositoryResponseMessage extends AbstractMessage {

    private final String responseKey;
    public RepositoryResponseMessage(String responseKey) {
        this.responseKey = responseKey;
    }

    @Override
    public String getMessageText() {
        return getMessageService().getResponseMessage(responseKey);
    }
}
