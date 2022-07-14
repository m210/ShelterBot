package sky.pro.shelterbot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import sky.pro.shelterbot.controller.ShelterController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BotResponseTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ShelterController shelterController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(shelterController).isNotNull();
    }

    @Test
    public void getResponseMessageTest() throws Exception {

        Assertions.
                assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/test/bot_response", String.class))
                .isNotNull();
    }

}
