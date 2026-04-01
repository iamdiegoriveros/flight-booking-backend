package com.flight_booking.mapper;

import com.flight_booking.booking.dto.BookingCreateRequestDto;
import com.flight_booking.booking.dto.BookingResponseDto;
import com.flight_booking.booking.entity.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    Booking toEntity(BookingCreateRequestDto dto);
    BookingResponseDto toDto(Booking entity);
}
