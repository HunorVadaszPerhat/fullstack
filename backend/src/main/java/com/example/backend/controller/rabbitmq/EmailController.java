package com.example.backend.controller.rabbitmq;

import com.example.backend.rabbitmq.EmailNotificationProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailNotificationProducer producer;

    public EmailController(EmailNotificationProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/send")
    public String sendEmail(@RequestBody String message) {
        producer.sendEmail(message);
        return "Message sent to email.queue";
    }
}
