package com.github.dsmiles.awslocal.message;

import com.github.dsmiles.awslocal.properties.ApplicationProperties;
import com.github.dsmiles.awslocal.storage.StorageService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
public class MessageController {

    private final MessageSender messageSender;
    private final StorageService storageService;
    private final ApplicationProperties applicationProperties;

    MessageController(MessageSender messageSender, StorageService storageService, ApplicationProperties applicationProperties) {
        this.messageSender = messageSender;
        this.storageService = storageService;
        this.applicationProperties = applicationProperties;
    }

    @PostMapping("/api/messages")
    public Map<String, String> create(@RequestBody Message message) {
        messageSender.publish(applicationProperties.queue(), message);
        return Map.of("uuid", message.uuid().toString());
    }

    @GetMapping("/api/messages/{uuid}")
    public Map<String, String> get(@PathVariable String uuid) throws IOException {
        String content = storageService.downloadAsString(applicationProperties.bucket(), uuid);
        return Map.of("uuid", uuid, "content", content);
    }
}
