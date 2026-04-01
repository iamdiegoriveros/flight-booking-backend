package com.flight_booking.booking.validators;

import com.flight_booking.booking.exceptions.NoUniqueDniException;
import com.flight_booking.ticket.dto.TicketCreateRequestDto;
import com.flight_booking.ticket.entity.Ticket;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UniqueDniValidator implements BookingValidator{
    @Override
    public void validate(BookingValidationContext context) {

        List<TicketCreateRequestDto> tickets = context.getTickets();

        Set<String> dnis = new HashSet<>();

        for (TicketCreateRequestDto ticket: tickets) {
            if (!dnis.add(ticket.getPassengers().getDni())) {
                throw new NoUniqueDniException("Duplicated DNI detected");
            }
        }
    }


}
