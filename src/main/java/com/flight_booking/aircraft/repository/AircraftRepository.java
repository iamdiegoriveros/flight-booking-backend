package com.flight_booking.aircraft.repository;

import com.flight_booking.aircraft.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
}
