package com.flight_booking.aircraft.service;

import com.flight_booking.aircraft.dto.AircraftCreateRequestDto;
import com.flight_booking.aircraft.dto.AircraftCreateResponseDto;

public interface AircraftService {

    AircraftCreateResponseDto create(AircraftCreateRequestDto requestDto);
}
