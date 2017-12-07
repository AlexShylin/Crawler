package com.gd.ashylin.cache.server.job;

import com.gd.ashylin.cache.server.bo.ValueBO;
import com.gd.ashylin.cache.server.job.cleaner.ExpiredTtlDataCleaner;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author Alexander Shylin
 */
public class CacheJobExecutorImpl implements CacheJobExecutor {

    static final Logger LOGGER  = Logger.getLogger(CacheJobExecutorImpl.class);

    private int port;
    private ServerSocket ss;

    private int initialCacheSize;
    private Map<String, ValueBO> cache;

    private int maximumPoolSize;
    private ThreadPoolExecutor jobsPool;

    private ExpiredTtlDataCleaner cleaner;

    public void init() {
        cache = new ConcurrentHashMap<>(initialCacheSize);

        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            LOGGER.error("Unable to open port", e);
        }

        initThreadPool();

        initExpiredTtlDataCleaner();

        listen();
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void setInitialCacheSize(int initialCacheSize) {
        this.initialCacheSize = initialCacheSize;
    }

    @Override
    public void setMaxPoolSize(int size) {
        maximumPoolSize = size;
    }

    public void setCleaner(ExpiredTtlDataCleaner cleaner) {
        this.cleaner = cleaner;
    }

    @Override
    public Map<String, ValueBO> getCache() {
        return cache;
    }

    private void listen() {
        try {
            while (true) {
                Socket s = ss.accept();
                CacheJob consumer = new CacheJobImpl(s, this);
                jobsPool.execute(consumer);
            }
        } catch (IOException e) {
            LOGGER.error("Socket error", e);
        }
    }

    private void initThreadPool() {
        jobsPool = new ThreadPoolExecutor(1, maximumPoolSize, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>() {
            @Override
            public boolean offer(Runnable e) {
                if (size() <= 1) {
                    return super.offer(e);
                } else {
                    return false;
                }
            }
        });

        jobsPool.setRejectedExecutionHandler(new RejectedExecutionHandler() {

            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                try {
                    executor.getQueue().put(r);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        });
    }

    private void initExpiredTtlDataCleaner() {
        Thread thread = new Thread(cleaner);
        thread.setDaemon(true);
        thread.start();
    }
}
