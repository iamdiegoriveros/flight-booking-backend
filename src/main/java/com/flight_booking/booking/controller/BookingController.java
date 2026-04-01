package com.flight_booking.booking.controller;

import com.flight_booking.booking.dto.BookingCreateRequestDto;
import com.flight_booking.booking.dto.BookingResponseDto;
import com.flight_booking.booking.service.BookingService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public ResponseEntity<BookingResponseDto> create(
            @RequestBody BookingCreateRequestDto requestDto,
            Authentication authentication){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.create(requestDto, authentication));
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<List<BookingResponseDto>> getMyBookings(
            Authentication authentication,
            @PageableDefault Pageable pageable) {

        return ResponseEntity.ok().body(bookingService.getMyBookings(authentication, pageable));
    }
}
