package com.shopverse.shopverseapi.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BatchJobRunner implements CommandLineRunner {

    private final JobLauncher jobLauncher;
    private final Job importCatalogJob;

    public BatchJobRunner(JobLauncher jobLauncher, Job importCatalogJob) {
        this.jobLauncher = jobLauncher;
        this.importCatalogJob = importCatalogJob;
    }

    @Override
    public void run(String... args) throws Exception {
        JobParameters parameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        JobExecution execution = jobLauncher.run(importCatalogJob, parameters);
        System.out.println("🔄 Job Status: " + execution.getStatus());
    }
}
