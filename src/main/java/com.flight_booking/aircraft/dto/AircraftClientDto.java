package com.flight_booking.aircraft.dto;

import java.util.List;

public class AircraftClientDto {

    List<AircraftDataClient> data;

    public List<AircraftDataClient> getData() {
        return data;
    }

    public void setData(List<AircraftDataClient> data) {
        this.data = data;
    }
}
