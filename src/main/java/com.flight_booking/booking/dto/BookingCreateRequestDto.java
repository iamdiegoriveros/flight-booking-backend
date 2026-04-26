package com.flight_booking.booking.dto;

import com.flight_booking.ticket.dto.TicketCreateRequestDto;

import java.util.List;

public class BookingCreateRequestDto {

    private Long flightId;
    private PassengerSeatDto passengerSeat;

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public PassengerSeatDto getPassengerSeat() {
        return passengerSeat;
    }

    public void setPassengerSeat(PassengerSeatDto passengerSeat) {
        this.passengerSeat = passengerSeat;
    }
}
