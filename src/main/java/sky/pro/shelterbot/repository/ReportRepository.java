package sky.pro.shelterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.shelterbot.model.Report;

import java.time.LocalDate;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long>  {

    Report getReportByParentIdAndDate(Long parentId, LocalDate date);

    List<Report> findAllByParentId(Long parentId);

}
