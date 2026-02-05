package com.flight_booking.aircraft.dto;

import com.flight_booking.airline.dto.AirlineCreateResponseDto;

public class AircraftCreateResponseDto {

    private Long id;
    private String model;
    private int capacity;
    private String status;
    private AirlineCreateResponseDto airline;

    public String getModel() {
        return model;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public AirlineCreateResponseDto getAirline() {
        return airline;
    }

    public void setAirline(AirlineCreateResponseDto airline) {
        this.airline = airline;
    }
}
