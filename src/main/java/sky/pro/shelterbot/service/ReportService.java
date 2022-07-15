package sky.pro.shelterbot.service;

import sky.pro.shelterbot.model.ReportResponse;
import sky.pro.shelterbot.model.ReportStatus;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    void addPhoto(byte[] data, Long fromUser, LocalDate date);

    void addRation(String text, Long fromUser, LocalDate date);

    void addHealth(String text, Long fromUser, LocalDate date);

    void addBehavior(String text, Long fromUser, LocalDate date);

    List<ReportResponse> findReportsWithStatus(ReportStatus status);

    List<ReportResponse> findWrongReports();

    byte[] getReportPhoto(Long reportId);

    void sendMessageToUser(Long userId, String message);
}
