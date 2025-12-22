package com.example.msemprunt.batch.dto;
import com.example.msemprunt.model.Emprunt;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmpruntNotification {
    private Emprunt emprunt;
    private NotificationMessage notification;
}