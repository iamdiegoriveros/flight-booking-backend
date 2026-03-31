package com.flight_booking.mapper;

import com.flight_booking.flight.dto.FlightCreateRequestDto;
import com.flight_booking.flight.dto.FlightResponseDto;
import com.flight_booking.flight.entity.Flight;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    Flight toEntity(FlightCreateRequestDto dto);
    FlightResponseDto toDto(Flight entity);
}
