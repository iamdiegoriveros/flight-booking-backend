package com.flight_booking.booking.dto;

import com.flight_booking.flight.dto.FlightCreateResponseDto;
import com.flight_booking.flight.dto.FlightSummaryBookingDto;
import com.flight_booking.passenger.dto.PassengerCreateResponseDto;

public class TicketCreateResponseDto {

    private Long id;
    private String travelClass;
    private String seatCode;
    private float price;
    private String currency;
    private String status;
    private PassengerCreateResponseDto passenger;
    private FlightSummaryBookingDto flight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTravelClass() {
        return travelClass;
    }

    public void setTravelClass(String travelClass) {
        this.travelClass = travelClass;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PassengerCreateResponseDto getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerCreateResponseDto passenger) {
        this.passenger = passenger;
    }

    public FlightSummaryBookingDto getFlight() {
        return flight;
    }

    public void setFlight(FlightSummaryBookingDto flight) {
        this.flight = flight;
    }


}
