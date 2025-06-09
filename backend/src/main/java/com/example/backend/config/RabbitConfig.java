package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue; // ✅ correct for RabbitMQ
@Configuration
public class RabbitConfig {

    public static final String EMAIL_QUEUE = "email.queue";

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true); // durable = true
    }
}
