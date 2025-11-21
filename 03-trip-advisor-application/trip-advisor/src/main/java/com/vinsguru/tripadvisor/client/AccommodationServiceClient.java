package com.vinsguru.tripadvisor.client;

import com.vinsguru.tripadvisor.dto.Accommodation;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class AccommodationServiceClient {

    private final RestClient client;

    public AccommodationServiceClient(RestClient client) {
        this.client = client;
    }

    public List<Accommodation> getAccommodations(String airportCode) {
        return this.client.get()
                          .uri("{airportCode}", airportCode)
                          .retrieve()
                          .body(new ParameterizedTypeReference<List<Accommodation>>() {
                          });
    }

}
