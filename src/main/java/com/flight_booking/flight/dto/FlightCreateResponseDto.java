package com.flight_booking.flight.dto;

import com.flight_booking.aircraft.dto.AircraftCreateResponseDto;
import com.flight_booking.aircraft.entity.Aircraft;

import java.time.LocalDateTime;
import java.util.List;

public class FlightCreateResponseDto {

    private Long id;
    private String origin;
    private String destination;
    private LocalDateTime dateTimeDeparture;
    private LocalDateTime dateTimeArrival;
    private String status;
    private AircraftCreateResponseDto aircraft;
    private List<FlightFareResponseDto> flightFare;

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

    public AircraftCreateResponseDto getAircraft() {
        return aircraft;
    }

    public void setAircraft(AircraftCreateResponseDto aircraft) {
        this.aircraft = aircraft;
    }

    public List<FlightFareResponseDto> getFlightFare() {
        return flightFare;
    }

    public void setFlightFare(List<FlightFareResponseDto> flightFare) {
        this.flightFare = flightFare;
    }
}
