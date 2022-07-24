package sky.pro.shelterbot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import sky.pro.shelterbot.message.MessageConstants;
import sky.pro.shelterbot.model.*;
import sky.pro.shelterbot.repository.ReportRepository;
import sky.pro.shelterbot.service.ReportService;
import sky.pro.shelterbot.service.UserService;

import java.time.LocalDate;
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

    private Report getReport(Long telegramId, LocalDate date) {
        ParentUser parent = userService.findParentByTelegramId(telegramId);
        Report report = repository.getReportByParentIdAndDate(parent.getId(), date);
        if(report == null) {
            report = new Report();
            report.setParentId(parent.getId());
            report.setDate(date);
        }

        return report;
    }

    @Override
    public void addPhoto(byte[] data, Long telegramId, LocalDate date) {
        Report report = getReport(telegramId, date);

        report.setPhoto(data);
        repository.save(report);
    }

    @Override
    public void addRation(String text, Long telegramId, LocalDate date) {
        Report report = getReport(telegramId, date);

        report.setRation(text);
        repository.save(report);
    }

    @Override
    public void addHealth(String text, Long telegramId, LocalDate date) {
        Report report = getReport(telegramId, date);

        report.setHealth(text);
        repository.save(report);
    }

    @Override
    public void addBehavior(String text, Long telegramId, LocalDate date) {
        Report report = getReport(telegramId, date);

        report.setBehavior(text);
        repository.save(report);
    }

    @Override
    public Report findReportById(long reportId) {
        Optional<Report> report = repository.findById(reportId);
        return report.orElse(null);
    }

    @Override
    public List<Report> findAllReports() {
        return repository.findAll();
    }

    @Override
    public List<Report> findReportsWithStatus(ReportStatus status) {
        List<Report> reports = repository.findAll();
        reports.removeIf(a -> a.getStatus() != status);
        return reports;
    }

    private boolean isWrongReport(Report r) {
        boolean hasPhoto = r.getPhoto() != null;
        boolean hasRation = r.getRation() != null;
        boolean hasHealth = r.getHealth() != null;
        boolean hasBehavior = r.getBehavior() != null;

        return !hasPhoto || !hasRation || !hasHealth || !hasBehavior;
    }

    @Override
    public List<Report> findWrongReports() {
        List<Report> reports = repository.findAll();
        reports.removeIf(a -> !isWrongReport(a));
        return reports;
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
        SendMessage message = new SendMessage(user.getTelegramId(), MessageConstants.VOLUNTEER_MESSAGE + text);
        telegramBot.execute(message);
    }

    @Override
    public boolean isReportingAllowed(Long telegramId) {
        return userService.findParentByTelegramId(telegramId) != null;
    }

    @Override
    public List<Report> findAllReportsByParentId(ReportStatus status, long parentId) {
        List<Report> reports = repository.findAllByParentId(parentId);
        reports.removeIf(a -> a.getStatus() != status);
        return reports;
    }

    @Override
    public Report setReportStatus(long reportId, ReportStatus status) {
        Report report = findReportById(reportId);
        report.setStatus(status);
        repository.save(report);
        return report;
    }

}
