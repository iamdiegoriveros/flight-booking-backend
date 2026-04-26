package com.flight_booking.mapper;

import com.flight_booking.flight.dto.FlightFareRequestDto;
import com.flight_booking.flight.dto.FlightFareResponseDto;
import com.flight_booking.flight.entity.FlightFare;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightFareMapper {

    FlightFare toEntity(FlightFareRequestDto requestDto);
    FlightFareResponseDto toDto(FlightFare entity);
}
