package com.flight_booking.flight.dto;

import java.time.LocalDateTime;

public class FlightSummaryBookingDto {

    Long id;
    String origin;
    String destination;
    LocalDateTime dateTimeDeparture;
    LocalDateTime dateTimeArrival;
    String status;
    String airlineName;

    public FlightSummaryBookingDto() {
    }

    public FlightSummaryBookingDto(Long id, String destination, String origin, LocalDateTime dateTimeDeparture, LocalDateTime dateTimeArrival, String status, String airlineName) {
        this.id = id;
        this.destination = destination;
        this.origin = origin;
        this.dateTimeDeparture = dateTimeDeparture;
        this.dateTimeArrival = dateTimeArrival;
        this.status = status;
        this.airlineName = airlineName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }
}
