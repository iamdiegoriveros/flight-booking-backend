package com.flight_booking.passenger.repository;

import com.flight_booking.passenger.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    Boolean existsByDni(String dni);

    Optional<Passenger> findByDni(String dni);

    Passenger getReferenceByDni(String dni);

    List<Passenger> findByDniIn(Collection<String> dnis);
}
