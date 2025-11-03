package com.flight_booking.airline.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AirlineTest {

    @Test
    void createAirline() {
        Airline airline = new Airline("Aerolineas Argentinas", "AA123");

        assertEquals("Aerolineas Argentinas", airline.getName());
        assertEquals("AA123", airline.getCode());
    }
}
