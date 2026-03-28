package com.flight_booking.flight.service;

import com.flight_booking.exceptions.ResourceNotFoundException;
import com.flight_booking.flight.entity.FlightFare;
import com.flight_booking.flight.repository.FlightFareRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FlightFareServiceImpl implements FlightFareService{

    private final FlightFareRepository flightFareRepository;

    public FlightFareServiceImpl(FlightFareRepository flightFareRepository) {
        this.flightFareRepository = flightFareRepository;
    }

    @Override
    public Map<String, FlightFare> getFlightFareMap(Long flightId) {
        List<FlightFare> flightFaresList = flightFareRepository.findByFlightId(flightId);

        if (flightFaresList.isEmpty()) throw new ResourceNotFoundException("Fares not found for flight with id: " + flightId);

        Map<String, FlightFare> fareMap = flightFaresList.stream()
                .collect(Collectors.toMap(FlightFare::getTravelClass, fare -> fare));

        return fareMap;
    }
}
