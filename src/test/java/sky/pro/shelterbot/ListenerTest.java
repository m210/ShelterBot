package sky.pro.shelterbot;

import com.pengrad.telegrambot.TelegramBot;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListenerTest {

    public static String token() {
        String token;
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("local.properties"));
            token = properties.getProperty("TEST_TOKEN");
        } catch (Exception e) {
            token = System.getenv("TEST_TOKEN");
        }
        return token;
    }

    private TelegramBot bot;

    @Before("value")
    public void initBot() {
        bot = new TelegramBot.Builder(token()).debug().build();
    }

    @Test
    public void telegramErrorLogging() throws InterruptedException {
        withLatch(latch -> {
            bot = new TelegramBot("12312312:token");
            bot.setUpdatesListener(updates -> 0);
            System.out.println("get log");
            latch.countDown();
        });
    }

    private void withLatch(Consumer<CountDownLatch> body) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        body.accept(latch);
        assertTrue(latch.await(3, TimeUnit.SECONDS));
        bot.removeGetUpdatesListener();
    }
}
