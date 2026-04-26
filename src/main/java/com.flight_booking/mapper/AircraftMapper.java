package com.flight_booking.mapper;

import com.flight_booking.aircraft.dto.AircraftCreateRequestDto;
import com.flight_booking.aircraft.dto.AircraftCreateResponseDto;
import com.flight_booking.aircraft.entity.Aircraft;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AircraftMapper {

    Aircraft toEntity(AircraftCreateRequestDto dto);
    AircraftCreateResponseDto toDto(Aircraft entity);
}
