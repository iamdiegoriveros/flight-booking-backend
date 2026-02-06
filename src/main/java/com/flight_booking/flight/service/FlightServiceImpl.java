package com.flight_booking.flight.service;

import com.flight_booking.aircraft.entity.Aircraft;
import com.flight_booking.aircraft.repository.AircraftRepository;
import com.flight_booking.exceptions.ResourceNotFoundException;
import com.flight_booking.flight.dto.FlightCreateRequestDto;
import com.flight_booking.flight.dto.FlightCreateResponseDto;
import com.flight_booking.flight.entity.Flight;
import com.flight_booking.flight.repository.FlightRepository;
import com.flight_booking.mapper.FlightMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlightServiceImpl implements FlightService{

    private final FlightRepository flightRepository;
    private final AircraftRepository aircraftRepository;
    private final FlightMapper flightMapper;

    public FlightServiceImpl(FlightRepository flightRepository, AircraftRepository aircraftRepository, FlightMapper flightMapper) {
        this.flightRepository = flightRepository;
        this.aircraftRepository = aircraftRepository;
        this.flightMapper = flightMapper;
    }

    @Override
    @Transactional
    public FlightCreateResponseDto create(FlightCreateRequestDto requestDto) {

        Aircraft aircraft = aircraftRepository.findById(requestDto.getAircraftId())
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found with id " + requestDto.getAircraftId()));

        Flight flight = flightMapper.toEntity(requestDto);

        flight.setAircraft(aircraft);

        return flightMapper.toDto(flightRepository.save(flight));
    }
}
