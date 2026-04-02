package com.flight_booking.booking.validators;

import com.flight_booking.booking.exceptions.NoSeatAvailableException;
import com.flight_booking.exceptions.BadRequestException;
import com.flight_booking.flight.entity.Flight;
import org.springframework.stereotype.Component;

@Component
public class SeatAvailableValidator implements BookingValidator{
    @Override
    public void validate(BookingValidationContext context) {

        Flight flight = context.getFlight();
        int ticketCount = context.getTickets().size();

        if (!flight.hasAvailableSeats(ticketCount)) {
            throw new BadRequestException("Not enough available seats. FlightId %d has %d seats remaining"
                    .formatted(flight.getId(), flight.getAvailableSeats()));
        }
    }
}
