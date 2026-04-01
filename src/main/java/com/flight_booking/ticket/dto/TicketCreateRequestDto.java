package com.flight_booking.ticket.dto;

import com.flight_booking.passenger.dto.PassengerCreateRequestDto;

public class TicketCreateRequestDto {

    private String travelClass;
    private PassengerCreateRequestDto passengers;

    public String getTravelClass() {
        return travelClass;
    }

    public void setTravelClass(String travelClass) {
        this.travelClass = travelClass;
    }

    public PassengerCreateRequestDto getPassengers() {
        return passengers;
    }

    public void setPassengers(PassengerCreateRequestDto passengers) {
        this.passengers = passengers;
    }
}
