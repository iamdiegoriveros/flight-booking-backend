package com.flight_booking.booking.dto;

import com.flight_booking.ticket.dto.TicketCreateRequestDto;

import java.util.List;

public class BookingCreateRequestDto {

    private Long flightId;
    private List<TicketCreateRequestDto> tickets;

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public List<TicketCreateRequestDto> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketCreateRequestDto> tickets) {
        this.tickets = tickets;
    }
}
