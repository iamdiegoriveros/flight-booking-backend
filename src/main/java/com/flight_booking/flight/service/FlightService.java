package com.flight_booking.flight.service;

import com.flight_booking.flight.dto.FlightCreateRequestDto;
import com.flight_booking.flight.dto.FlightCreateResponseDto;

public interface FlightService{

    public FlightCreateResponseDto create(FlightCreateRequestDto requestDto);
}
