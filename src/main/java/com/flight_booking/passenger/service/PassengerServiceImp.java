package com.flight_booking.passenger.service;

import com.flight_booking.mapper.PassengerMapper;
import com.flight_booking.passenger.dto.PassengerCreateRequestDto;
import com.flight_booking.passenger.dto.PassengerCreateResponseDto;
import com.flight_booking.passenger.entity.Passenger;
import com.flight_booking.passenger.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PassengerServiceImp implements PassengerService{

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    public PassengerServiceImp(PassengerRepository passengerRepository, PassengerMapper passengerMapper) {
        this.passengerRepository = passengerRepository;
        this.passengerMapper = passengerMapper;
    }

    @Override
    public PassengerCreateResponseDto save(PassengerCreateRequestDto responseDto) {
        Passenger passenger = passengerMapper.toEntity(responseDto);
        return passengerMapper.toDto(passengerRepository.save(passenger));
    }

    @Override
    public Map<String,Passenger> getExistingPassengerByDni(Collection<String> dnis) {
        return  passengerRepository.findByDniIn(dnis).stream()
                .collect(Collectors.toMap(Passenger::getDni, passenger -> passenger));
    }

    @Override
    public List<Passenger> saveAll(Collection<Passenger> passengers) {
        return passengerRepository.saveAll(passengers);
    }

    @Override
    public Map<String, Passenger> saveAllMap(Collection<Passenger> passengers) {
        List<Passenger> savedPassengers = this.saveAll(passengers);
        return savedPassengers.stream()
                .collect(Collectors.toMap(Passenger::getDni, passenger -> passenger));
    }


}
