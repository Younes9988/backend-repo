package com.example.msemprunt.batch.penalty;

import com.example.msemprunt.model.Emprunt;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class EmpruntOverdueProcessor
        implements ItemProcessor<Emprunt, PenaltyResult> {

    private static final double DAILY_FINE = 5.0;

    @Override
    public PenaltyResult process(Emprunt emprunt) {

        LocalDate today = LocalDate.now();

        // Already penalized today → skip
        if (today.equals(emprunt.getDateDernierePenalite())) {
            return null;
        }

        long overdueDays = ChronoUnit.DAYS.between(
                emprunt.getDateRetourPrevue(),
                today
        );

        if (overdueDays <= 0) {
            return null;
        }

        boolean sendNotification =
                Boolean.FALSE.equals(emprunt.getNotificationRetardEnvoyee());

        return new PenaltyResult(
                emprunt,
                sendNotification,
                DAILY_FINE
        );
    }
}
