package com.flight_booking.mapper;

import com.flight_booking.airline.dto.AirlineResponseDto;
import com.flight_booking.airline.dto.AirlineCreateResquestDto;
import com.flight_booking.airline.entity.Airline;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AirlineMapper {

    Airline toEntity(AirlineCreateResquestDto dto);
    AirlineResponseDto toDto(Airline entity);
}
