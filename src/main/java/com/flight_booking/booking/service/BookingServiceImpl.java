package com.flight_booking.booking.service;

import com.flight_booking.booking.dto.*;
import com.flight_booking.booking.entity.Booking;
import com.flight_booking.booking.validators.BookingValidationContext;
import com.flight_booking.booking.validators.BookingValidator;
import com.flight_booking.flight.service.FlightFareService;
import com.flight_booking.passenger.dto.PassengerCreateRequestDto;
import com.flight_booking.passenger.service.PassengerService;
import com.flight_booking.ticket.dto.TicketBuildResult;
import com.flight_booking.ticket.dto.TicketCreateRequestDto;
import com.flight_booking.ticket.dto.TicketCreateResponseDto;
import com.flight_booking.ticket.entity.Ticket;
import com.flight_booking.booking.repository.BookingRepository;
import com.flight_booking.exceptions.ResourceNotFoundException;
import com.flight_booking.flight.entity.Flight;
import com.flight_booking.flight.entity.FlightFare;
import com.flight_booking.flight.repository.FlightRepository;
import com.flight_booking.mapper.BookingMapper;
import com.flight_booking.mapper.FlightMapper;
import com.flight_booking.mapper.TicketMapper;
import com.flight_booking.passenger.entity.Passenger;
import com.flight_booking.ticket.service.TicketBuilder;
import com.flight_booking.user.entity.User;
import com.flight_booking.user.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService{

    private final PassengerService passengerService;
    private final FlightFareService flightFareService;

    private final TicketBuilder ticketBuilder;

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    private final List<BookingValidator> bookingValidators;

    private final FlightMapper flightMapper;
    private final TicketMapper ticketMapper;
    private final BookingMapper bookingMapper;

    public BookingServiceImpl(PassengerService passengerService, FlightFareService flightFareService, TicketBuilder ticketBuilder, BookingRepository bookingRepository, FlightRepository flightRepository, UserRepository userRepository, List<BookingValidator> bookingValidators, FlightMapper flightMapper, TicketMapper ticketMapper, BookingMapper bookingMapper) {
        this.passengerService = passengerService;
        this.flightFareService = flightFareService;
        this.ticketBuilder = ticketBuilder;
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
        this.bookingValidators = bookingValidators;
        this.flightMapper = flightMapper;
        this.ticketMapper = ticketMapper;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public List<BookingResponseDto> getMyBookings(Authentication authentication, Pageable pageable) {

        String usernameUser = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByUsername(usernameUser)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + usernameUser));

        return bookingRepository.findBookingByUser(user, pageable).stream()
                .map(booking -> bookingMapper.toDto(booking))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookingResponseDto create(BookingCreateRequestDto requestDto,
                                     Authentication authentication) {

        String usernameUser = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByUsername(usernameUser)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + usernameUser));

        // get flight
        Flight flight = flightRepository.findById(requestDto.getFlightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id " + requestDto.getFlightId()));

        BookingValidationContext context = new BookingValidationContext(flight, requestDto.getTickets());
        bookingValidators.forEach(validator -> validator.validate(context));

        // load fares
        Map<String, FlightFare> fareMap = flightFareService.getFlightFareMap(requestDto.getFlightId());

        // get dnis existentes
        Set<String> dnis = requestDto.getTickets().stream()
                .map(ticket -> ticket.getPassengers().getDni())
                .collect(Collectors.toSet());
        Map<String,Passenger> passengersInDB = passengerService.getExistingPassengerByDni(dnis);

        List<Passenger> newPassengers = buildNewPassengersEntity(requestDto.getTickets(), passengersInDB);
        Map<String, Passenger> savedNewPassenger = passengerService.saveAllMap(newPassengers);

        Map<String, Passenger> allPassengerInDb = new HashMap<>(passengersInDB);
        allPassengerInDb.putAll(savedNewPassenger);

        TicketBuildResult ticketBuildResult =  ticketBuilder.build(requestDto.getTickets(),flight,fareMap,allPassengerInDb);



        // create booking in DataBase
        Booking bookingDB = saveBooking(flight, ticketBuildResult, user);


        return buildBookingResponse(bookingDB, bookingDB.getTickets(), flight.getId());
    }

    private List<Passenger> buildNewPassengersEntity(List<TicketCreateRequestDto> ticketsDto, Map<String, Passenger> passengerInDbMap){

        List<Passenger> passengers = new ArrayList<>();

        for (TicketCreateRequestDto ticketDto:ticketsDto) {
            if (!passengerInDbMap.containsKey(ticketDto.getPassengers().getDni())) {
                Passenger passenger = buildPassengerEntity(ticketDto.getPassengers());
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

    private Booking saveBooking(Flight flight,TicketBuildResult ticketBuildResult, User user) {

        flight.reserveSeats(ticketBuildResult.getAllTickets().size());

        Booking booking = new Booking();
        booking.setCurrency("USD");
        booking.setTotalPrice(ticketBuildResult.getTotalPrice());
        booking.setStatus("RESERVED");
        booking.addTickets(ticketBuildResult.getAllTickets());
        booking.setFlight(flight);
        booking.setUser(user);

        return bookingRepository.save(booking);
    }

    private BookingResponseDto buildBookingResponse(
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
        BookingResponseDto bookingCreateResponseDto = new BookingResponseDto();
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
