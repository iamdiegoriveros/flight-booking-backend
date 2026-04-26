package com.flight_booking.flight.controller;

import com.flight_booking.flight.dto.FlightCreateRequestDto;
import com.flight_booking.flight.dto.FlightFilterDto;
import com.flight_booking.flight.dto.FlightResponseDto;
import com.flight_booking.flight.entity.Flight;
import com.flight_booking.flight.service.FlightService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flight")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<FlightResponseDto> create(
            @RequestBody FlightCreateRequestDto requestDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.create(requestDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<FlightResponseDto>> getFlights(
            FlightFilterDto flightFilterDto,
            @PageableDefault Pageable pageable) {
        return ResponseEntity.ok().body(flightService.getByFilters(flightFilterDto ,pageable));
    }
}
