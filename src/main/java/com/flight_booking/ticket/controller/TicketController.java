package com.flight_booking.ticket.controller;

import com.flight_booking.ticket.dto.TicketResponseDto;
import com.flight_booking.ticket.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<List<TicketResponseDto>> getTicketsByFlightId(
            @PathVariable Long flightId) {

        return ResponseEntity.ok().body(ticketService.getTicketsByFlightId(flightId));
    }
}
