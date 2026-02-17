package com.flight_booking.booking.dto;

import com.flight_booking.flight.dto.FlightCreateResponseDto;
import com.flight_booking.flight.dto.FlightSummaryBookingDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BookingCreateResponseDto {

    private Long id;
    private String status;
    private LocalDateTime createdAt;
    private BigDecimal totalPrice;
    private String currency;

    private List<TicketCreateResponseDto> tickets;
    private FlightSummaryBookingDto flight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<TicketCreateResponseDto> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketCreateResponseDto> tickets) {
        this.tickets = tickets;
    }

    public FlightSummaryBookingDto getFlight() {
        return flight;
    }

    public void setFlight(FlightSummaryBookingDto flight) {
        this.flight = flight;
    }
}
