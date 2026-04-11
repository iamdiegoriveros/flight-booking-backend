package com.flight_booking.aircraft.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AircraftDataClient {

    @JsonProperty("model_code")
    private String modelCode;

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }
}
