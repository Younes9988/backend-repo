package com.example.msemprunt.batch.penalty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OverdueNotificationEvent {

    private String email;
    private String subject;
    private String message;
}