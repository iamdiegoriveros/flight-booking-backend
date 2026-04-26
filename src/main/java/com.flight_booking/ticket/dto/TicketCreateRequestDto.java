package com.flight_booking.ticket.dto;

import com.flight_booking.passenger.dto.PassengerCreateRequestDto;

import java.util.List;

public class TicketCreateRequestDto {

    private Long bookingId;
    private List<PassengerCreateRequestDto> passengers;

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public List<PassengerCreateRequestDto> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerCreateRequestDto> passengers) {
        this.passengers = passengers;
    }
}
