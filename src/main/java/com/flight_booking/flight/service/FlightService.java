package com.flight_booking.flight.service;

import com.flight_booking.flight.dto.FlightCreateRequestDto;
import com.flight_booking.flight.dto.FlightFilterDto;
import com.flight_booking.flight.dto.FlightResponseDto;
import com.flight_booking.flight.entity.Flight;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FlightService{

    Flight getFlightEntity(Long flightId);

    FlightResponseDto create(FlightCreateRequestDto requestDto);

    List<FlightResponseDto> getAllFlight(Pageable pageable);

    List<FlightResponseDto> getByFilters(FlightFilterDto flightFilterDto, Pageable pageable);

    void reserveSeat(Long flightId, int seatCount);
}
