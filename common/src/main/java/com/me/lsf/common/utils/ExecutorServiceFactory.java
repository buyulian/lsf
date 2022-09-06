package com.me.lsf.common.utils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池工厂类
 */
public class ExecutorServiceFactory {

    private static final ExecutorService CACHED_THREAD_POOL =
            new ThreadPoolExecutor(0
                    , 100
                    , 60L
                    , TimeUnit.SECONDS
                    ,new SynchronousQueue<>()
                    ,new CustomThreadFactory()
            );

    public static ExecutorService getNewCachedThreadPool(){
        return CACHED_THREAD_POOL;
    }

    private static class CustomThreadFactory implements ThreadFactory {

        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            String threadName = "lsf task thread " + count.addAndGet(1);
            thread.setName(threadName);
            return thread;
        }
    }
}
