package com.flight_booking.passenger.controller;

import com.flight_booking.passenger.dto.PassengerResponseDto;
import com.flight_booking.passenger.service.PassengerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController
//public class PassengerController {
//
//    private final PassengerService passengerService;
//
//    public PassengerController(PassengerService passengerService) {
//        this.passengerService = passengerService;
//    }
//
//    @GetMapping("/{flightId}")
//    public ResponseEntity<List<PassengerResponseDto>> getPassengerByFlightId(
//            @PathVariable Long flightId) {
//
//        return ResponseEntity.ok().body(passengerService.getPassengersByFlightId(flightId));
//    }
//}
