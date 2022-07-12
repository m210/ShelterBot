package sky.pro.shelterbot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import sky.pro.shelterbot.controller.KnowledgeController;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KnowledgeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private KnowledgeController knowledgeController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(knowledgeController).isNotNull();
    }

    @Test
    public void getKnowledgeByIdTest() throws Exception {

        Assertions.
                assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/idRead", String.class))
                .isNotNull();
    }

    @Test
    public void getAllKnowledgeTest() throws Exception {

        Assertions.
                assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/all", String.class))
                .isNotNull();
    }
}
