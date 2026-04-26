package com.flight_booking.ticket.builder;

import com.flight_booking.booking.entity.Booking;
import com.flight_booking.exceptions.ResourceNotFoundException;
import com.flight_booking.flight.entity.Flight;
import com.flight_booking.flight.entity.FlightFare;
import com.flight_booking.passenger.dto.PassengerCreateRequestDto;
import com.flight_booking.passenger.entity.Passenger;
import com.flight_booking.ticket.dto.TicketCreateRequestDto;
import com.flight_booking.ticket.entity.Ticket;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class TicketBuilder {

    public List<Ticket> build(
            Booking booking,
            Map<String, Passenger> passengersInDbMap)
    {
        List<Ticket> allTickets = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        // construir tickets y passenger

        passengersInDbMap.forEach((key, passenger) -> {
            Ticket ticket = buildTicketEntity(booking);
            ticket.setPassenger(passenger);
            allTickets.add(ticket);

        });

//        for (PassengerCreateRequestDto passenger: passengersInDbMap) {
//
//            Ticket ticket = buildTicketEntity(booking);
//
//            ticket.setPassenger(passenger);
//
//            allTickets.add(ticket);
//        }

        return allTickets;
    }

    private Ticket buildTicketEntity(Booking booking) {
        Ticket ticket = new Ticket();
        ticket.setTravelClass(booking.getTravelClass());
        ticket.setFlight(booking.getFlight());
        ticket.setStatus("RESERVED");
        ticket.setSeatCode(null);
        ticket.setBooking(booking);

        return ticket;
    }
}
