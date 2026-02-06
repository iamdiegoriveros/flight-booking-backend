package com.flight_booking.flight.dto;

import java.time.LocalDateTime;

public class FlightCreateRequestDto {

    private String origin;
    private String destination;
    private LocalDateTime dateTimeDeparture;
    private LocalDateTime dateTimeArrival;
    private String status;
    private Long aircraftId;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDateTimeDeparture() {
        return dateTimeDeparture;
    }

    public void setDateTimeDeparture(LocalDateTime dateTimeDeparture) {
        this.dateTimeDeparture = dateTimeDeparture;
    }

    public LocalDateTime getDateTimeArrival() {
        return dateTimeArrival;
    }

    public void setDateTimeArrival(LocalDateTime dateTimeArrival) {
        this.dateTimeArrival = dateTimeArrival;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }
}
