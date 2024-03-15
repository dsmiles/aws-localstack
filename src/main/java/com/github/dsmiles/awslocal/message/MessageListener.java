package com.github.dsmiles.awslocal.message;

import com.github.dsmiles.awslocal.properties.ApplicationProperties;
import com.github.dsmiles.awslocal.storage.StorageService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Service
public class MessageListener {

    private final StorageService storageService;
    private final ApplicationProperties applicationProperties;

    public MessageListener(StorageService storageService, ApplicationProperties applicationProperties) {
        this.storageService = storageService;
        this.applicationProperties = applicationProperties;
    }

    @SqsListener(queueNames = { "${app.queue}" })
    public void handle(Message message) {
        String bucketName = this.applicationProperties.bucket();
        String key = message.uuid().toString();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(message.content().getBytes(StandardCharsets.UTF_8));
        this.storageService.upload(bucketName, key, inputStream);
    }
}
