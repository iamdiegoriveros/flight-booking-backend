package com.flight_booking.aircraft.service;

import com.flight_booking.aircraft.dto.AircraftCreateRequestDto;
import com.flight_booking.aircraft.dto.AircraftCreateResponseDto;
import com.flight_booking.aircraft.entity.Aircraft;
import com.flight_booking.aircraft.repository.AircraftRepository;
import com.flight_booking.airline.entity.Airline;
import com.flight_booking.airline.repository.AirlineRepository;
import com.flight_booking.exceptions.airline.AirlineNotFoundException;
import com.flight_booking.mapper.AircraftMapper;
import org.springframework.stereotype.Service;

@Service
public class AircraftServiceImpl implements AircraftService{

    private final AircraftRepository aircraftRepository;
    private final AircraftMapper aircraftMapper;
    private final AirlineRepository airlineRepository;

    public AircraftServiceImpl(AircraftRepository aircraftRepository, AircraftMapper aircraftMapper, AirlineRepository airlineRepository) {
        this.aircraftRepository = aircraftRepository;
        this.aircraftMapper = aircraftMapper;
        this.airlineRepository = airlineRepository;
    }

    @Override
    public AircraftCreateResponseDto create(AircraftCreateRequestDto requestDto) {

        Airline airline = airlineRepository.findById(requestDto.getAirlineId())
                .orElseThrow(() -> new AirlineNotFoundException("Airline not found"));

        Aircraft aircraft = aircraftMapper.toEntity(requestDto);

        aircraft.setAirline(airline);

        return aircraftMapper.toDto(aircraftRepository.save(aircraft));
    }
}
