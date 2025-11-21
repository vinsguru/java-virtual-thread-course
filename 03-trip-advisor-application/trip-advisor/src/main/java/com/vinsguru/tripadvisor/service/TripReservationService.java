package com.vinsguru.tripadvisor.service;

import com.vinsguru.tripadvisor.client.FlightReservationServiceClient;
import com.vinsguru.tripadvisor.client.FlightSearchServiceClient;
import com.vinsguru.tripadvisor.dto.Flight;
import com.vinsguru.tripadvisor.dto.FlightReservationRequest;
import com.vinsguru.tripadvisor.dto.FlightReservationResponse;
import com.vinsguru.tripadvisor.dto.TripReservationRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class TripReservationService {

    private final FlightSearchServiceClient searchServiceClient;
    private final FlightReservationServiceClient reservationServiceClient;

    public TripReservationService(FlightSearchServiceClient searchServiceClient, FlightReservationServiceClient reservationServiceClient) {
        this.searchServiceClient = searchServiceClient;
        this.reservationServiceClient = reservationServiceClient;
    }

    public FlightReservationResponse reserve(TripReservationRequest request){
        var flights = this.searchServiceClient.getFlights(request.departure(), request.arrival());
        var bestDeal = flights.stream().min(Comparator.comparingInt(Flight::price));
        var flight = bestDeal.orElseThrow(() -> new IllegalStateException("no flights found"));
        var reservationRequest = new FlightReservationRequest(request.departure(), request.arrival(), flight.flightNumber(), request.date());
        return this.reservationServiceClient.reserve(reservationRequest);
    }

}
