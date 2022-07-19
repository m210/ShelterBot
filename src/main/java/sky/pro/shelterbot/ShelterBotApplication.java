package sky.pro.shelterbot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@OpenAPIDefinition
@EnableScheduling
public class ShelterBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShelterBotApplication.class, args);
    }
}
