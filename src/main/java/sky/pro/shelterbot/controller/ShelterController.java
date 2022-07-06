package sky.pro.shelterbot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("volunteer")
public class ShelterController {

    public ShelterController() {

    }

    @GetMapping
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome");
    }
}

