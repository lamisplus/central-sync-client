package org.lamisplus.modules.central.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * By default, Spring Boot will use just a single thread for all
 * scheduled tasks to run on. This is not ideal, because these tasks will be
 * blocking. Instead, we will configure the scheduler to run each scheduled tasks on a
 * separate thread (if there is enough threads available)
 *
 * Configures the scheduler to allow multiple concurrent pools.
 * Prevents blocking
 */
@Configuration
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@RequiredArgsConstructor
@Slf4j
public class SchedulerConfig  implements SchedulingConfigurer {

    /**
     * Sets the pool size
     */
    private final int POOL_SIZE = 10;

    /**
     * Configures the scheduler to allow multiple pools
     * @param scheduledTaskRegistrar
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

        threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
        threadPoolTaskScheduler.setThreadNamePrefix("central-sync-");
        threadPoolTaskScheduler.initialize();

        scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);

    }
}
