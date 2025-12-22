package com.example.msnotification.dto;

import lombok.Data;

@Data
public class EmailNotificationEvent {

    private String email;
    private String subject;
    private String message;
}