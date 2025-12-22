package com.example.msemprunt.batch;


import com.example.msemprunt.dao.EmpruntRepository;
import com.example.msemprunt.model.Emprunt;
import com.example.msemprunt.model.StatutEmprunt;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Configuration
public class EmpruntReminderReaderConfig {

    @Bean
    public RepositoryItemReader<Emprunt> empruntReminderReader(
            EmpruntRepository empruntRepository
    ) {

        RepositoryItemReader<Emprunt> reader = new RepositoryItemReader<>();

        reader.setRepository(empruntRepository);
        reader.setMethodName(
                "findByStatutAndDateRetourPrevueAndRappelJ1EnvoyeFalse"
        );

        reader.setArguments(
                List.of(
                        StatutEmprunt.EN_COURS,
                        LocalDate.now().plusDays(1)
                )
        );

        reader.setPageSize(10);

        reader.setSort(
                Collections.singletonMap("empruntId", Sort.Direction.ASC)
        );

        return reader;
    }
}