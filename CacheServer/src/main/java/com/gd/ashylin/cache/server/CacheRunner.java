package com.gd.ashylin.cache.server;

import com.gd.ashylin.cache.server.job.CacheJobExecutor;
import com.gd.ashylin.cache.server.stat.Stat;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Alexander Shylin
 */
public class CacheRunner {

    private CacheJobExecutor jobExecutor;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
        CacheRunner cacheRunner = context.getBean(CacheRunner.class);
        new Stat();
        cacheRunner.jobExecutor.init();
    }

    public void setJobExecutor(CacheJobExecutor jobExecutor) {
        this.jobExecutor = jobExecutor;
    }
}
