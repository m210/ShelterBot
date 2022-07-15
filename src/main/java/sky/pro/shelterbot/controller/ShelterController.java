package sky.pro.shelterbot.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sky.pro.shelterbot.UserNotFoundException;
import sky.pro.shelterbot.model.BotResponse;
import sky.pro.shelterbot.model.Report;
import sky.pro.shelterbot.model.ReportResponse;
import sky.pro.shelterbot.model.ReportStatus;
import sky.pro.shelterbot.service.BotResponseService;
import sky.pro.shelterbot.service.ReportService;

import java.util.List;

@RestController
@RequestMapping("volunteer")
public class ShelterController {

    private final BotResponseService shelterService;
    private final ReportService reportService;

    public ShelterController(BotResponseService shelterService, ReportService reportService) {
        this.shelterService = shelterService;
        this.reportService = reportService;
    }

    @GetMapping("test")
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome");
    }

    @GetMapping("findNewReports")
    public ResponseEntity<List<ReportResponse>> findNewReports() {
        List<ReportResponse> list = reportService.findReportsWithStatus(ReportStatus.NEW);
        return ResponseEntity.ok(list);
    }

    @GetMapping("findReportsInProgress")
    public ResponseEntity<List<ReportResponse>> findReportsInProgress() {
        List<ReportResponse> list = reportService.findReportsWithStatus(ReportStatus.IN_PROGRESS);
        return ResponseEntity.ok(list);
    }

    @GetMapping("findWrongReports")
    public ResponseEntity<List<ReportResponse>> findWrongReports() {
        List<ReportResponse> list = reportService.findWrongReports();
        return ResponseEntity.ok(list);
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

    @GetMapping("test/bot_response")
    public ResponseEntity<String> getResponseMessage(String message) {
        String response = shelterService.getResponseMessage(message);
        if(response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("test/bot_response")
    public ResponseEntity<BotResponse> getResponseMessage(String keyMessage, String responseMessage) {
        BotResponse response = shelterService.saveResponseMessage(keyMessage, responseMessage);
        if(response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}

