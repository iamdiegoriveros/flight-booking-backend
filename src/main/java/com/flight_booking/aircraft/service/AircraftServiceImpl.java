package com.flight_booking.aircraft.service;

import com.flight_booking.aircraft.dto.AircraftCreateRequestDto;
import com.flight_booking.aircraft.dto.AircraftCreateResponseDto;
import com.flight_booking.aircraft.entity.Aircraft;
import com.flight_booking.aircraft.repository.AircraftRepository;
import com.flight_booking.mapper.AircraftMapper;
import org.springframework.stereotype.Service;

@Service
public class AircraftServiceImpl implements AircraftService{

    private final AircraftRepository aircraftRepository;
    private final AircraftMapper aircraftMapper;

    public AircraftServiceImpl(AircraftRepository aircraftRepository, AircraftMapper aircraftMapper) {
        this.aircraftRepository = aircraftRepository;
        this.aircraftMapper = aircraftMapper;
    }

    @Override
    public AircraftCreateResponseDto create(AircraftCreateRequestDto requestDto) {

        Aircraft aircraft = aircraftMapper.toEntity(requestDto);

        return aircraftMapper.toDto(aircraftRepository.save(aircraft));
    }
}
