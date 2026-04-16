package com.flight_booking.flight.exception;

public class NoAvailableSeatException extends RuntimeException{

    public NoAvailableSeatException(String message) {
        super(message);
    }
}
