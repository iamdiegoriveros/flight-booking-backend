package com.flight_booking.ticket.validator;

import com.flight_booking.booking.entity.Booking;
import com.flight_booking.exceptions.BadRequestException;
import com.flight_booking.passenger.dto.PassengerCreateRequestDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeatCountMatchesPassengersValidator implements TicketValidator{
    @Override
    public void validate(TicketValidationContext context) {

        Booking booking = context.getBooking();
        List<PassengerCreateRequestDto> passengers = context.getPassengers();

        if (booking.getSeatCount() != passengers.size()) {
            throw new BadRequestException(
                    "Seat count reserved (%d) does not match number of passengers (%d)"
                            .formatted(booking.getSeatCount(), passengers.size())
            );
        }
    }
}
