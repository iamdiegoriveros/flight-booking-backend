package com.flight_booking.ticket.service;

import com.flight_booking.booking.entity.Booking;
import com.flight_booking.booking.entity.BookingStatus;
import com.flight_booking.booking.service.BookingService;
import com.flight_booking.exceptions.ResourceNotFoundException;
import com.flight_booking.flight.entity.Flight;
import com.flight_booking.flight.repository.FlightRepository;
import com.flight_booking.flight.service.FlightService;
import com.flight_booking.mapper.PassengerMapper;
import com.flight_booking.mapper.TicketMapper;
import com.flight_booking.passenger.dto.PassengerCreateRequestDto;
import com.flight_booking.passenger.entity.Passenger;
import com.flight_booking.passenger.repository.PassengerRepository;
import com.flight_booking.passenger.service.PassengerService;
import com.flight_booking.ticket.builder.TicketBuildResult;
import com.flight_booking.ticket.builder.TicketBuilder;
import com.flight_booking.ticket.dto.TicketCreateRequestDto;
import com.flight_booking.ticket.dto.TicketResponseDto;
import com.flight_booking.ticket.entity.Ticket;
import com.flight_booking.ticket.repository.TicketRepository;
import com.flight_booking.ticket.validator.TicketValidationContext;
import com.flight_booking.ticket.validator.TicketValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AutoPopulatingList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    private final FlightService flightService;
    private final TicketRepository ticketRepository;
    private final PassengerService passengerService;
    private final BookingService bookingService;
    private final FlightRepository flightRepository;

    private final TicketMapper ticketMapper;
    private final PassengerMapper passengerMapper;

    private final TicketBuilder ticketBuilder;

    private final List<TicketValidator> ticketValidators;

    public TicketServiceImpl(FlightRepository flightRepository, FlightService flightService, TicketRepository ticketRepository, PassengerService passengerService, BookingService bookingService, TicketMapper ticketMapper, PassengerMapper passengerMapper, TicketBuilder ticketBuilder, List<TicketValidator> ticketValidators) {
        this.flightRepository = flightRepository;
        this.flightService = flightService;
        this.ticketRepository = ticketRepository;
        this.passengerService = passengerService;
        this.bookingService = bookingService;
        this.ticketMapper = ticketMapper;
        this.passengerMapper = passengerMapper;
        this.ticketBuilder = ticketBuilder;
        this.ticketValidators = ticketValidators;
    }

    @Override
    public List<TicketResponseDto> getTicketsByFlightId(Long flightId) {

        if (!flightRepository.existsById(flightId)) {
            throw new ResourceNotFoundException("Flight not found with id: " + flightId);
        }

        List<Ticket> tickets = ticketRepository.findByFlightId(flightId);

        return tickets.stream()
                .map(ticket -> ticketMapper.toDto(ticket))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<TicketResponseDto> save(TicketCreateRequestDto requestDto) {

        Booking booking = bookingService.getBookingEntityById(requestDto.getBookingId());

        TicketValidationContext validationContext = new TicketValidationContext(booking,
                requestDto.getPassengers());
        ticketValidators.forEach(validator -> validator.validate(validationContext));

        Map<String, Passenger> savedPassengers = passengerService.saveNewPassenger(requestDto.getPassengers());

        List<Ticket> tickets = ticketBuilder.build(booking, savedPassengers);

        List<Ticket> savedTickets = ticketRepository.saveAll(tickets);

        booking.addTickets(savedTickets);

        booking.setStatus(BookingStatus.WAITING_PAYMENT);

        return buildResponse(savedTickets);
    }

    private List<TicketResponseDto> buildResponse(List<Ticket> tickets) {

        List<TicketResponseDto> ticketsResponseDto = new ArrayList<>();

        for (Ticket ticket: tickets) {
            TicketResponseDto ticketResponseDto = new TicketResponseDto();
            ticketResponseDto.setId(ticket.getId());
            ticketResponseDto.setTravelClass(ticket.getTravelClass());
            ticketResponseDto.setSeatCode(ticket.getSeatCode());
            ticketResponseDto.setPassenger(passengerMapper.toDto(ticket.getPassenger()));
            ticketResponseDto.setFlightId(ticket.getFlight().getId());
            ticketResponseDto.setStatus(ticket.getStatus());
            ticketResponseDto.setBookingId(ticket.getBooking().getId());

            ticketsResponseDto.add(ticketResponseDto);
        }

        return ticketsResponseDto;
    }
}
