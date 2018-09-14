package com.gd.ashylin.crawler.crawler.v2;

import com.gd.ashylin.crawler.crawler.listener.CrawlerFinishListener;
import com.gd.ashylin.crawler.crawler.listener.CrawlerLaunchListener;
import com.gd.ashylin.crawler.crawler.util.UrlUtil;
import com.gd.ashylin.crawler.db.entity.Result;
import com.gd.ashylin.crawler.db.entity.ScrapResult;
import com.gd.ashylin.crawler.dto.CacheDto;
import com.gd.ashylin.crawler.exception.RelaunchCrawlerException;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A part of producer-consumer pattern
 *
 * Producer - gives tasks to Consumer {@link com.gd.ashylin.crawler.crawler.v2.CrawlerImpl}
 *
 * @author Alexander Shylin
 */
public class CrawlerMultiThreadManagerImpl extends Thread implements CrawlerMultiThreadManager {
    static final Logger LOGGER = Logger.getLogger(CrawlerMultiThreadManagerImpl.class);

    private Lock accessToCollections = new ReentrantLock();

    private String mainPageUrl;
    private int threads;
    private long delay;
    private Boolean enableCaching;

    private ThreadPoolExecutor threadPool;
    private Queue<ScrapResult> tasks = new ConcurrentLinkedQueue<>();

    private Result result;
    private CrawlerLaunchListener launchListener;
    private CrawlerFinishListener finishListener;
    private boolean canceled;



    private static final String SOURCE_URL_ROOT = "/";

    @Override
    public long launch() {
        if (canceled) {
            throw new RelaunchCrawlerException();
        }

        result = new Result(mainPageUrl, threads, delay);
        initThreadPool();
        if (launchListener != null) {
            launchListener.eventDispatcher(result);
        }
        this.start();
        return result.getId();
    }

    private void initThreadPool() {
        threadPool = new ThreadPoolExecutor(1, threads, 15, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>() {
            @Override
            public boolean offer(Runnable e) {
                if (size() <= 1) {
                    return super.offer(e);
                } else {
                    return false;
                }
            }
        });

        threadPool.setRejectedExecutionHandler(new RejectedExecutionHandler() {
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

    @Override
    public void run() {
        initialize();
        while (!tasks.isEmpty() || areWorkingThreadsExist() || threadPool.getPoolSize()>1 && !canceled) {
            while (!tasks.isEmpty()) {
                threadPool.execute(new CrawlerImpl(relocateScrapResult(), mainPageUrl, new CrawlerConsumerHelper() {
                    @Override
                    public void addTask(ScrapResult scrapResult) {
                        tryAddTask(scrapResult);
                    }
                }, enableCaching));
            }
            doSleep(delay);
        }
        finish();
    }

    @Override
    public void cancel() {
        canceled = true;
        result.setStatus(Result.STATUS_CANCELED);
    }



    /*
     * private methods
     */

    private void initialize() {
        ScrapResult initialScrap = new ScrapResult(0, mainPageUrl, SOURCE_URL_ROOT, CrawlerImpl.STATUS_UNDEFINED, 0);
        result.add(initialScrap);
        Crawler crawler = new CrawlerImpl(initialScrap, mainPageUrl, this::tryAddTask, enableCaching);
        crawler.runInCurrentThread();
    }

    private void finish() {
        result.setTimestampFinish(new Date());
        threadPool.shutdown();
        while (threadPool.isTerminating()) {
            doSleep(delay);
        }
        if (!result.getStatus().equals(Result.STATUS_CANCELED)) {
            result.setStatus(Result.STATUS_FINISHED);
        }
        finishListener.eventDispatcher(result);
    }


    private void doSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            LOGGER.error("Manager sleep error", e);
        }
    }

    private boolean areWorkingThreadsExist() {
        return threadPool.getActiveCount() > 0;
    }


    /*
     * thread-safe methods
     */

    private void tryAddTask(ScrapResult scrapResult) {
        accessToCollections.lock();
        try {
            if (!result.contains(scrapResult) && !tasks.contains(scrapResult)) {
                tasks.add(scrapResult);
            }
        } finally {
            accessToCollections.unlock();
        }
    }

    private ScrapResult relocateScrapResult() {
        accessToCollections.lock();
        try {
            ScrapResult toRelocate = tasks.poll();
            result.add(toRelocate);
            return toRelocate;
        } finally {
            accessToCollections.unlock();
        }
    }

    public Result getResult() {
        return result;
    }

    public void setUrl(String mainPageUrl) {
        this.mainPageUrl = new UrlUtil().removeEndingSlashes(mainPageUrl);
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public void setDelay(long delay) {
        if (delay < 10L) {
            this.delay = 10L;
        } else {
            this.delay = delay;
        }
    }

    public void setCrawlerLaunchListener(CrawlerLaunchListener launchListener) {
        this.launchListener = launchListener;
    }

    public void setCrawlerFinishListener(CrawlerFinishListener finishListener) {
        this.finishListener = finishListener;
    }

    public void setEnableCaching(Boolean enableCaching) {
        this.enableCaching = enableCaching;
    }
}
