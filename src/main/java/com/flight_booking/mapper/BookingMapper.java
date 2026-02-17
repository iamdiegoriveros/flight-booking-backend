package com.flight_booking.mapper;

import com.flight_booking.booking.dto.BookingCreateRequestDto;
import com.flight_booking.booking.dto.BookingCreateResponseDto;
import com.flight_booking.booking.entity.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    Booking toEntity(BookingCreateRequestDto dto);
    BookingCreateResponseDto toDto(Booking entity);
}
