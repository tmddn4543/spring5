package com.nautestech.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulerConfiguration {
	@Bean
	public TaskScheduler poolScheduler() {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(Runtime.getRuntime().availableProcessors() * 2);
		threadPoolTaskScheduler.setThreadNamePrefix("jeong-pro-threadpool");
		return threadPoolTaskScheduler;
	}

}
