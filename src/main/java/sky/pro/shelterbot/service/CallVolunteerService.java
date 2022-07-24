package sky.pro.shelterbot.service;

import sky.pro.shelterbot.handler.UserMessage;
import sky.pro.shelterbot.model.CallVolunteer;
import sky.pro.shelterbot.model.ParentUser;
import sky.pro.shelterbot.model.ShelterType;

import java.util.List;

public interface CallVolunteerService {


    List<CallVolunteer> findAllCalls();

    List<CallVolunteer> findNewCalls();

    List<CallVolunteer> findNewCalls(ShelterType type);

    CallVolunteer saveCall(ParentUser parent);

    CallVolunteer saveCall(UserMessage userMessage);

}
