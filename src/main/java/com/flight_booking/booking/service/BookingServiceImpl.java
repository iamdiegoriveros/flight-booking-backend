package com.flight_booking.booking.service;

import com.flight_booking.booking.dto.*;
import com.flight_booking.booking.entity.Booking;
import com.flight_booking.booking.entity.Ticket;
import com.flight_booking.booking.repository.BookingRepository;
import com.flight_booking.booking.repository.TicketRepository;
import com.flight_booking.exceptions.BadRequestException;
import com.flight_booking.exceptions.ResourceNotFoundException;
import com.flight_booking.flight.dto.FlightSummaryBookingDto;
import com.flight_booking.flight.entity.Flight;
import com.flight_booking.flight.entity.FlightFare;
import com.flight_booking.flight.repository.FlightFareRepository;
import com.flight_booking.flight.repository.FlightRepository;
import com.flight_booking.mapper.BookingMapper;
import com.flight_booking.mapper.FlightMapper;
import com.flight_booking.mapper.TicketMapper;
import com.flight_booking.passenger.dto.PassengerCreateRequestDto;
import com.flight_booking.passenger.entity.Passenger;
import com.flight_booking.passenger.repository.PassengerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;
    private final FlightFareRepository flightFareRepository;
    private final FlightRepository flightRepository;

    private final FlightMapper flightMapper;
    private final TicketMapper ticketMapper;
    private final BookingMapper bookingMapper;

    public BookingServiceImpl(BookingRepository bookingRepository, TicketRepository ticketRepository, PassengerRepository passengerRepository, FlightFareRepository flightFareRepository, FlightRepository flightRepository, FlightMapper flightMapper, TicketMapper ticketMapper, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.ticketRepository = ticketRepository;
        this.passengerRepository = passengerRepository;
        this.flightFareRepository = flightFareRepository;
        this.flightRepository = flightRepository;
        this.flightMapper = flightMapper;
        this.ticketMapper = ticketMapper;
        this.bookingMapper = bookingMapper;
    }

    @Override
    @Transactional
    public BookingCreateResponseDto create(BookingCreateRequestDto requestDto) {

        // get flight
        FlightSummaryBookingDto flightSummaryBookingDto = flightRepository.flightSummaryByIdBooking(requestDto.getFlightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id " + requestDto.getFlightId()));
        Flight flightRef = flightRepository.getReferenceById(requestDto.getFlightId());

        // load fares
        Map<String, FlightFare> fareMap = loadFlightFareMap(requestDto);

        // validate dnis
        Set<String> dnis = validateUniqueDni(requestDto);

        // get dnis existentes
        Map<String,Passenger> passengersInBD = passengerRepository.findByDniIn(new ArrayList<>(dnis)).stream()
                .collect(Collectors.toMap(Passenger::getDni, passenger -> passenger));

        // build new tickets and new passengers
        TicketBuildResult ticketBuildResult = buildTickets(requestDto, fareMap, passengersInBD, flightRef);

        // create booking in DataBase
        Booking bookingDB = createBooking(ticketBuildResult);


        return buildBookingResponse(bookingDB, bookingDB.getTickets(), flightSummaryBookingDto);

    }

    private TicketBuildResult buildTickets(
            BookingCreateRequestDto requestDto,
            Map<String, FlightFare> fareMap,
            Map<String, Passenger> passengersInDB,
            Flight flightRef) {

        Map<String,Passenger> newPassengers = new HashMap<>();
        Map<String,Ticket> ticketsNewPassenger = new HashMap<>();
        List<Ticket> ticketsExistsPassenger = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        // construir tickets y passenger
        for (TicketCreateRequestDto ticketDto: requestDto.getTickets()) {

            FlightFare flightFare = Optional.ofNullable(fareMap.get(ticketDto.getTravelClass()))
                    .orElseThrow(() -> new ResourceNotFoundException("Fare not found with travel class " + ticketDto.getTravelClass()));

            totalPrice = totalPrice.add(flightFare.getBasePrice());

            Ticket ticket = buildTicketEntity(flightFare, flightRef);

            // create passenger
            Passenger passenger = passengersInDB.get(ticketDto.getPassengerCreateRequestDto().getDni());
            if (passenger == null) {
                passenger = buildPassengerEntity(ticketDto);

                newPassengers.put(ticketDto.getPassengerCreateRequestDto().getDni(),passenger);
                ticketsNewPassenger.put(ticketDto.getPassengerCreateRequestDto().getDni(), ticket);
            } else {
                ticket.setPassenger(passengersInDB.get(ticketDto.getPassengerCreateRequestDto().getDni()));
                ticketsExistsPassenger.add(ticket);
            }
        }

        List<Passenger> passengersDB = passengerRepository.saveAll(newPassengers.values());

        // set passenger to ticket
        passengersDB.forEach(passenger -> {
            if (ticketsNewPassenger.containsKey(passenger.getDni())) {
                Ticket ticket = ticketsNewPassenger.get(passenger.getDni());
                ticket.setPassenger(passenger);
            }
        });

        List<Ticket> allTickets = Stream
                .concat(
                        ticketsExistsPassenger.stream(),
                        ticketsNewPassenger.values().stream())
                .collect(Collectors.toList());

        return new TicketBuildResult(allTickets, totalPrice);
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

    private Map<String, FlightFare> loadFlightFareMap(BookingCreateRequestDto requestDto) {

        List<FlightFare> flightFaresList = flightFareRepository.findByFlightId(requestDto.getFlightId());

        if (flightFaresList.isEmpty()) throw new ResourceNotFoundException("Fares not found for flight with id: " + requestDto.getFlightId());

        Map<String, FlightFare> fareMap = flightFaresList.stream()
                .collect(Collectors.toMap(FlightFare::getTravelClass, fare -> fare));

        return fareMap;
    }

    private Ticket buildTicketEntity(FlightFare flightFare, Flight flightRef) {
        Ticket ticket = new Ticket();
        ticket.setTravelClass(flightFare.getTravelClass());
        ticket.setPrice(flightFare.getBasePrice());
        ticket.setFlight(flightRef);
        ticket.setCurrency(flightFare.getCurrency());
        ticket.setStatus("RESERVED");
        ticket.setSeatCode(null);

        return ticket;
    }

    private Passenger buildPassengerEntity(TicketCreateRequestDto ticketDto) {
        Passenger passenger = new Passenger();
        passenger.setFirstName(ticketDto.getPassengerCreateRequestDto().getFirstName());
        passenger.setLastName(ticketDto.getPassengerCreateRequestDto().getLastName());
        passenger.setDni(ticketDto.getPassengerCreateRequestDto().getDni());

        return passenger;
    }

    private Booking createBooking(TicketBuildResult ticketBuildResult) {
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
            FlightSummaryBookingDto flightSummary) {

        List<TicketCreateResponseDto> ticketsCreateResponseDto = new ArrayList<>();

        for (Ticket ticket:ticketsDB) {
            TicketCreateResponseDto ticketDto = ticketMapper.toDto(ticket);
            ticketDto.setFlight(flightSummary);
            ticketsCreateResponseDto.add(ticketDto);
        }

        // create booking dto
        BookingCreateResponseDto bookingCreateResponseDto = new BookingCreateResponseDto();
        bookingCreateResponseDto.setId(bookingDB.getId());
        bookingCreateResponseDto.setCreatedAt(bookingDB.getCreatedAt());
        bookingCreateResponseDto.setTotalPrice(bookingDB.getTotalPrice());
        bookingCreateResponseDto.setCurrency(bookingDB.getCurrency());
        bookingCreateResponseDto.setStatus("RESERVED");
        bookingCreateResponseDto.setFlight(flightSummary);
        bookingCreateResponseDto.setTickets(ticketsCreateResponseDto);

        return bookingCreateResponseDto;
    }
}
