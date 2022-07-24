package sky.pro.shelterbot.service;

import sky.pro.shelterbot.model.Report;
import sky.pro.shelterbot.model.ReportStatus;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    void addPhoto(byte[] data, Long fromUser, LocalDate date);

    void addRation(String text, Long fromUser, LocalDate date);

    void addHealth(String text, Long fromUser, LocalDate date);

    void addBehavior(String text, Long fromUser, LocalDate date);

    Report findReportById(long reportId);

    List<Report> findAllReports();

    List<Report> findReportsWithStatus(ReportStatus status);

    List<Report> findWrongReports();

    byte[] getReportPhoto(Long reportId);

    void sendMessageToUser(Long userId, String message);

    boolean isReportingAllowed(Long telegramId);

    List<Report> findAllReportsByParentId(ReportStatus status, long parentId);

    Report setReportStatus(long reportId, ReportStatus status);
}
