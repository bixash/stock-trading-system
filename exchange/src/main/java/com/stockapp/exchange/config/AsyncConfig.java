package com.stockapp.exchange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean("AsyncExecutor")
    public Executor asyncExecutor(){
        ThreadPoolTaskExecutor taskExecutor=new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setQueueCapacity(150);
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.setThreadNamePrefix("AsyncThread-");
        taskExecutor.initialize();;
        return taskExecutor;
    }
}







