package com.vinsguru.tripadvisor.client;

import com.vinsguru.tripadvisor.dto.LocalRecommendations;
import org.springframework.web.client.RestClient;

public class LocalRecommendationServiceClient {

    private final RestClient restClient;

    public LocalRecommendationServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public LocalRecommendations getRecommendations(String airportCode) {
        return this.restClient.get()
                              .uri("{airportCode}", airportCode)
                              .retrieve()
                              .body(LocalRecommendations.class);
    }

}
