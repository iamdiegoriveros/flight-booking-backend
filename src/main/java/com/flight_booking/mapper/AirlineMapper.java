package com.flight_booking.mapper;

import com.flight_booking.airline.dto.AirlineCreateResponseDto;
import com.flight_booking.airline.dto.AirlineCreateResquestDto;
import com.flight_booking.airline.entity.Airline;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AirlineMapper {

    Airline toEntity(AirlineCreateResquestDto dto);
    AirlineCreateResponseDto toDto(Airline entity);
}
