package com.flight_booking.booking.validators;

import com.flight_booking.flight.entity.Flight;
import com.flight_booking.ticket.dto.TicketCreateRequestDto;

import java.util.List;

public class BookingValidationContext {

    private Flight flight;
    private List<TicketCreateRequestDto> tickets;

    public BookingValidationContext(Flight flight, List<TicketCreateRequestDto> tickets) {
        this.flight = flight;
        this.tickets = tickets;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public List<TicketCreateRequestDto> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketCreateRequestDto> tickets) {
        this.tickets = tickets;
    }
}
