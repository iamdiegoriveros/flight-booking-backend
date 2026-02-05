package com.flight_booking.airline.repository;

import com.flight_booking.airline.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AirlineRepository extends JpaRepository<Airline, Long> {
}
