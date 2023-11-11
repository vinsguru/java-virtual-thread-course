package com.vinsguru.sec07;

import com.vinsguru.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.Executors;

/*
    ExecutorService now extends the AutoCloseable
*/
public class Lec01AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(Lec01AutoCloseable.class);

    public static void main(String[] args) {

    }

    // w/o autocloseable - we have to issue shutdown for short-lived applications
    private static void withoutAutoCloseable(){
        var executorService = Executors.newSingleThreadExecutor();
        executorService.submit(Lec01AutoCloseable::task);
        log.info("submitted");
        executorService.shutdown();
    }

    private static void withAutoCloseable(){
        try(var executorService = Executors.newSingleThreadExecutor()){
            executorService.submit(Lec01AutoCloseable::task);
            executorService.submit(Lec01AutoCloseable::task);
            executorService.submit(Lec01AutoCloseable::task);
            executorService.submit(Lec01AutoCloseable::task);
            log.info("submitted");
        }
    }

    private static void task(){
        CommonUtils.sleep(Duration.ofSeconds(1));
        log.info("task executed");
    }

}
