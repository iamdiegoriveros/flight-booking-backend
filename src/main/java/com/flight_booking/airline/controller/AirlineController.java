package com.flight_booking.airline.controller;

import com.flight_booking.airline.dto.AirlineCreateResponseDto;
import com.flight_booking.airline.dto.AirlineCreateResquestDto;
import com.flight_booking.airline.service.AirlineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/airline")
public class AirlineController {

    private final AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @PostMapping("/create")
    public ResponseEntity<AirlineCreateResponseDto> create(
            @RequestBody AirlineCreateResquestDto resquestDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(airlineService.create(resquestDto));
    }
}
