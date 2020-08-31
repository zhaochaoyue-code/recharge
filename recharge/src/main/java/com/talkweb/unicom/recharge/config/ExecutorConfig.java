package com.talkweb.unicom.recharge.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

//@Configuration
public class ExecutorConfig implements AsyncConfigurer {
	
	@Bean
	public Executor getAsyncExecutor () {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(50); 
		executor.setMaxPoolSize(200); 
		executor.setQueueCapacity(200); 
		executor.setKeepAliveSeconds(60);  
		
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); 
		executor.initialize();
		
		return executor;
	}
	
}
