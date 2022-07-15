package sky.pro.shelterbot.service.impl;

import org.springframework.stereotype.Service;
import sky.pro.shelterbot.UserNotFoundException;
import sky.pro.shelterbot.model.ShelterUser;
import sky.pro.shelterbot.repository.UserRepository;
import sky.pro.shelterbot.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository repository;

	public UserServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public ShelterUser findUserByTelegramId(long id) {
		return repository.findUserByTelegramId(id);
	}

	@Override
	public ShelterUser findUserById(long id) {
		return repository.findById(id).orElseThrow(UserNotFoundException::new);
	}

	@Override
	public ShelterUser save(ShelterUser user) {
		return repository.save(user);
	}

}
