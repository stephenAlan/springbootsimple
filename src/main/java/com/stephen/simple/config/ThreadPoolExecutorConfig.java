package com.stephen.simple.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Created by loki
 */
@Configuration
public class ThreadPoolExecutorConfig {

  @Bean(name = "threadPoolExecutor")
  public ThreadPoolExecutor getThreadPoolExecutor() {
      return createThreadPoolExecutor();
  }


    public static ThreadPoolExecutor createThreadPoolExecutor() {
        return new ThreadPoolExecutor(3, 10,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(100, true));
    }
}