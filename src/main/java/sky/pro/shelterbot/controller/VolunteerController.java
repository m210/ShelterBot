package sky.pro.shelterbot.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sky.pro.shelterbot.UserNotFoundException;
import sky.pro.shelterbot.model.ParentUser;
import sky.pro.shelterbot.model.Report;
import sky.pro.shelterbot.model.ReportStatus;
import sky.pro.shelterbot.service.ReportService;
import sky.pro.shelterbot.service.UserService;

import java.util.List;

@RestController
@RequestMapping("volunteer")
public class VolunteerController {

    private final ReportService reportService;
    private final UserService userService;

    public VolunteerController(ReportService reportService, UserService userService) {
        this.reportService = reportService;
        this.userService = userService;
    }

    @GetMapping("findParentById")
    public ResponseEntity<ParentUser> findParentById(long parentId) {
        ParentUser user = userService.findParentById(parentId);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("registerUserAsParent")
    public ResponseEntity<ParentUser> registerUserAsParent(long userId) {
        ParentUser user = userService.findParentByUserId(userId);
        if(user != null) {
            return ResponseEntity.ok(user);
        }

        user = userService.registerAsParent(userId);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        reportService.sendMessageToUser(userId, "Поздравляем, Вы были зарегистрированы как усыновитель. Теперь Вам необходимо отправлять отчет каждый день в течении испытательного срока 3 месяца.");
        return ResponseEntity.ok(user);
    }

    @GetMapping("findAllParents")
    public ResponseEntity<List<ParentUser>> findAllUsers() {
        List<ParentUser> list = userService.findAllParents();
        return ResponseEntity.ok(list);
    }

    @GetMapping("findReports")
    public ResponseEntity<List<Report>> findReports(ReportStatus status) {
        List<Report> list = reportService.findReportsWithStatus(status);
        return ResponseEntity.ok(list);
    }

    @GetMapping("findAllReportsByParentId")
    public ResponseEntity<List<Report>> findAllReportsByParentId(ReportStatus status, long parentId) {
        return ResponseEntity.ok(reportService.findAllReportsByParentId(status, parentId));
    }

    @GetMapping("setReportStatus")
    public ResponseEntity<Report> setReportStatus(long reportId, ReportStatus status) {
        return ResponseEntity.ok(reportService.setReportStatus(reportId, status));
    }

    @GetMapping("getReportPhoto")
    public ResponseEntity<byte[]> getReportPhoto(Long reportId) {
        byte[] data = reportService.getReportPhoto(reportId);
        if(data.length == 0) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(data.length);
        return ResponseEntity.ok().headers(headers).body(data);
    }

    @GetMapping("sendMessageToUser")
    public ResponseEntity<String> sendMessageToUser(Long userId, String message) {
        try {
            reportService.sendMessageToUser(userId, message);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("The message is sent");
    }

    //
//    @GetMapping("findReportsInProgress")
//    public ResponseEntity<List<ReportResponse>> findReportsInProgress() {
//        List<ReportResponse> list = reportService.findReportsWithStatus(ReportStatus.IN_PROGRESS);
//        return ResponseEntity.ok(list);
//    }

//    @GetMapping("findAllReports")
//    public ResponseEntity<List<Report>> findAllReports() {
//        return ResponseEntity.ok(reportService.findAllReports());
//    }

//    @GetMapping("findWrongReports")
//    public ResponseEntity<List<ReportResponse>> findWrongReports() {
//        List<ReportResponse> list = reportService.findWrongReports();
//        return ResponseEntity.ok(list);
//    }

}
