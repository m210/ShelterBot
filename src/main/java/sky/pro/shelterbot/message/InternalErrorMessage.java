package sky.pro.shelterbot.message;

public class InternalErrorMessage extends AbstractMessage{
    @Override
    public String getMessageText() {
        return "Internal error or message text == null!";
    }
}
