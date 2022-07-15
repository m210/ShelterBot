package sky.pro.shelterbot.model;

import java.time.LocalDate;

public class ReportResponse {

    private Long reportId;
    private boolean hasPhoto;
    private Long userId;
    private String ration;
    private String health;
    private String behavior;
    private LocalDate date;

    public ReportResponse(ShelterUser user, Report report) {
        this.reportId = report.getId();
        this.hasPhoto = report.getPhoto() != null;
        this.ration = report.getRation();
        this.health = report.getHealth();
        this.behavior = report.getBehavior();
        this.userId = user.getId();
        this.date = report.getDate();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public boolean isHasPhoto() {
        return hasPhoto;
    }

    public void setHasPhoto(boolean hasPhoto) {
        this.hasPhoto = hasPhoto;
    }

    public String getRation() {
        return ration;
    }

    public void setRation(String ration) {
        this.ration = ration;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }
}
