package com.flight_booking.ticket.service;

import com.flight_booking.ticket.dto.TicketResponseDto;
import com.flight_booking.ticket.entity.Ticket;

import java.util.List;

public interface TicketService {

    List<TicketResponseDto> getTicketsByFlightId(Long flightId);
}
