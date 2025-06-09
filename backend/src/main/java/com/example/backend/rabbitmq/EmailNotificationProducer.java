package com.example.backend.rabbitmq;

import com.example.backend.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationProducer {

    private final RabbitTemplate rabbitTemplate;

    public EmailNotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmail(String message) {
        rabbitTemplate.convertAndSend(RabbitConfig.EMAIL_QUEUE, message);
    }
}
