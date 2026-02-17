package com.flight_booking.passenger.repository;

import com.flight_booking.passenger.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    Boolean existsByDni(String dni);
    Optional<Passenger> findByDni(String dni);
    Passenger getReferenceByDni(String dni);
    List<Passenger> findByDniIn(List<String> dnis);
}
