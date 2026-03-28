package com.flight_booking.flight.entity;

import com.flight_booking.aircraft.entity.Aircraft;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origin;
    private String destination;
    private LocalDateTime dateTimeDeparture;
    private LocalDateTime dateTimeArrival;
    private String status;

    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

    public Flight() {
    }

    public Flight(String origin, String destination, LocalDateTime dateTimeDeparture, LocalDateTime dateTimeArrival) {
        this.origin = origin;
        this.destination = destination;
        this.dateTimeDeparture = dateTimeDeparture;
        this.dateTimeArrival = dateTimeArrival;
    }

    @PrePersist
    public void prePersist() {
        this.status = "PROGRAMMED";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDateTimeDeparture() {
        return dateTimeDeparture;
    }

    public void setDateTimeDeparture(LocalDateTime dateTimeDeparture) {
        this.dateTimeDeparture = dateTimeDeparture;
    }

    public LocalDateTime getDateTimeArrival() {
        return dateTimeArrival;
    }

    public void setDateTimeArrival(LocalDateTime dateTimeArrival) {
        this.dateTimeArrival = dateTimeArrival;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public String formatterDateTimeDeparture() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return this.dateTimeDeparture.format(format);
    }

    public String formatterDateTimeArrival() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return this.dateTimeArrival.format(format);
    }

    public boolean seatAvailable() {
        return this.aircraft.getCapacity() > 0;
    }
}

