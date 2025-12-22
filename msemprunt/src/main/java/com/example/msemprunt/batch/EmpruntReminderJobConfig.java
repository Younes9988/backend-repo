package com.example.msemprunt.batch;

import com.example.msemprunt.batch.dto.EmpruntNotification;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class EmpruntReminderJobConfig {

    @Bean
    public Step empruntReminderStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<Emprunt> reader,
            ItemProcessor<Emprunt, EmpruntNotification> processor,
            ItemWriter<EmpruntNotification> writer
    ) {
        return new StepBuilder("empruntReminderStep", jobRepository)
                .<Emprunt, EmpruntNotification>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job empruntReminderJob(
            JobRepository jobRepository,
            Step empruntReminderStep
    ) {
        return new JobBuilder("empruntReminderJob", jobRepository)
                .start(empruntReminderStep)
                .build();
    }
}