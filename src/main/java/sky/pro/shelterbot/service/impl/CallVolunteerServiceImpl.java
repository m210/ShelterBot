package sky.pro.shelterbot.service.impl;

import com.pengrad.telegrambot.model.Contact;
import org.springframework.stereotype.Service;
import sky.pro.shelterbot.handler.UserMessage;
import sky.pro.shelterbot.model.*;
import sky.pro.shelterbot.repository.CallVolunteerRepository;
import sky.pro.shelterbot.service.CallVolunteerService;
import sky.pro.shelterbot.service.UserService;

import java.time.LocalDate;
import java.util.List;

@Service
public class CallVolunteerServiceImpl implements CallVolunteerService {

    private final CallVolunteerRepository callVolunteerRepository;
    private final UserService userService;

    public CallVolunteerServiceImpl(CallVolunteerRepository callVolunteerRepository, UserService userService) {
        this.callVolunteerRepository = callVolunteerRepository;
        this.userService = userService;
    }

    @Override
    public List<CallVolunteer> findAllCalls() {
        return callVolunteerRepository.findAll();
    }

    @Override
    public List<CallVolunteer> findNewCalls() {
        return callVolunteerRepository.findAllByStatusEquals(ReportStatus.NEW);
    }

    @Override
    public List<CallVolunteer> findNewCalls(ShelterType type) {
        List<CallVolunteer> list = callVolunteerRepository.findAllByStatusEquals(ReportStatus.NEW);
        list.removeIf(a -> a.getType() != type);
        return list;
    }

    @Override
    public CallVolunteer saveCall(ParentUser parent) {
        ShelterUser user = userService.findUserById(parent.getShelterUserId());

        CallVolunteer call = new CallVolunteer();
        call.setDate(LocalDate.now());
        call.setType(user.getType());
        call.setFirstName(user.getFirstName());
        call.setLastName(user.getLastName());
        call.setParentId(parent.getId());
        call.setCause("Не отправлял отчет более трех дней!");
        call.setPhoneNumber(parent.getPhoneNumber());

        return callVolunteerRepository.save(call);
    }

    @Override
    public CallVolunteer saveCall(UserMessage userMessage) {
        if(userMessage.getContact() == null) {
            return null;
        }

        Contact contact = userMessage.getContact();
        CallVolunteer call = new CallVolunteer();
        ShelterUser user = userService.findUserByTelegramId(userMessage.getUserId());
        if(user == null) {
            user = new ShelterUser();
            user.setId(-1L);
            user.setType(ShelterType.NEWUSER);
        }
        call.setDate(LocalDate.now());
        call.setType(user.getType());
        call.setFirstName(contact.firstName());
        call.setLastName(contact.lastName());
        call.setPhoneNumber(contact.phoneNumber());
        ParentUser parent = userService.findParentByUserId(user.getId());
        if(parent != null) {
            call.setParentId(parent.getId());
        }
        call.setCause("Инициатива пользователя");

        return callVolunteerRepository.save(call);
    }
}
