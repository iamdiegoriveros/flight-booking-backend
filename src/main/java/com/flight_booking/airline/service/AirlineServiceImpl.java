package com.flight_booking.airline.service;

import com.flight_booking.airline.dto.AirlineResponseDto;
import com.flight_booking.airline.dto.AirlineCreateResquestDto;
import com.flight_booking.airline.entity.Airline;
import com.flight_booking.airline.repository.AirlineRepository;
import com.flight_booking.exceptions.ResourceNotFoundException;
import com.flight_booking.mapper.AirlineMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirlineServiceImpl implements AirlineService{

    private final AirlineRepository airlineRepository;
    private final AirlineMapper airlineMapper;

    public AirlineServiceImpl(AirlineRepository airlineRepository, AirlineMapper airlineMapper) {
        this.airlineRepository = airlineRepository;
        this.airlineMapper = airlineMapper;
    }

    @Override
    public AirlineResponseDto create(AirlineCreateResquestDto requestDto) {

        Airline airline = airlineMapper.toEntity(requestDto);
        return airlineMapper.toDto(airlineRepository.save(airline));
    }

    @Override
    public List<AirlineResponseDto> getAllAirline() {
        return airlineRepository.findAll()
                .stream()
                .map(airline -> airlineMapper.toDto(airline))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAirlineById(Long id) {
        try{
            airlineRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Airline not found with id " + id);
        }
    }


}
