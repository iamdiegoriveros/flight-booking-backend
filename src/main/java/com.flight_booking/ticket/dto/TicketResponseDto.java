package com.flight_booking.ticket.dto;

import com.flight_booking.passenger.dto.PassengerResponseDto;

public class TicketResponseDto {

    private Long id;
    private String travelClass;
    private String seatCode;
    private String status;
    private PassengerResponseDto passenger;
    private Long flightId;
    private Long bookingId;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PassengerResponseDto getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerResponseDto passenger) {
        this.passenger = passenger;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
}
