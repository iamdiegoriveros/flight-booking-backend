package com.flight_booking.aircraft.controller;

import com.flight_booking.aircraft.dto.AircraftCreateRequestDto;
import com.flight_booking.aircraft.dto.AircraftCreateResponseDto;
import com.flight_booking.aircraft.service.AircraftService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aircraft")
public class AircraftController {

    private final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @PostMapping("/create")
    public ResponseEntity<AircraftCreateResponseDto> create(
            @RequestBody AircraftCreateRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(aircraftService.create(requestDto));
    }
}
