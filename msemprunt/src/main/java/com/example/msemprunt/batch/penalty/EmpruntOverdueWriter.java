package com.example.msemprunt.batch.penalty;

import com.example.msemprunt.client.UtilisateurClient;
import com.example.msemprunt.dao.EmpruntRepository;
import com.example.msemprunt.dto.UtilisateurDTO;
import com.example.msemprunt.model.Emprunt;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
@RequiredArgsConstructor
public class EmpruntOverdueWriter implements ItemWriter<PenaltyResult> {

    private final EmpruntRepository empruntRepository;
    private final UtilisateurClient utilisateurClient;
    private final JmsTemplate jmsTemplate;

    private static final String OVERDUE_QUEUE = "notification.email.overdue";

    @Override
    public void write(Chunk<? extends PenaltyResult> items) {

        LocalDate today = LocalDate.now();

        for (PenaltyResult result : items) {

            Emprunt emprunt = result.getEmprunt();

            // Apply daily penalty
            emprunt.setPenalite(
                    emprunt.getPenalite() + result.getPenaltyToAdd()
            );
            emprunt.setDateDernierePenalite(today);

            //  Send overdue notification (only once)
            if (result.isSendOverdueNotification()) {

                UtilisateurDTO user =
                        utilisateurClient.getUtilisateurById(
                                emprunt.getLecteurId()
                        );

                OverdueNotificationEvent event =
                        new OverdueNotificationEvent(
                                user.getEmail(),
                                "Overdue Book Reminder",
                                "Your borrowed book is overdue. Daily penalties are being applied."
                        );

                jmsTemplate.convertAndSend(OVERDUE_QUEUE, event);

                emprunt.setNotificationRetardEnvoyee(true);
            }

            empruntRepository.save(emprunt);
        }
    }
}
