package sky.pro.shelterbot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import sky.pro.shelterbot.model.Report;
import sky.pro.shelterbot.model.ReportResponse;
import sky.pro.shelterbot.model.ReportStatus;
import sky.pro.shelterbot.model.ShelterUser;
import sky.pro.shelterbot.repository.ReportRepository;
import sky.pro.shelterbot.service.ReportService;
import sky.pro.shelterbot.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository repository;
    private final UserService userService;
    private final TelegramBot telegramBot;

    public ReportServiceImpl(ReportRepository repository, UserService userService, TelegramBot telegramBot) {
        this.repository = repository;
        this.userService = userService;
        this.telegramBot = telegramBot;
    }

    private Report getReport(Long fromUser, LocalDate date) {
        Report report = repository.getReportByUserIdAndAndDate(fromUser, date);
        if(report == null) {
            report = new Report();
            report.setUserId(fromUser);
            report.setDate(date);
        }

        return report;
    }

    @Override
    public void addPhoto(byte[] data, Long fromUser, LocalDate date) {
        Report report = getReport(fromUser, date);

        report.setPhoto(data);
        repository.save(report);
    }

    @Override
    public void addRation(String text, Long fromUser, LocalDate date) {
        Report report = getReport(fromUser, date);

        report.setRation(text);
        repository.save(report);
    }

    @Override
    public void addHealth(String text, Long fromUser, LocalDate date) {
        Report report = getReport(fromUser, date);

        report.setHealth(text);
        repository.save(report);
    }

    @Override
    public void addBehavior(String text, Long fromUser, LocalDate date) {
        Report report = getReport(fromUser, date);

        report.setBehavior(text);
        repository.save(report);
    }

    @Override
    public List<ReportResponse> findReportsWithStatus(ReportStatus status) {
        List<Report> reports = repository.findAll();
        List<ReportResponse> list = new ArrayList<>();
        for(Report r : reports) {
            if(r.getStatus() != status) {
                continue;
            }

            ShelterUser user = userService.findUserByTelegramId(r.getUserId());
            list.add(new ReportResponse(user, r));
            r.setStatus(ReportStatus.IN_PROGRESS);
            repository.save(r);
        }
        return list;
    }

    private boolean isWrongReport(Report r) {
        boolean hasPhoto = r.getPhoto() != null;
        boolean hasRation = r.getRation() != null;
        boolean hasHealth = r.getHealth() != null;
        boolean hasBehavior = r.getBehavior() != null;

        return !hasPhoto || !hasRation || !hasHealth || !hasBehavior;
    }

    @Override
    public List<ReportResponse> findWrongReports() {
        List<Report> reports = repository.findAll();
        List<ReportResponse> list = new ArrayList<>();
        for(Report r : reports) {
            if(r.getStatus() == ReportStatus.PROCESSED || !isWrongReport(r)) {
                continue;
            }

            ShelterUser user = userService.findUserByTelegramId(r.getUserId());
            list.add(new ReportResponse(user, r));


        }
        return list;
    }

    @Override
    public byte[] getReportPhoto(Long reportId) {
        Optional<Report> report = repository.findById(reportId);
        if(report.isPresent()) {
            byte[] result = report.get().getPhoto();
            if(result != null) {
                return result;
            }
        }
        return new byte[0];
    }

    @Override
    public void sendMessageToUser(Long userId, String text) {
        ShelterUser user = userService.findUserById(userId);
        SendMessage message = new SendMessage(user.getTelegramId(), text);
        telegramBot.execute(message);
    }

}
