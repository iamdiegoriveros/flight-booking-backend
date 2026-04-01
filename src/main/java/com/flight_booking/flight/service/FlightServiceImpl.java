package com.flight_booking.flight.service;

import com.flight_booking.aircraft.entity.Aircraft;
import com.flight_booking.aircraft.repository.AircraftRepository;
import com.flight_booking.exceptions.ResourceNotFoundException;
import com.flight_booking.flight.dto.*;
import com.flight_booking.flight.entity.Flight;
import com.flight_booking.flight.entity.FlightFare;
import com.flight_booking.flight.repository.FlightFareRepository;
import com.flight_booking.flight.repository.FlightRepository;
import com.flight_booking.flight.specification.FlightSpecificationBuilder;
import com.flight_booking.mapper.FlightFareMapper;
import com.flight_booking.mapper.FlightMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public FlightResponseDto create(FlightCreateRequestDto requestDto) {

        Aircraft aircraft = aircraftRepository.findById(requestDto.getAircraftId())
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found with id " + requestDto.getAircraftId()));


        Flight flight = flightMapper.toEntity(requestDto);

        flight.setAircraft(aircraft);
        flight.setAvailableSeats(aircraft.getCapacity());

        Flight flightDB = flightRepository.save(flight);

        List<FlightFareRequestDto> flightFareRequestDtos = requestDto.getFlightFare();

        List<FlightFareResponseDto> flightFaresResponseDto = flightFareRequestDtos.stream()
                .map(flightFareRequestDto -> {
                    FlightFare flightFare = flightFareMapper.toEntity(flightFareRequestDto);
                    flightFare.setFlight(flightDB);
                    return flightFareMapper.toDto(flightFareRepository.save(flightFare));
                })
                .toList();

        FlightResponseDto flightCreateResponseDto = flightMapper.toDto(flightDB);
        flightCreateResponseDto.setFlightFare(flightFaresResponseDto);

        return flightCreateResponseDto;
    }

    @Override
    public List<FlightResponseDto> getAllFlight(Pageable pageable) {

        return flightRepository.findAll(pageable)
                .stream()
                .map(flight -> flightMapper.toDto(flight))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightResponseDto> getByFilters(FlightFilterDto flightFilterDto, Pageable pageable) {

        Specification<Flight> specification = FlightSpecificationBuilder.build(flightFilterDto);

        return flightRepository.findAll(specification, pageable)
                .stream()
                .map(flight -> flightMapper.toDto(flight))
                .collect(Collectors.toList());
    }
}
