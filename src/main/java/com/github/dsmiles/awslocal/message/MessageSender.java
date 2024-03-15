package com.github.dsmiles.awslocal.message;

import com.github.dsmiles.awslocal.message.Message;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {

    private final SqsTemplate sqsTemplate;

    public MessageSender(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    public void publish(String queueName, Message message) {
        this.sqsTemplate.send(queueName, message);
    }
}
