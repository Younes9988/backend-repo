package com.example.msemprunt.batch.penalty;

import com.example.msemprunt.dao.EmpruntRepository;
import com.example.msemprunt.model.Emprunt;
import com.example.msemprunt.model.StatutEmprunt;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Configuration
public class EmpruntOverdueReaderConfig {

    @Bean
    public RepositoryItemReader<Emprunt> overdueEmpruntReader(
            EmpruntRepository empruntRepository
    ) {

        RepositoryItemReader<Emprunt> reader = new RepositoryItemReader<>();

        reader.setRepository(empruntRepository);
        reader.setMethodName("findByStatutAndDateRetourPrevueBefore");

        reader.setArguments(List.of(
                StatutEmprunt.EN_COURS,
                LocalDate.now()
        ));

        reader.setPageSize(10);

        reader.setSort(
                Map.of("empruntId", Sort.Direction.ASC)
        );

        return reader;
    }
}