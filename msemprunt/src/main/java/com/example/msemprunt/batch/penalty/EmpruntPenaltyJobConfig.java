package com.example.msemprunt.batch.penalty;


import com.example.msemprunt.model.Emprunt;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class EmpruntPenaltyJobConfig {

    @Bean
    public Step empruntPenaltyStep(
            @Qualifier("overdueEmpruntReader") ItemReader<Emprunt> reader,
            ItemProcessor<Emprunt, PenaltyResult> processor,
            ItemWriter<PenaltyResult> writer,
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager
    ) {

        return new StepBuilder("empruntPenaltyStep", jobRepository)
                .<Emprunt, PenaltyResult>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job empruntPenaltyJob(
            JobRepository jobRepository,
            Step empruntPenaltyStep
    ) {
        return new JobBuilder("empruntPenaltyJob", jobRepository)
                .start(empruntPenaltyStep)
                .build();
    }
}