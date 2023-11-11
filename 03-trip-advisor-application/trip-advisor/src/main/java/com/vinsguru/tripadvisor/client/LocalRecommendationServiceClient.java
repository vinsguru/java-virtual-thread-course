package com.vinsguru.tripadvisor.client;

import com.vinsguru.tripadvisor.dto.LocalRecommendations;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class LocalRecommendationServiceClient {

    private final RestClient restClient;

    public LocalRecommendations getRecommendations(String airportCode) {
        return this.restClient.get()
                              .uri("{airportCode}", airportCode)
                              .retrieve()
                              .body(LocalRecommendations.class);
    }

}
