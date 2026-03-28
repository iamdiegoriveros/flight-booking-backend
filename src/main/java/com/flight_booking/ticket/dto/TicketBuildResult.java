package com.flight_booking.ticket.dto;

import com.flight_booking.passenger.entity.Passenger;
import com.flight_booking.ticket.entity.Ticket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketBuildResult {

    List<Ticket> allTickets;
    BigDecimal totalPrice;

    public TicketBuildResult() {
    }

    public TicketBuildResult(List<Ticket> allTickets, BigDecimal totalPrice) {
        this.allTickets = allTickets;
        this.totalPrice = totalPrice;
    }

    public List<Ticket> getAllTickets() {
        return allTickets;
    }

    public void setAllTickets(List<Ticket> allTickets) {
        this.allTickets = allTickets;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
