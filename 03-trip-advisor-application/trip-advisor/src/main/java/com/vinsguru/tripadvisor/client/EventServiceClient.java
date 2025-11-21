package com.vinsguru.tripadvisor.client;

import com.vinsguru.tripadvisor.dto.Event;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class EventServiceClient {

    private final RestClient client;

    public EventServiceClient(RestClient client) {
        this.client = client;
    }

    public List<Event> getEvents(String airportCode) {
        return this.client.get()
                          .uri("{airportCode}", airportCode)
                          .retrieve()
                          .body(new ParameterizedTypeReference<List<Event>>() {
                          });
    }

}
