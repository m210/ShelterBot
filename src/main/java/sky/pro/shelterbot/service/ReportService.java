package sky.pro.shelterbot.service;

import org.springframework.stereotype.Service;
import sky.pro.shelterbot.model.Report;
import sky.pro.shelterbot.model.ReportField;
import sky.pro.shelterbot.repository.ReportRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    private final ReportRepository repository;

    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    public Report parseReport(String message, Long fromUser, LocalDate date) {
        Report report = repository.getReportByUserIdAndAndDate(fromUser, date);
        if(report == null) {
            report = new Report();
            report.setUserId(fromUser);
            report.setDate(date);
        }

        List<Integer> indexes = new ArrayList<>();
        if(message == null) {
            return report;
        }

        String mess = message.toUpperCase();
        indexes.add(mess.indexOf(ReportField.RATION.getField()));
        indexes.add(mess.indexOf(ReportField.HEALTH.getField()));
        indexes.add(mess.indexOf(ReportField.BEHAVIOR.getField()));
        indexes.removeIf(a -> a == -1);
        indexes.sort(Integer::compare);

        if(indexes.size() == 0) {
            return report;
        }

        String handle = message;
        for(int i = 0, firstIndex = indexes.get(i++); !handle.isEmpty(); i++) {
            int lastIndex;
            if(i < indexes.size()) {
                lastIndex = indexes.get(i) - firstIndex;
                handlePartition(report, handle.substring(0, lastIndex));
            } else {
                lastIndex = handle.length();
                handlePartition(report, handle);
            }

            handle = handle.substring(lastIndex);
            firstIndex = lastIndex - firstIndex;
        }

        repository.save(report);
        return report;
    }

    private void handlePartition(Report report, String part) {
        String[] values = part.split(":");
        if(values.length < 2) {
            return;
        }

        String key = values[0].trim().toUpperCase();
        String message = values[1].trim();

        ReportField field = ReportField.getValue(key);
        if(field == null) {
            return;
        }

        switch(field) {
            case RATION:
                report.setRation(message);
                break;
            case HEALTH:
                report.setHealth(message);
                break;
            case BEHAVIOR:
                report.setBehavior(message);
                break;
        }
    }
}
