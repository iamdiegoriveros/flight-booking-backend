package com.flight_booking.ticket.service;

import com.flight_booking.flight.entity.Flight;
import com.flight_booking.flight.repository.FlightRepository;
import com.flight_booking.mapper.TicketMapper;
import com.flight_booking.passenger.repository.PassengerRepository;
import com.flight_booking.ticket.dto.TicketResponseDto;
import com.flight_booking.ticket.entity.Ticket;
import com.flight_booking.ticket.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService{

    private final FlightRepository flightRepository;
    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;

    private final TicketMapper ticketMapper;

    public TicketServiceImpl(FlightRepository flightRepository, TicketRepository ticketRepository, PassengerRepository passengerRepository, TicketMapper ticketMapper) {
        this.flightRepository = flightRepository;
        this.ticketRepository = ticketRepository;
        this.passengerRepository = passengerRepository;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public List<TicketResponseDto> getTicketsByFlightId(Long flightId) {

        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + flightId));

        List<Ticket> tickets = ticketRepository.findByFlight(flight);

        return tickets.stream()
                .map(ticket -> ticketMapper.toDto(ticket))
                .collect(Collectors.toList());
    }
}
