package sky.pro.shelterbot.repository;

import okhttp3.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.shelterbot.model.CallVolunteer;
import sky.pro.shelterbot.model.ReportStatus;

import java.util.List;


/**
 * Репозиторий, в котором хранятся пользователи, требующие волонтера
 */
public interface CallVolunteerRepository extends JpaRepository<CallVolunteer, Long>  {

    List<CallVolunteer> findAllByStatusEquals(ReportStatus status);

}
