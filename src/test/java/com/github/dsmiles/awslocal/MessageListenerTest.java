package com.github.dsmiles.awslocal;

import com.github.dsmiles.awslocal.message.Message;
import com.github.dsmiles.awslocal.message.MessageSender;
import com.github.dsmiles.awslocal.properties.ApplicationProperties;
import com.github.dsmiles.awslocal.storage.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
public class MessageListenerTest extends BaseContainersTest {

    @Autowired
    StorageService storageService;

    @Autowired
    MessageSender messageSender;

    @Autowired
    ApplicationProperties properties;

    @Test
    void shouldHandleMessageSuccessfully() {
        Message message = new Message(UUID.randomUUID(), "Hello World!");
        messageSender.publish(properties.queue(), message);

        await()
            .pollInterval(Duration.ofSeconds(2))
            .atMost(Duration.ofSeconds(10))
            .ignoreExceptions()
            .untilAsserted(() -> {
                String content = storageService.downloadAsString(
                    properties.bucket(),
                    message.uuid().toString()
                );

            assertThat(content).isEqualTo("Hello World!");
        });
    }
}
