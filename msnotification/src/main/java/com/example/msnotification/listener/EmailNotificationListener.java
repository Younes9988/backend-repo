package com.example.msnotification.listener;

import com.example.msnotification.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmailNotificationListener {

    private final EmailService emailService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public EmailNotificationListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @JmsListener(destination = "notification.email.test")
    public void receive(String message) throws Exception {

        Map<String, String> payload =
                objectMapper.readValue(message, Map.class);

        String email = payload.get("email");
        String subject = payload.get("subject");
        String body = payload.get("message");

        emailService.sendEmail(email, subject, body);

        System.out.println("✅ Email sent to " + email);
    }
}
