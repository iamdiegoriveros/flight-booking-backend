package com.flight_booking.ticket.validator;

import com.flight_booking.booking.entity.Booking;
import com.flight_booking.booking.entity.BookingStatus;
import com.flight_booking.exceptions.BadRequestException;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;


@Component
public class BookingStatusPendingValidator implements TicketValidator{
    @Override
    public void validate(TicketValidationContext context) {

        Booking booking = context.getBooking();

        if (!BookingStatus.PENDING.equals(booking.getStatus())) {
            throw new BadRequestException("Booking with id %s is not PENDING".formatted(booking.getId()));
        }
    }
}
