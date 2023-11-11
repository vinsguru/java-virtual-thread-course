package com.vinsguru.sec08;

import com.vinsguru.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

/*
    Factory method
    Run async
    Executor
 */
public class Lec02RunAsync {

    private static final Logger log = LoggerFactory.getLogger(Lec02RunAsync.class);

    public static void main(String[] args) {
        log.info("main starts");

        runAsync()
                .thenRun(() -> log.info("it is done"))
                 .exceptionally(ex -> {
                     log.info("error - {}", ex.getMessage());
                     return null;
                 });

        log.info("main ends");
        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static CompletableFuture<Void> runAsync(){
        log.info("method starts");

        var cf = CompletableFuture.runAsync(() -> {
            CommonUtils.sleep(Duration.ofSeconds(1));
           // log.info("task completed");
            throw new RuntimeException("oops");
        }, Executors.newVirtualThreadPerTaskExecutor());

        log.info("method ends");
        return cf;
    }

}
