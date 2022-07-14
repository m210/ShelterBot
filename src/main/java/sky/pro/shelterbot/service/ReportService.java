package sky.pro.shelterbot.service;

import java.time.LocalDate;

public interface ReportService {

    void addPhoto(byte[] data, Long fromUser, LocalDate date);

    void addRation(String text, Long fromUser, LocalDate date);

    void addHealth(String text, Long fromUser, LocalDate date);

    void addBehavior(String text, Long fromUser, LocalDate date);

}
