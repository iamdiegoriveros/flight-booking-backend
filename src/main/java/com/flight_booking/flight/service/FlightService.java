package com.flight_booking.flight.service;

import com.flight_booking.flight.dto.FlightCreateRequestDto;
import com.flight_booking.flight.dto.FlightResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FlightService{

    FlightResponseDto create(FlightCreateRequestDto requestDto);

    List<FlightResponseDto> getAllFlight(Pageable pageable);

}
