package com.example.backend.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    @KafkaListener(topics = "order-events", groupId = "backend-group")
    public void listen(String message) {
        System.out.println("📝 Order event received: " + message);
    }
}

