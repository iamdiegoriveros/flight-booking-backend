package com.flight_booking.ticket.validator;

import com.flight_booking.ticket.dto.TicketCreateRequestDto;

public interface TicketValidator {

    void validate(TicketValidationContext context);
}
