package com.flight_booking.booking.service;

import com.flight_booking.booking.dto.BookingCreateRequestDto;
import com.flight_booking.booking.dto.BookingResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface BookingService {

    BookingResponseDto create(BookingCreateRequestDto requestDto, Authentication authentication);

    List<BookingResponseDto> getMyBookings(Authentication authentication, Pageable pageable);
}
