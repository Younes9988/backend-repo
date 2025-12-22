package com.example.msemprunt.presentation;


import com.example.msemprunt.messaging.NotificationProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationTestController {

    private final NotificationProducer producer;

    public NotificationTestController(NotificationProducer producer) {
        this.producer = producer;
    }

    @GetMapping("/test/notify")
    public String testNotification() {
        producer.sendTestEmail();
        return "Message sent to Artemis";
    }
}