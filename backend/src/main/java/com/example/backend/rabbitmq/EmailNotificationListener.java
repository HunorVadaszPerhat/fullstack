package com.example.backend.rabbitmq;

import com.example.backend.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationListener {

    @RabbitListener(queues = RabbitConfig.EMAIL_QUEUE)
    public void handle(String message) {
        System.out.println("📨 Email message received: " + message);
        // You could add your email sending logic here
    }
}

