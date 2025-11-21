package com.vinsguru.tripadvisor.client;

import com.vinsguru.tripadvisor.dto.Transportation;
import org.springframework.web.client.RestClient;

public class TransportationServiceClient {

    private final RestClient restClient;

    public TransportationServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public Transportation getTransportation(String airportCode) {
        return this.restClient.get()
                              .uri("{airportCode}", airportCode)
                              .retrieve()
                              .body(Transportation.class);
    }

}
