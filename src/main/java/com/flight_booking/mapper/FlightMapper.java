package com.flight_booking.mapper;

import com.flight_booking.flight.dto.FlightCreateRequestDto;
import com.flight_booking.flight.dto.FlightCreateResponseDto;
import com.flight_booking.flight.entity.Flight;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    Flight toEntity(FlightCreateRequestDto dto);
    FlightCreateResponseDto toDto(Flight entity);
}
