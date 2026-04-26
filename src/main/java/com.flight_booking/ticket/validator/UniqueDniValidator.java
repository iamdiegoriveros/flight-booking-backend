package com.flight_booking.ticket.validator;

import com.flight_booking.exceptions.BadRequestException;
import com.flight_booking.passenger.dto.PassengerCreateRequestDto;
import com.flight_booking.ticket.dto.TicketCreateRequestDto;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UniqueDniValidator implements TicketValidator{

    public void validate(TicketValidationContext context) {

        List<PassengerCreateRequestDto> passengers = context.getPassengers();

        Set<String> dnis = new HashSet<>();

        for (PassengerCreateRequestDto passenger: passengers) {
            if (!dnis.add(passenger.getDni())) {
                throw new BadRequestException("Duplicated DNI detected");
            }
        }
    }


}

