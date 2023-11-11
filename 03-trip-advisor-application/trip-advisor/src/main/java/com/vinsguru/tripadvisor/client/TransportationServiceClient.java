package com.vinsguru.tripadvisor.client;

import com.vinsguru.tripadvisor.dto.Transportation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class TransportationServiceClient {

    private final RestClient restClient;

    public Transportation getTransportation(String airportCode) {
        return this.restClient.get()
                              .uri("{airportCode}", airportCode)
                              .retrieve()
                              .body(Transportation.class);
    }

}
