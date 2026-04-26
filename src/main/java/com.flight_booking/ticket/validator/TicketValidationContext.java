package com.flight_booking.ticket.validator;

import com.flight_booking.booking.entity.Booking;
import com.flight_booking.booking.validators.BookingValidator;
import com.flight_booking.passenger.dto.PassengerCreateRequestDto;

import java.util.List;

public class TicketValidationContext {

    private Booking booking;
    private List<PassengerCreateRequestDto> passengers;

    public TicketValidationContext(Booking booking, List<PassengerCreateRequestDto> passengers) {
        this.booking = booking;
        this.passengers = passengers;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public List<PassengerCreateRequestDto> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerCreateRequestDto> passengers) {
        this.passengers = passengers;
    }
}
