package com.flight_booking.booking.validators;

import com.flight_booking.flight.entity.Flight;
import com.flight_booking.ticket.dto.TicketCreateRequestDto;

import java.util.List;

public class BookingValidationContext {

    private Flight flight;
    private int seatReserve;

    public BookingValidationContext() {
    }

    public BookingValidationContext(Flight flight, int seatReserve) {
        this.flight = flight;
        this.seatReserve = seatReserve;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public int getSeatReserve() {
        return seatReserve;
    }

    public void setSeatReserve(int seatReserve) {
        this.seatReserve = seatReserve;
    }
}
