package com.vinsguru.sec08.aggregator;

import com.vinsguru.sec08.externalservice.Client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class AggregatorService {

    private final ExecutorService executorService;

    public AggregatorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ProductDto getProductDto(int id) {
        var product = CompletableFuture.supplyAsync(() -> Client.getProduct(id), executorService)
                                       .orTimeout(1250, TimeUnit.MILLISECONDS)
                                       .exceptionally(ex -> null);
        var rating = CompletableFuture.supplyAsync(() -> Client.getRating(id), executorService)
                                      .exceptionally(ex -> -1)
                                      .orTimeout(1250, TimeUnit.MILLISECONDS)
                                      .exceptionally(ex -> -2);
        return new ProductDto(id, product.join(), rating.join());
    }

}
