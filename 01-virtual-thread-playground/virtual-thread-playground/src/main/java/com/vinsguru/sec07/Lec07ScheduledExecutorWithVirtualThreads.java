package com.vinsguru.sec07;

import com.vinsguru.sec07.externalservice.Client;
import com.vinsguru.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Lec07ScheduledExecutorWithVirtualThreads {

    private static final Logger log = LoggerFactory.getLogger(Lec07ScheduledExecutorWithVirtualThreads.class);

    public static void main(String[] args) {
        scheduled();
    }

    // To schedule tasks periodically
    private static void scheduled(){
        var scheduler = Executors.newSingleThreadScheduledExecutor();
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        try(scheduler; executor){
            scheduler.scheduleAtFixedRate(() -> {
                executor.submit(() -> printProductInfo(1));
            }, 0, 3, TimeUnit.SECONDS);

            CommonUtils.sleep(Duration.ofSeconds(15));
        }
    }

    private static void printProductInfo(int id){
        log.info("{} => {}", id, Client.getProduct(id));
    }

}
