package com.flight_booking.booking.service;

import com.flight_booking.booking.dto.*;
import com.flight_booking.booking.entity.Booking;
import com.flight_booking.booking.entity.BookingStatus;
import com.flight_booking.booking.validators.BookingValidationContext;
import com.flight_booking.booking.validators.BookingValidator;
import com.flight_booking.flight.service.FlightFareService;
import com.flight_booking.flight.service.FlightService;
import com.flight_booking.passenger.dto.PassengerCreateRequestDto;
import com.flight_booking.passenger.service.PassengerService;
import com.flight_booking.ticket.builder.TicketBuildResult;
import com.flight_booking.ticket.dto.TicketCreateRequestDto;
import com.flight_booking.ticket.dto.TicketResponseDto;
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
import com.flight_booking.ticket.builder.TicketBuilder;
import com.flight_booking.user.entity.User;
import com.flight_booking.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService{

    private final PassengerService passengerService;
    private final FlightFareService flightFareService;
    private final FlightService flightService;

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    private final List<BookingValidator> bookingValidators;

    private final FlightMapper flightMapper;
    private final TicketMapper ticketMapper;
    private final BookingMapper bookingMapper;

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    public BookingServiceImpl(PassengerService passengerService, FlightFareService flightFareService, FlightService flightService, BookingRepository bookingRepository, UserRepository userRepository, List<BookingValidator> bookingValidators, FlightMapper flightMapper, TicketMapper ticketMapper, BookingMapper bookingMapper) {
        this.passengerService = passengerService;
        this.flightFareService = flightFareService;
        this.flightService = flightService;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.bookingValidators = bookingValidators;
        this.flightMapper = flightMapper;
        this.ticketMapper = ticketMapper;
        this.bookingMapper = bookingMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Booking getBookingEntityById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
    }

    @Override
    @Transactional(readOnly = true)
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

        Flight flight = flightService.getFlightEntity(requestDto.getFlightId());

        BookingValidationContext context = new BookingValidationContext(flight, requestDto.getPassengerSeat().getSeatCount());
        bookingValidators.forEach(validator -> validator.validate(context));

        BigDecimal totalPrice = calculatePrice(requestDto);

        flightService.reserveSeat(requestDto.getFlightId(), requestDto.getPassengerSeat().getSeatCount());

        Booking savedBooking = saveBooking(flight, user, totalPrice, requestDto);

        return buildBookingResponse(savedBooking , flight.getId());
    }



    private BigDecimal calculatePrice(BookingCreateRequestDto requestDto) {
        Map<String, FlightFare> fareMap = flightFareService.getFlightFareMap(requestDto.getFlightId());
        BigDecimal totalPrice;
        FlightFare flightFare = fareMap.get(requestDto.getPassengerSeat().getTravelClass());
        totalPrice = flightFare.getBasePrice().multiply(BigDecimal.valueOf(requestDto.getPassengerSeat().getSeatCount()));
        return totalPrice;
    }

    //    @Override
//    @Transactional
//    public BookingResponseDto create(BookingCreateRequestDto requestDto,
//                                     Authentication authentication) {
//
//        String usernameUser = ((UserDetails) authentication.getPrincipal()).getUsername();
//        User user = userRepository.findByUsername(usernameUser)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + usernameUser));
//
//        Flight flight = flightService.getFlightEntity(requestDto.getFlightId());
//
//        BookingValidationContext context = new BookingValidationContext(flight, requestDto.getTickets());
//        bookingValidators.forEach(validator -> validator.validate(context));
//
//        Map<String, FlightFare> fareMap = flightFareService.getFlightFareMap(requestDto.getFlightId());
//
//        List<PassengerCreateRequestDto> passengersDto = requestDto.getTickets().stream()
//                .map(ticket -> ticket.getPassengers())
//                .collect(Collectors.toList());
//        Map<String, Passenger> savedPassengers = passengerService.saveNewPassenger(passengersDto);
//
//        TicketBuildResult ticketBuildResult =  ticketBuilder.build(requestDto.getTickets(),flight,fareMap,savedPassengers);
//
//        flightService.reserveSeat(requestDto.getFlightId(), ticketBuildResult.getAllTickets().size());
//
//        Booking bookingDB = saveBooking(flight, user);
//
//        return buildBookingResponse(bookingDB, flight.getId());
//    }

    private Booking saveBooking(Flight flight,
                                User user,
                                BigDecimal totalPrice,
                                BookingCreateRequestDto requestDto) {

        Booking booking = new Booking();
        booking.setCurrency("USD");
        booking.setTotalPrice(totalPrice);
        booking.setStatus(BookingStatus.PENDING);
        booking.setFlight(flight);
        booking.setUser(user);
        booking.setSeatCount(requestDto.getPassengerSeat().getSeatCount());
        booking.setTravelClass(requestDto.getPassengerSeat().getTravelClass());

        return bookingRepository.save(booking);
    }

    private BookingResponseDto buildBookingResponse(
            Booking bookingDB,
            Long flightId) {

        List<TicketResponseDto> ticketsCreateResponseDto = new ArrayList<>();

//        for (Ticket ticket:ticketsDB) {
//            TicketResponseDto ticketDto = ticketMapper.toDto(ticket);
//            ticketDto.setFlightId(flightId);
//            ticketsCreateResponseDto.add(ticketDto);
//        }

        // create booking dto
        BookingResponseDto bookingCreateResponseDto = new BookingResponseDto();
        bookingCreateResponseDto.setId(bookingDB.getId());
        bookingCreateResponseDto.setCreatedAt(bookingDB.getCreatedAt());
        bookingCreateResponseDto.setTotalPrice(bookingDB.getTotalPrice());
        bookingCreateResponseDto.setCurrency(bookingDB.getCurrency());
        bookingCreateResponseDto.setStatus(bookingDB.getStatus().toString());
        bookingCreateResponseDto.setFlightId(flightId);
        //bookingCreateResponseDto.setTickets(ticketsCreateResponseDto);
        bookingCreateResponseDto.setSeatCount(bookingDB.getSeatCount());
        bookingCreateResponseDto.setTravelClass(bookingDB.getTravelClass());

        return bookingCreateResponseDto;
    }
}
