package com.flight_booking.booking.service;

import com.flight_booking.booking.dto.BookingCreateRequestDto;
import com.flight_booking.booking.dto.BookingCreateResponseDto;
import org.springframework.security.core.Authentication;

public interface BookingService {

    BookingCreateResponseDto create(BookingCreateRequestDto requestDto, Authentication authentication);
}
