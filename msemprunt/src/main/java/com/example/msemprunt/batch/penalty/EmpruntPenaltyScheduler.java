package com.example.msemprunt.batch.penalty;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class EmpruntPenaltyScheduler {

    private final JobLauncher jobLauncher;
    private final Job empruntPenaltyJob;

    public EmpruntPenaltyScheduler(
            JobLauncher jobLauncher,
            Job empruntPenaltyJob
    ) {
        this.jobLauncher = jobLauncher;
        this.empruntPenaltyJob = empruntPenaltyJob;
    }

    //  Runs every day at 03:00 AM
    @Scheduled(cron = "0 0 3 * * *")
    public void runPenaltyJob() throws Exception {

        jobLauncher.run(
                empruntPenaltyJob,
                new JobParametersBuilder()
                        .addLong("timestamp", System.currentTimeMillis())
                        .toJobParameters()
        );
    }
}
