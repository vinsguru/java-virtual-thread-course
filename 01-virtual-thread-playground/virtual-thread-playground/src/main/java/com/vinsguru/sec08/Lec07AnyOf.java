package com.vinsguru.sec08;

import com.vinsguru.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class Lec07AnyOf {

    private static final Logger log = LoggerFactory.getLogger(Lec07AnyOf.class);

    public static void main(String[] args) {

            try(var executor = Executors.newVirtualThreadPerTaskExecutor()){
                var cf1 = getDeltaAirfare(executor);
                var cf2 = getFrontierAirfare(executor);
                log.info("airfare={}", CompletableFuture.anyOf(cf1, cf2).join());
            }

    }

    private static CompletableFuture<String> getDeltaAirfare(ExecutorService executor){
        return CompletableFuture.supplyAsync(() -> {
            var random = ThreadLocalRandom.current().nextInt(100, 1000);
            CommonUtils.sleep(Duration.ofMillis(random));
            return "Delta-$" + random;
        }, executor);
    }

    private static CompletableFuture<String> getFrontierAirfare(ExecutorService executor){
        return CompletableFuture.supplyAsync(() -> {
            var random = ThreadLocalRandom.current().nextInt(100, 1000);
            CommonUtils.sleep(Duration.ofMillis(random));
            return "Frontier-$" + random;
        }, executor);
    }

}
