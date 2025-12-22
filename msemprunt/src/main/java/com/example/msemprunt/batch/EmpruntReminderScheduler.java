package com.example.msemprunt.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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

    // ⏰ Every day at 08:00
    //@Scheduled(cron = "0 0 8 * * *")
    @Scheduled(cron = "0 0 2 * * *")
    public void runDailyReminderJob() throws Exception {

        jobLauncher.run(
                empruntReminderJob,
                new JobParametersBuilder()
                        .addLong("timestamp", System.currentTimeMillis())
                        .toJobParameters()
        );
    }
}
