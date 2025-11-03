package com.flight_booking.aircraft.entity;

import com.flight_booking.airline.entity.Airline;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AircraftTest {

    @Test
    void createAircraft() {
        Aircraft aircraft = new Aircraft("ak154", 120, "Operativo");

        assertEquals("ak154", aircraft.getModel());
        assertEquals(120, aircraft.getCapacity());
        assertEquals("Operativo", aircraft.getStatus());
    }

    @Test
    void relationshipAircraftWithAirline() {
        Aircraft aircraft = new Aircraft("ak154", 120, "Operativo");

        Airline airline = new Airline("American Airline", "aa145");

        aircraft.setAirline(airline);

        assertNotNull(aircraft.getAirline());
        assertEquals(airline, aircraft.getAirline());
        assertEquals("American Airline", aircraft.getAirline().getName());
        assertEquals("aa145", aircraft.getAirline().getCode());
    }
}
