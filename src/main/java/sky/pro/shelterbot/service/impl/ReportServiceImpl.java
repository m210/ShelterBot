package sky.pro.shelterbot.service.impl;

import org.springframework.stereotype.Service;
import sky.pro.shelterbot.model.Report;
import sky.pro.shelterbot.repository.ReportRepository;
import sky.pro.shelterbot.service.ReportService;

import java.time.LocalDate;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository repository;

    public ReportServiceImpl(ReportRepository repository) {
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
}
