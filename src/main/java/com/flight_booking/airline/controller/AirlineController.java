package com.flight_booking.airline.controller;

import com.flight_booking.airline.dto.AirlineResponseDto;
import com.flight_booking.airline.dto.AirlineCreateResquestDto;
import com.flight_booking.airline.service.AirlineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airline")
public class AirlineController {

    private final AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @PostMapping("/create")
    public ResponseEntity<AirlineResponseDto> create(
            @RequestBody AirlineCreateResquestDto resquestDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(airlineService.create(resquestDto));
    }

    @GetMapping
    public ResponseEntity<List<AirlineResponseDto>> getAllAirline() {
        return ResponseEntity.ok().body(airlineService.getAllAirline());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAirline(@PathVariable Long id) {
        airlineService.deleteAirlineById(id);
        return ResponseEntity.ok().body("Airline deleted with id " + id);
    }
}
