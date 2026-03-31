package com.flight_booking.flight.controller;

import com.flight_booking.flight.dto.FlightCreateRequestDto;
import com.flight_booking.flight.dto.FlightResponseDto;
import com.flight_booking.flight.service.FlightService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flight")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/create")
    public ResponseEntity<FlightResponseDto> create(
            @RequestBody FlightCreateRequestDto requestDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.create(requestDto));
    }

    @GetMapping
    public ResponseEntity<List<FlightResponseDto>> getAllFlight(
            @PageableDefault Pageable pageable) {
        return ResponseEntity.ok().body(flightService.getAllFlight(pageable));
    }
}
