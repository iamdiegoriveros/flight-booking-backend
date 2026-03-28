package com.flight_booking.passenger.service;

import com.flight_booking.passenger.dto.PassengerCreateRequestDto;
import com.flight_booking.passenger.dto.PassengerCreateResponseDto;
import com.flight_booking.passenger.entity.Passenger;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PassengerService {

    PassengerCreateResponseDto save(PassengerCreateRequestDto responseDto);
    Map<String, Passenger> getExistingPassengerByDni(Collection<String> dnis);
    List<Passenger> saveAll(Collection<Passenger> passengers);
    Map<String, Passenger> saveAllMap(Collection<Passenger> passengers);

}
