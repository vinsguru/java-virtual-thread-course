package com.vinsguru.tripadvisor.controller;

import com.vinsguru.tripadvisor.dto.FlightReservationResponse;
import com.vinsguru.tripadvisor.dto.TripPlan;
import com.vinsguru.tripadvisor.dto.TripReservationRequest;
import com.vinsguru.tripadvisor.service.TripPlanService;
import com.vinsguru.tripadvisor.service.TripReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("trip")
@RequiredArgsConstructor
public class TripController {

    private final TripPlanService planService;
    private final TripReservationService reservationService;

    @GetMapping("{airportCode}")
    public TripPlan planTrip(@PathVariable String airportCode){
        return this.planService.getTripPlan(airportCode);
    }

    @PostMapping("reserve")
    public FlightReservationResponse reserveFlight(@RequestBody TripReservationRequest request){
        return this.reservationService.reserve(request);
    }

}
