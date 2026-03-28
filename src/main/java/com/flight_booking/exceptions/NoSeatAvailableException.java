package com.flight_booking.exceptions;

public class NoSeatAvailableException extends RuntimeException {
    public NoSeatAvailableException(String message) {
        super(message);
    }
}
