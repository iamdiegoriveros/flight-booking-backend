package com.flight_booking.airline.dto;

public class AirlineCreateResquestDto {

    private String name;
    private String code;

    public AirlineCreateResquestDto() {
    }

    public AirlineCreateResquestDto(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
