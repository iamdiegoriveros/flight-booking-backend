package com.flight_booking.flight.entity;

import com.flight_booking.aircraft.entity.Aircraft;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origin;
    private String destination;
    private Date date;
    private LocalDateTime dateTimeDeparture;
    private LocalDateTime dateTimeArrival;
    private String status;

    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

    public Flight() {
    }

    public Flight(String origin, String destination, Date date, LocalDateTime dateTimeDeparture, LocalDateTime dateTimeArrival, String status) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.dateTimeDeparture = dateTimeDeparture;
        this.dateTimeArrival = dateTimeArrival;
        this.status = status;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}

