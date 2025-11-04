package com.flight_booking.flight.entity;

import com.flight_booking.aircraft.entity.Aircraft;
import com.flight_booking.airline.entity.Airline;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FlightTest {

    @Test
    void createFlight() {

        LocalDateTime departure = LocalDateTime.of(2025, 9,12,9,15);
        LocalDateTime arrival = LocalDateTime.of(2025,9,12,13,30);

        Flight flight = new Flight("Londres", "Madrid", departure, arrival);

        assertEquals("Londres", flight.getOrigin());
        assertEquals("Madrid", flight.getDestination());
        assertEquals(LocalDateTime.of(2025, 9,12,9,15), flight.getDateTimeDeparture());
        assertEquals(LocalDateTime.of(2025,9,12,13,30), flight.getDateTimeArrival());
    }

    @Test
    void relationshipFlightWithAircraft() {
        
        LocalDateTime departure = LocalDateTime.of(2025, 9,12,9,15);
        LocalDateTime arrival = LocalDateTime.of(2025,9,12,13,30);

        Flight flight = new Flight("Londres", "Madrid", departure, arrival);

        Aircraft aircraft = new Aircraft("ak154", 120, "Operativo");
        Airline airline = new Airline("American Airline", "aa145");
        aircraft.setAirline(airline);

        flight.setAircraft(aircraft);

        assertNotNull(flight.getAircraft());
        assertEquals("ak154", flight.getAircraft().getModel());
        assertEquals("American Airline", flight.getAircraft().getAirline().getName());

    }
}
