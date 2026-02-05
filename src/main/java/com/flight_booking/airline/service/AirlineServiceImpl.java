package com.flight_booking.airline.service;

import com.flight_booking.airline.dto.AirlineCreateResponseDto;
import com.flight_booking.airline.dto.AirlineCreateResquestDto;
import com.flight_booking.airline.entity.Airline;
import com.flight_booking.airline.repository.AirlineRepository;
import com.flight_booking.mapper.AirlineMapper;
import org.springframework.stereotype.Service;

@Service
public class AirlineServiceImpl implements AirlineService{

    private final AirlineRepository airlineRepository;
    private final AirlineMapper airlineMapper;

    public AirlineServiceImpl(AirlineRepository airlineRepository, AirlineMapper airlineMapper) {
        this.airlineRepository = airlineRepository;
        this.airlineMapper = airlineMapper;
    }

    @Override
    public AirlineCreateResponseDto create(AirlineCreateResquestDto requestDto) {

        Airline airline = airlineMapper.toEntity(requestDto);
        return airlineMapper.toDto(airlineRepository.save(airline));
    }
}
