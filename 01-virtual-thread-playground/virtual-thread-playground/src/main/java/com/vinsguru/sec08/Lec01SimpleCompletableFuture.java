package com.vinsguru.sec08;

import com.vinsguru.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

/*
    A very simple demo to show how CompletableFuture works
    Intentionally NOT using factory methods
 */
public class Lec01SimpleCompletableFuture {

    private static final Logger log = LoggerFactory.getLogger(Lec01SimpleCompletableFuture.class);

    public static void main(String[] args) {
        log.info("main starts");
        var cf = slowTask();
        cf.thenAccept(v -> log.info("value={}", v));

       // log.info("value={}", cf.join());
        log.info("main ends");

        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static CompletableFuture<String> fastTask(){
        log.info("method starts");
        var cf = new CompletableFuture<String>();
        cf.complete("hi");
        log.info("method ends");
        return cf;
    }

    private static CompletableFuture<String> slowTask(){
        log.info("method starts");
        var cf = new CompletableFuture<String>();
        Thread.ofVirtual().start(() -> {
            CommonUtils.sleep(Duration.ofSeconds(1));
            cf.complete("hi");
        });
        log.info("method ends");
        return cf;
    }

}
