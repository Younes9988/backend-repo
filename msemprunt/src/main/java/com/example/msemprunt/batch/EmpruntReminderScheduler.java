package com.example.msemprunt.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;

@Configuration
@EnableScheduling
public class EmpruntReminderScheduler {

    private final JobLauncher jobLauncher;
    private final Job empruntReminderJob;

    public EmpruntReminderScheduler(
            JobLauncher jobLauncher,
            Job empruntReminderJob
    ) {
        this.jobLauncher = jobLauncher;
        this.empruntReminderJob = empruntReminderJob;
    }

    // Every day at 02:00
    @Scheduled(cron = "0 0 2 * * *")
    //@Scheduled(cron = "*/30 * * * * *")
    public void runDailyReminderJob() throws Exception {

        jobLauncher.run(
                empruntReminderJob,
                new JobParametersBuilder()
                        .addLocalDate("targetDate", LocalDate.now().plusDays(1))
                        .addLong("timestamp", System.currentTimeMillis())
                        .toJobParameters()
        );
    }
}
