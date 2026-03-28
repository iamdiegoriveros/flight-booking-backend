package com.flight_booking.ticket.dto;

import com.flight_booking.passenger.dto.PassengerCreateRequestDto;

public class TicketCreateRequestDto {

    private String travelClass;
    private PassengerCreateRequestDto passengerCreateRequestDto;

    public String getTravelClass() {
        return travelClass;
    }

    public void setTravelClass(String travelClass) {
        this.travelClass = travelClass;
    }

    public PassengerCreateRequestDto getPassengerCreateRequestDto() {
        return passengerCreateRequestDto;
    }

    public void setPassengerCreateRequestDto(PassengerCreateRequestDto passengerCreateRequestDto) {
        this.passengerCreateRequestDto = passengerCreateRequestDto;
    }
}
