package com.flight_booking.airline.service;

import com.flight_booking.airline.dto.AirlineResponseDto;
import com.flight_booking.airline.dto.AirlineCreateResquestDto;
import com.flight_booking.airline.entity.Airline;
import com.flight_booking.exceptions.ResourceNotFoundException;

import java.util.List;

public interface AirlineService {

    AirlineResponseDto create(AirlineCreateResquestDto requestDto);

    List<AirlineResponseDto> getAllAirline();

    void deleteAirlineById(Long id);
}
