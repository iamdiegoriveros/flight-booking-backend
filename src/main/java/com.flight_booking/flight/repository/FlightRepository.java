package com.flight_booking.flight.repository;

import com.flight_booking.flight.entity.Flight;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long>,
        JpaSpecificationExecutor<Flight> {

    List<Flight> findByOriginAndDestination(String origin, String destination, Pageable pageable);

    @Modifying
    @Query("""
            UPDATE Flight f
            SET f.availableSeats = f.availableSeats - :seatCount
            WHERE f.id = :flightId AND f.availableSeats >= :seatCount
            """)
    int reserveSeat(Long flightId, int seatCount);

    // version con OPTIMISTIC locking
//    @Modifying
//    @Query("""
//    UPDATE Flight f
//    SET f.availableSeats = f.availableSeats - :seatCount,
//        f.version = f.version + 1
//    WHERE f.id = :flightId
//    AND f.version = :version
//    AND f.availableSeats >= :seatCount
//""")
//    int reserveSeat(Long flightId, int seatCount, Long version);
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
