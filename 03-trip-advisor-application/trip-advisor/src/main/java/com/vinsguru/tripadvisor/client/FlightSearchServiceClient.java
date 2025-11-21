package com.vinsguru.tripadvisor.client;

import com.vinsguru.tripadvisor.dto.Flight;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class FlightSearchServiceClient {

    private final RestClient client;

    public FlightSearchServiceClient(RestClient client) {
        this.client = client;
    }

    public List<Flight> getFlights(String departure, String arrival){
        return this.client.get()
                .uri("/{departure}/{arrival}", departure, arrival)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Flight>>() {
                });
    }

}
