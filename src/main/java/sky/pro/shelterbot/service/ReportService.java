package sky.pro.shelterbot.service;

import org.springframework.stereotype.Service;
import sky.pro.shelterbot.model.Report;
import sky.pro.shelterbot.repository.ReportRepository;

import java.time.LocalDate;

@Service
public class ReportService {

    private final ReportRepository repository;

    public ReportService(ReportRepository repository) {
        this.repository = repository;
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

    public void addPhoto(byte[] data, Long fromUser, LocalDate date) {
        Report report = getReport(fromUser, date);

        report.setPhoto(data);
        repository.save(report);
    }

    public void addRation(String text, Long fromUser, LocalDate date) {
        Report report = getReport(fromUser, date);

        report.setRation(text);
        repository.save(report);
    }

    public void addHealth(String text, Long fromUser, LocalDate date) {
        Report report = getReport(fromUser, date);

        report.setHealth(text);
        repository.save(report);
    }

    public void addBehavior(String text, Long fromUser, LocalDate date) {
        Report report = getReport(fromUser, date);

        report.setBehavior(text);
        repository.save(report);
    }
}
