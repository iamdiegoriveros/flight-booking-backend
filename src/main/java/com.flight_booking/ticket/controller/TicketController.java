package com.flight_booking.ticket.controller;

import com.flight_booking.ticket.dto.TicketCreateRequestDto;
import com.flight_booking.ticket.dto.TicketResponseDto;
import com.flight_booking.ticket.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{flightId}")
    public ResponseEntity<List<TicketResponseDto>> getTicketsByFlightId(
            @PathVariable Long flightId) {

        return ResponseEntity.ok().body(ticketService.getTicketsByFlightId(flightId));
    }

    //@PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<List<TicketResponseDto>> save(@RequestBody TicketCreateRequestDto ticketRequestDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.save(ticketRequestDto));
    }
}
