package sky.pro.shelterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.shelterbot.model.Report;

import java.time.LocalDate;

public interface ReportRepository extends JpaRepository<Report, Long>  {
    Report getReportByUserIdAndAndDate(Long userId, LocalDate date);
}
