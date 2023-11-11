package com.vinsguru.tripadvisor;

import com.vinsguru.tripadvisor.dto.Accommodation;
import com.vinsguru.tripadvisor.dto.FlightReservationRequest;
import com.vinsguru.tripadvisor.dto.FlightReservationResponse;
import com.vinsguru.tripadvisor.dto.Weather;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.List;

/*
	This is a simple demo to play with RestClient
	Not spring unit tests / integrations tests
 */
class RestClientTests {

    private static final Logger log = LoggerFactory.getLogger(RestClientTests.class);

    @Test
    void simpleGet() {
        var client = RestClient.create();
        var response = client.get()
                             .uri("http://localhost:7070/sec02/weather/LAS")
                             .retrieve()
                             .body(Weather.class);
        log.info("response: {}", response);
    }

    @Test
    void baseUrl() {
        var client = RestClient.builder()
                .baseUrl("http://localhost:7070/sec02/weather/")
                .build();

        var response = client.get()
                             .uri("{airportCode}", "LAS")
                             .retrieve()
                             .body(Weather.class);
        log.info("response: {}", response);
    }

    @Test
    void listResponse() {
        var client = RestClient.builder()
                               .baseUrl("http://localhost:7070/sec02/accommodations/")
                               .build();

        var response = client.get()
                             .uri("{airportCode}", "LAS")
                             .retrieve()
                             .body(new ParameterizedTypeReference<List<Accommodation>>() {
                             });
        log.info("response: {}", response);
    }

    @Test
    void postRequest() {
        var client = RestClient.builder()
                               .baseUrl("http://localhost:7070/sec03/flight/reserve/")
                               .build();
        var request = new FlightReservationRequest("ATL", "LAS", "UA789", LocalDate.now());
        var response = client.post()
                             .body(request)
                             .retrieve()
                             .body(FlightReservationResponse.class);
        log.info("response: {}", response);
    }

}

