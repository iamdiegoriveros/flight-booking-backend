package com.flight_booking.booking.validators;

import org.apache.coyote.BadRequestException;

public interface BookingValidator {

    void validate(BookingValidationContext context);
}
