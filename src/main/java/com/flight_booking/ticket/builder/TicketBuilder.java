package com.flight_booking.ticket.builder;

import com.flight_booking.exceptions.ResourceNotFoundException;
import com.flight_booking.flight.entity.Flight;
import com.flight_booking.flight.entity.FlightFare;
import com.flight_booking.passenger.entity.Passenger;
import com.flight_booking.ticket.dto.TicketCreateRequestDto;
import com.flight_booking.ticket.entity.Ticket;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class TicketBuilder {

    public TicketBuildResult build(
            List<TicketCreateRequestDto> ticketDtos,
            Flight flight,
            Map<String, FlightFare> flightFareMap,
            Map<String, Passenger> passengersInDbMap)
    {
        List<Ticket> allTickets = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        // construir tickets y passenger
        for (TicketCreateRequestDto ticketDto: ticketDtos) {

            FlightFare flightFare = Optional.ofNullable(flightFareMap.get(ticketDto.getTravelClass()))
                    .orElseThrow(() -> new ResourceNotFoundException("Fare not found with travel class " + ticketDto.getTravelClass()));

            totalPrice = totalPrice.add(flightFare.getBasePrice());

            Ticket ticket = buildTicketEntity(flightFare, flight);

            Passenger passenger = Optional.ofNullable(passengersInDbMap.get(ticketDto.getPassengers().getDni())
            ).orElseThrow(() -> new ResourceNotFoundException(
                    "Passenger not found for DNI: " + ticketDto.getPassengers().getDni()
            ));

            ticket.setPassenger(passenger);

            allTickets.add(ticket);
        }

        return new TicketBuildResult(allTickets, totalPrice);
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
}
