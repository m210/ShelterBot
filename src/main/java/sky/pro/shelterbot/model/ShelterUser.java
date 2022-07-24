package sky.pro.shelterbot.model;

import com.pengrad.telegrambot.model.Contact;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ShelterUser {

	@Id
	@GeneratedValue
	private Long id;
	private long telegramId;
	private String firstName;
	private String lastName;
	private ShelterType type;

	public ShelterUser() {
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public long getTelegramId() {
		return telegramId;
	}
	
	public void setTelegramId(long userId) {
		this.telegramId = userId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public ShelterType getType() {
		return type;
	}
	
	public void setType(ShelterType type) {
		this.type = type;
	}

}
