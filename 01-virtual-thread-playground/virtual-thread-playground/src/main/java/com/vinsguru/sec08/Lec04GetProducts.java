package com.vinsguru.sec08;

import com.vinsguru.sec08.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

/*
    To get multiple products information in parallel
 */
public class Lec04GetProducts {

    private static final Logger log = LoggerFactory.getLogger(Lec04GetProducts.class);

    public static void main(String[] args) throws Exception {

        try(var executor = Executors.newVirtualThreadPerTaskExecutor()){

            var product1 = CompletableFuture.supplyAsync(() -> Client.getProduct(1), executor);
            var product2 = CompletableFuture.supplyAsync(() -> Client.getProduct(2), executor);
            var product3 = CompletableFuture.supplyAsync(() -> Client.getProduct(3), executor);

            log.info("product-1: {}", product1.get());
            log.info("product-2: {}", product2.get());
            log.info("product-3: {}", product3.get());

        }

    }

}
