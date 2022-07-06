package sky.pro.shelterbot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sky.pro.shelterbot.model.BotResponse;
import sky.pro.shelterbot.service.BotResponseService;

@RestController
@RequestMapping("volunteer")
public class ShelterController {

    private final BotResponseService shelterService;

    public ShelterController(BotResponseService shelterService) {
        this.shelterService = shelterService;
    }

    @GetMapping("test")
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome");
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

