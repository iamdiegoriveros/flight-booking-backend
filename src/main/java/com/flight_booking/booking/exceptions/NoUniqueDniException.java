package com.flight_booking.booking.exceptions;

public class NoUniqueDniException extends RuntimeException {
    public NoUniqueDniException(String message) {
        super(message);
    }
}
