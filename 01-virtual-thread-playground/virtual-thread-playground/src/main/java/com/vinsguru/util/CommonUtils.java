package com.vinsguru.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.stream.IntStream;

public class CommonUtils {

    private static final Logger log = LoggerFactory.getLogger(CommonUtils.class);

    public static void sleep(String taskName, Duration duration){
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            log.info("{} is cancelled", taskName);
        }
    }

    public static void sleep(Duration duration){
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static long timer(Runnable runnable){
        var start = System.currentTimeMillis();
        runnable.run();
        var end = System.currentTimeMillis();
        return (end - start);
    }

}
