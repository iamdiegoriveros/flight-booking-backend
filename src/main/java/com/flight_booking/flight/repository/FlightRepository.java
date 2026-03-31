package com.flight_booking.flight.repository;

import com.flight_booking.flight.entity.Flight;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long>,
        JpaSpecificationExecutor<Flight> {

    List<Flight> findByOriginAndDestination(String origin, String destination, Pageable pageable);

//    @Query("""
//            SELECT new com.flight_booking.flight.dto.FlightSummaryBookingDto(
//                f.id,
//                f.origin,
//                f.destination,
//                f.dateTimeDeparture,
//                f.dateTimeArrival,
//                f.status,
//                al.name
//            )
//            FROM Flight f
//            JOIN f.aircraft ac
//            JOIN ac.airline al
//            WHERE f.id = :flightId
//            """)
//    Optional<FlightSummaryBookingDto> flightSummaryByIdBooking(@Param("flightId") Long flightId);
}
