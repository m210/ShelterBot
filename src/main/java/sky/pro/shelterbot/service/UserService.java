package sky.pro.shelterbot.service;

import sky.pro.shelterbot.model.ParentUser;
import sky.pro.shelterbot.model.ShelterUser;

import java.util.List;

public interface UserService {

    ParentUser findParentByUserId(long id);

    ShelterUser findUserByTelegramId(long id);

    ShelterUser findUserById(long id);

    /**
     * @param user регистрация пользователя в системе телеграм бота
     * @return возврат сохраненного в базе данных юзера
     */
    ShelterUser saveUser(ShelterUser user);

    ParentUser saveParent(ParentUser parent);

    ParentUser registerAsParent(long userId);

    List<ParentUser> findAllParents();

    long findTelegramIdByParent(ParentUser parent);

    ParentUser findParentById(long parentId);

    ParentUser findParentByTelegramId(long telegramId);

}
