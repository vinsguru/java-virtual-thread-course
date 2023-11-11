package com.vinsguru.tripadvisor.dto;

import java.util.List;

public record TripPlan(String airportCode,
                       List<Accommodation> accommodations,
                       Weather weather,
                       List<Event> events,
                       LocalRecommendations localRecommendations,
                       Transportation transportation) {
}
