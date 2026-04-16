package com.flight_booking.passenger.service;

import com.flight_booking.mapper.PassengerMapper;
import com.flight_booking.passenger.dto.PassengerCreateRequestDto;
import com.flight_booking.passenger.dto.PassengerResponseDto;
import com.flight_booking.passenger.entity.Passenger;
import com.flight_booking.passenger.repository.PassengerRepository;
import com.flight_booking.ticket.dto.TicketCreateRequestDto;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PassengerServiceImpl implements PassengerService{

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    public PassengerServiceImpl(PassengerRepository passengerRepository, PassengerMapper passengerMapper) {
        this.passengerRepository = passengerRepository;
        this.passengerMapper = passengerMapper;
    }

    @Override
    public PassengerResponseDto save(PassengerCreateRequestDto responseDto) {
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

    @Override
    public Map<String, Passenger> saveNewPassenger(List<PassengerCreateRequestDto> passengerDto) {

        Set<String> dnis = passengerDto.stream()
                .map(passenger -> passenger.getDni())
                .collect(Collectors.toSet());

        Map<String,Passenger> passengersInDB = this.getExistingPassengerByDni(dnis);

        List<Passenger> newPassengers = buildNewPassengersEntity(passengerDto, passengersInDB);
        Map<String, Passenger> savedNewPassenger = this.saveAllMap(newPassengers);

        Map<String, Passenger> allPassengerInDB = new HashMap<>(passengersInDB);
        allPassengerInDB.putAll(savedNewPassenger);

        return allPassengerInDB;
    }


    private List<Passenger> buildNewPassengersEntity(List<PassengerCreateRequestDto> passengersDto, Map<String, Passenger> passengerInDbMap){

        List<Passenger> passengers = new ArrayList<>();

        for (PassengerCreateRequestDto passengerDto:passengersDto) {
            if (!passengerInDbMap.containsKey(passengerDto.getDni())) {
                Passenger passenger = passengerMapper.toEntity(passengerDto);
                passengers.add(passenger);
            }
        }
        return passengers;
    }
}
