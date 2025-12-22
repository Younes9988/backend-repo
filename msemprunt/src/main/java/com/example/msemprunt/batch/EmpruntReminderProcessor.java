package com.example.msemprunt.batch;

import com.example.msemprunt.batch.dto.EmpruntNotification;
import com.example.msemprunt.batch.dto.NotificationMessage;
import com.example.msemprunt.client.UtilisateurClient;
import com.example.msemprunt.dto.UtilisateurDTO;
import com.example.msemprunt.model.Emprunt;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

@Component
public class EmpruntReminderProcessor
        implements ItemProcessor<Emprunt, EmpruntNotification> {

    private final UtilisateurClient utilisateurClient;

    public EmpruntReminderProcessor(UtilisateurClient utilisateurClient) {
        this.utilisateurClient = utilisateurClient;
    }

    @Override
    public EmpruntNotification process(Emprunt emprunt) {

        // 1️Fetch user
        UtilisateurDTO utilisateur =
                utilisateurClient.getUtilisateurById(emprunt.getLecteurId());

        // 2️Days remaining (for message clarity)
        long daysLeft = ChronoUnit.DAYS.between(
                java.time.LocalDate.now(),
                emprunt.getDateRetourPrevue()
        );

        // 3️Build message
        String subject = "📚 Reminder: book due tomorrow";
        String body = String.format(
                "Hello %s %s,\n\n" +
                        "This is a reminder that your borrowed book must be returned in %d day(s).\n" +
                        "Due date: %s\n\n" +
                        "Thank you,\nLibrary Team",
                utilisateur.getPrenom(),
                utilisateur.getNom(),
                daysLeft,
                emprunt.getDateRetourPrevue()
        );

        return new EmpruntNotification(emprunt,
                new NotificationMessage(
                        utilisateur.getEmail(),
                        subject,
                        body
                )
        );
    }
}