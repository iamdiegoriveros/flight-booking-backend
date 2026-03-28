package com.flight_booking.booking.service;

import com.flight_booking.booking.dto.*;
import com.flight_booking.booking.entity.Booking;
import com.flight_booking.exceptions.NoSeatAvailableException;
import com.flight_booking.flight.service.FlightFareService;
import com.flight_booking.passenger.dto.PassengerCreateRequestDto;
import com.flight_booking.passenger.service.PassengerService;
import com.flight_booking.ticket.dto.TicketBuildResult;
import com.flight_booking.ticket.dto.TicketCreateRequestDto;
import com.flight_booking.ticket.dto.TicketCreateResponseDto;
import com.flight_booking.ticket.entity.Ticket;
import com.flight_booking.booking.repository.BookingRepository;
import com.flight_booking.exceptions.BadRequestException;
import com.flight_booking.exceptions.ResourceNotFoundException;
import com.flight_booking.flight.entity.Flight;
import com.flight_booking.flight.entity.FlightFare;
import com.flight_booking.flight.repository.FlightRepository;
import com.flight_booking.mapper.BookingMapper;
import com.flight_booking.mapper.FlightMapper;
import com.flight_booking.mapper.TicketMapper;
import com.flight_booking.passenger.entity.Passenger;
import com.flight_booking.ticket.service.TicketBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BookingServiceImpl implements BookingService{

    private final PassengerService passengerService;
    private final FlightFareService flightFareService;

    private final TicketBuilder ticketBuilder;

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;

    private final FlightMapper flightMapper;
    private final TicketMapper ticketMapper;
    private final BookingMapper bookingMapper;

    public BookingServiceImpl(PassengerService passengerService, FlightFareService flightFareService, TicketBuilder ticketBuilder, BookingRepository bookingRepository, FlightRepository flightRepository, FlightMapper flightMapper, TicketMapper ticketMapper, BookingMapper bookingMapper) {
        this.passengerService = passengerService;
        this.flightFareService = flightFareService;
        this.ticketBuilder = ticketBuilder;
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.flightMapper = flightMapper;
        this.ticketMapper = ticketMapper;
        this.bookingMapper = bookingMapper;
    }

    @Override
    @Transactional
    public BookingCreateResponseDto create(BookingCreateRequestDto requestDto) {

        // get flight
        Flight flight = flightRepository.findById(requestDto.getFlightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id " + requestDto.getFlightId()));

        if (!flight.seatAvailable()) {
            throw new NoSeatAvailableException("No seat available flight: " + flight.getId());
        }

        // load fares
        Map<String, FlightFare> fareMap = flightFareService.getFlightFareMap(requestDto.getFlightId());

        // validate dnis
        Set<String> dnis = validateUniqueDni(requestDto);

        // get dnis existentes
        Map<String,Passenger> passengersInBD = passengerService.getExistingPassengerByDni(dnis);

        List<Passenger> newPassengers = buildNewPassengersEntity(requestDto.getTickets(), passengersInBD);
        Map<String, Passenger> savedNewPassenger = passengerService.saveAllMap(newPassengers);

        Map<String, Passenger> allPassengerInDb = new HashMap<>(passengersInBD);
        allPassengerInDb.putAll(savedNewPassenger);

        TicketBuildResult ticketBuildResult =  ticketBuilder.build(requestDto.getTickets(),flight,fareMap,allPassengerInDb);

        // create booking in DataBase
        Booking bookingDB = saveBooking(ticketBuildResult);


        return buildBookingResponse(bookingDB, bookingDB.getTickets(), flight.getId());
    }

    private Set<String> validateUniqueDni(BookingCreateRequestDto requestDto) {
        Set<String> dnisU = new HashSet<>();
        for (TicketCreateRequestDto ticket:requestDto.getTickets()){
            String dni = ticket.getPassengerCreateRequestDto().getDni();
            if (!dnisU.contains(dni)) {
                dnisU.add(dni);
            } else {
                throw new BadRequestException("Duplicated dni");
            }
        }

        return dnisU;
    }

    private List<Passenger> buildNewPassengersEntity(List<TicketCreateRequestDto> ticketsDto, Map<String, Passenger> passengerInDbMap){

        List<Passenger> passengers = new ArrayList<>();

        for (TicketCreateRequestDto ticketDto:ticketsDto) {
            if (!passengerInDbMap.containsKey(ticketDto.getPassengerCreateRequestDto().getDni())) {
                Passenger passenger = buildPassengerEntity(ticketDto.getPassengerCreateRequestDto());
                passengers.add(passenger);
            }
        }
        return passengers;
    }

    private Passenger buildPassengerEntity(PassengerCreateRequestDto passengerDto) {
        Passenger passenger = new Passenger();
        passenger.setFirstName(passengerDto.getFirstName());
        passenger.setLastName(passengerDto.getLastName());
        passenger.setDni(passengerDto.getDni());

        return passenger;
    }

    private Booking saveBooking(TicketBuildResult ticketBuildResult) {

        Booking booking = new Booking();
        booking.setCurrency("USD");
        booking.setTotalPrice(ticketBuildResult.getTotalPrice());
        booking.setStatus("RESERVED");
        booking.addTickets(ticketBuildResult.getAllTickets());
        return bookingRepository.save(booking);
    }

    private BookingCreateResponseDto buildBookingResponse(
            Booking bookingDB,
            List<Ticket> ticketsDB,
            Long flightId) {

        List<TicketCreateResponseDto> ticketsCreateResponseDto = new ArrayList<>();

        for (Ticket ticket:ticketsDB) {
            TicketCreateResponseDto ticketDto = ticketMapper.toDto(ticket);
            ticketDto.setFlightId(flightId);
            ticketsCreateResponseDto.add(ticketDto);
        }

        // create booking dto
        BookingCreateResponseDto bookingCreateResponseDto = new BookingCreateResponseDto();
        bookingCreateResponseDto.setId(bookingDB.getId());
        bookingCreateResponseDto.setCreatedAt(bookingDB.getCreatedAt());
        bookingCreateResponseDto.setTotalPrice(bookingDB.getTotalPrice());
        bookingCreateResponseDto.setCurrency(bookingDB.getCurrency());
        bookingCreateResponseDto.setStatus("RESERVED");
        bookingCreateResponseDto.setFlightId(flightId);
        bookingCreateResponseDto.setTickets(ticketsCreateResponseDto);

        return bookingCreateResponseDto;
    }
}
