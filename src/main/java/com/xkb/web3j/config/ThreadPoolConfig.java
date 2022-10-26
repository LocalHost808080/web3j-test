package com.xkb.web3j.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ThreadPoolConfig {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolConfig.class);

    @Value("${async.executor.thread.core_pool_size}")
    private int corePoolSize;

    @Value("${async.executor.thread.max_pool_size}")
    private int maxPoolSize;

    @Value("${async.executor.thread.queue_capacity}")
    private int queueCapacity;

    @Value("${async.executor.thread.name.prefix}")
    private String namePrefix;

    @Value("${async.executor.thread.keep_alive_seconds}")
    private int keepAliveSeconds;

    @Bean("threadPoolTaskExecutor")
    public TaskExecutor taskExecutor() {

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(corePoolSize);          // 设置核心线程数
        taskExecutor.setMaxPoolSize(maxPoolSize);            // 设置最大线程数
        taskExecutor.setQueueCapacity(queueCapacity);        // 设置队列容量
        taskExecutor.setKeepAliveSeconds(keepAliveSeconds);  // 设置线程活跃时间（秒）
        taskExecutor.setThreadNamePrefix(namePrefix);        // 设置默认线程名称
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 设置拒绝策略
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);    // 等待所有任务结束后再关闭线程池

        // logger.info("\n创建一个线程池：" +
        //         "\ncorePoolSize     = [" + corePoolSize + "] " +
        //         "\nmaxPoolSize      = [" + maxPoolSize + "] " +
        //         "\nqueueCapacity    = [" + queueCapacity + "] " +
        //         "\nkeepAliveSeconds = [" + keepAliveSeconds + "] " +
        //         "\nnamePrefix       = [" + namePrefix + "].");

        return taskExecutor;
    }
}
