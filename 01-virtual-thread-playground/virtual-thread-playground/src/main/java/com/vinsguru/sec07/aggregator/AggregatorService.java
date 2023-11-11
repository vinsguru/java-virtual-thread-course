package com.vinsguru.sec07.aggregator;

import com.vinsguru.sec07.externalservice.Client;
import java.util.concurrent.ExecutorService;

public class AggregatorService {

    private final ExecutorService executorService;

    public AggregatorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ProductDto getProductDto(int id) throws Exception {
        var product = executorService.submit(() -> Client.getProduct(id));
        var rating = executorService.submit(() -> Client.getRating(id));
        return new ProductDto(id, product.get(), rating.get());
    }

}
