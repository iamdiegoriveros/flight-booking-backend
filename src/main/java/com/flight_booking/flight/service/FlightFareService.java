package com.flight_booking.flight.service;

import com.flight_booking.flight.entity.FlightFare;

import java.util.Map;

public interface FlightFareService {

    Map<String, FlightFare> getFlightFareMap(Long flightId);
}
