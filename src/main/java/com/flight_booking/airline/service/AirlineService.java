package com.flight_booking.airline.service;

import com.flight_booking.airline.dto.AirlineCreateResponseDto;
import com.flight_booking.airline.dto.AirlineCreateResquestDto;

public interface AirlineService {

    public AirlineCreateResponseDto create(AirlineCreateResquestDto requestDto);
}
