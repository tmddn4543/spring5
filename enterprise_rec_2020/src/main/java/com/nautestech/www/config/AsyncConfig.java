package com.nautestech.www.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ConfigurationProperties(prefix = "thread")
@EnableAsync
public class AsyncConfig {
	private int poolsize;
	private int maxooolsize;
	private int queuecapacity;
	 
    @Bean(name = "tcpExecutor")
    public Executor fooExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(getPoolsize());
        taskExecutor.setMaxPoolSize(getMaxooolsize());
        taskExecutor.setQueueCapacity(getQueuecapacity());
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setThreadNamePrefix("tcpExecutor-");
        taskExecutor.initialize();
        return taskExecutor;
    }

	public int getPoolsize() {
		return poolsize;
	}

	public void setPoolsize(int poolsize) {
		this.poolsize = poolsize;
	}

	public int getMaxooolsize() {
		return maxooolsize;
	}

	public void setMaxooolsize(int maxooolsize) {
		this.maxooolsize = maxooolsize;
	}

	public int getQueuecapacity() {
		return queuecapacity;
	}

	public void setQueuecapacity(int queuecapacity) {
		this.queuecapacity = queuecapacity;
	}

}
