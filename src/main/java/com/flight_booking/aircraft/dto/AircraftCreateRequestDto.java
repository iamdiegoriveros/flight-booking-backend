package com.flight_booking.aircraft.dto;

public class AircraftCreateRequestDto {

    private String model;
    private int capacity;
    private String status;
    private Long airlineId;

    public String getModel() {
        return model;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getStatus() {
        return status;
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

    public Long getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(Long airlineId) {
        this.airlineId = airlineId;
    }
}
