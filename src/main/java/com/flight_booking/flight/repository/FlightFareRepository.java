package com.flight_booking.flight.repository;

import com.flight_booking.flight.entity.FlightFare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightFareRepository extends JpaRepository<FlightFare, Long> {

    List<FlightFare> findByFlightId(Long id);
}
