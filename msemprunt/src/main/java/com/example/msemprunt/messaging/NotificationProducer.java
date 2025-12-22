package com.example.msemprunt.messaging;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    private final JmsTemplate jmsTemplate;

    public NotificationProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendTestEmail() {

        String message = """
        {
          "email": "your_email@gmail.com",
          "subject": "Test from ms-emprunt",
          "message": "This email was sent by ms-emprunt via Artemis 🚀"
        }
        """;

        jmsTemplate.convertAndSend(
                "notification.email.test",
                message
        );
    }
}