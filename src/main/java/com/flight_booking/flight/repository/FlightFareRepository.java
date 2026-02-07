package com.flight_booking.flight.repository;

import com.flight_booking.flight.entity.FlightFare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightFareRepository extends JpaRepository<FlightFare, Long> {
}
