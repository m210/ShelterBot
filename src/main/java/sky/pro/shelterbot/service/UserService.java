package sky.pro.shelterbot.service;

import sky.pro.shelterbot.model.ShelterUser;

public interface UserService {

    ShelterUser findUserByTelegramId(long id);

    ShelterUser findUserById(long id);

    ShelterUser save(ShelterUser user);

}
