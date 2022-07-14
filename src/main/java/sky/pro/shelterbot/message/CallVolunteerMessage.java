package sky.pro.shelterbot.message;

public class CallVolunteerMessage extends AbstractMessage {

	@Override
	public String getMessageText() {
		return "Зову волонтера";
	}
	
	@Override
	public boolean send(long id) {
		super.send(id);
		
		callVolunteer();

		return true;
	}

	/**
	 * Отправить запрос всем волонтерам о необходимости консультации с ним
	 */
	private void callVolunteer() {
    	System.out.println("callVolunteer");
    }

}
