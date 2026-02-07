package com.flight_booking.flight.service;

import com.flight_booking.aircraft.entity.Aircraft;
import com.flight_booking.aircraft.repository.AircraftRepository;
import com.flight_booking.exceptions.ResourceNotFoundException;
import com.flight_booking.flight.dto.FlightCreateRequestDto;
import com.flight_booking.flight.dto.FlightCreateResponseDto;
import com.flight_booking.flight.dto.FlightFareRequestDto;
import com.flight_booking.flight.dto.FlightFareResponseDto;
import com.flight_booking.flight.entity.Flight;
import com.flight_booking.flight.entity.FlightFare;
import com.flight_booking.flight.repository.FlightFareRepository;
import com.flight_booking.flight.repository.FlightRepository;
import com.flight_booking.mapper.FlightFareMapper;
import com.flight_booking.mapper.FlightMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService{

    private final FlightRepository flightRepository;
    private final AircraftRepository aircraftRepository;
    private final FlightFareRepository flightFareRepository;
    private final FlightMapper flightMapper;
    private final FlightFareMapper flightFareMapper;

    public FlightServiceImpl(FlightRepository flightRepository, AircraftRepository aircraftRepository, FlightFareRepository flightFareRepository, FlightMapper flightMapper, FlightFareMapper flightFareMapper) {
        this.flightRepository = flightRepository;
        this.aircraftRepository = aircraftRepository;
        this.flightFareRepository = flightFareRepository;
        this.flightMapper = flightMapper;
        this.flightFareMapper = flightFareMapper;
    }

    @Override
    @Transactional
    public FlightCreateResponseDto create(FlightCreateRequestDto requestDto) {

        Aircraft aircraft = aircraftRepository.findById(requestDto.getAircraftId())
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found with id " + requestDto.getAircraftId()));


        Flight flight = flightMapper.toEntity(requestDto);

        flight.setAircraft(aircraft);

        Flight flightDB = flightRepository.save(flight);

        List<FlightFareRequestDto> flightFareRequestDtos = requestDto.getFlightFare();

        List<FlightFareResponseDto> flightFaresResponseDto = flightFareRequestDtos.stream()
                .map(flightFareRequestDto -> {
                    FlightFare flightFare = flightFareMapper.toEntity(flightFareRequestDto);
                    flightFare.setFlight(flightDB);
                    return flightFareMapper.toDto(flightFareRepository.save(flightFare));
                })
                .toList();

        FlightCreateResponseDto flightCreateResponseDto = flightMapper.toDto(flightDB);
        flightCreateResponseDto.setFlightFare(flightFaresResponseDto);

        return flightCreateResponseDto;
    }
}
