package sky.pro.shelterbot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class ParentUser {

    @Id
    @GeneratedValue
    private Long id;
    private Long shelterUserId;
    private LocalDate lastReportDate;
    private LocalDate probation;

    public ParentUser() {
        lastReportDate = LocalDate.now();
        probation = lastReportDate.plusDays(30);
    }

    public LocalDate getProbation() {
        return probation;
    }

    public void setProbation(LocalDate probation) {
        this.probation = probation;
    }

    public Long getShelterUserId() {
        return shelterUserId;
    }

    public void setShelterUserId(Long shelterUserId) {
        this.shelterUserId = shelterUserId;
    }

    public LocalDate getLastReportDate() {
        return lastReportDate;
    }

    public void setLastReportDate(LocalDate lastReportDate) {
        this.lastReportDate = lastReportDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
