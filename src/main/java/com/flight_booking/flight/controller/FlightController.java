package com.flight_booking.flight.controller;

import com.flight_booking.flight.dto.FlightCreateRequestDto;
import com.flight_booking.flight.dto.FlightCreateResponseDto;
import com.flight_booking.flight.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flight")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/create")
    public ResponseEntity<FlightCreateResponseDto> create(
            @RequestBody FlightCreateRequestDto requestDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.create(requestDto));
    }
}
