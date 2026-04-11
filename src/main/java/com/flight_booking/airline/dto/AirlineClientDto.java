package com.flight_booking.airline.dto;

import java.util.List;

public class AirlineClientDto {

    List<AirlineDataClient> data;

    public List<AirlineDataClient> getData() {
        return data;
    }

    public void setData(List<AirlineDataClient> data) {
        this.data = data;
    }
}
