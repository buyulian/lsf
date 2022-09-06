package com.me.lsf.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class CommonUtils {

    private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    public static void holdThread() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.error("main InterruptedException", e);
            System.exit(1);
        }
    }

}
