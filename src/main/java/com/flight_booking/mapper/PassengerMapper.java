package com.flight_booking.mapper;

import com.flight_booking.passenger.dto.PassengerCreateRequestDto;
import com.flight_booking.passenger.dto.PassengerCreateResponseDto;
import com.flight_booking.passenger.entity.Passenger;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

    Passenger toEntity(PassengerCreateRequestDto dto);
    PassengerCreateResponseDto toDto(Passenger entity);
}
