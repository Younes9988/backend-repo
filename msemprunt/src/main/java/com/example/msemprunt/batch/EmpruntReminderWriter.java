package com.example.msemprunt.batch;

import com.example.msemprunt.batch.dto.EmpruntNotification;
import com.example.msemprunt.dao.EmpruntRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class EmpruntReminderWriter
        implements ItemWriter<EmpruntNotification> {

    private final JmsTemplate jmsTemplate;
    private final EmpruntRepository empruntRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public EmpruntReminderWriter(
            JmsTemplate jmsTemplate,
            EmpruntRepository empruntRepository
    ) {
        this.jmsTemplate = jmsTemplate;
        this.empruntRepository = empruntRepository;
    }

    @Override
    public void write(Chunk<? extends EmpruntNotification> chunk) throws Exception {

        for (EmpruntNotification item : chunk) {

            // 1️⃣ Send JMS
            String json = objectMapper.writeValueAsString(
                    item.getNotification()
            );

            jmsTemplate.convertAndSend(
                    "notification.email.test",
                    json
            );

            // 2️⃣ Mark reminder as sent
            item.getEmprunt().setRappelJ1Envoye(true);
            item.getEmprunt().setDateRappelJ1(LocalDate.now());

            empruntRepository.save(item.getEmprunt());
        }
    }


}