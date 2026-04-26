package com.flight_booking.ticket.builder;

import com.flight_booking.ticket.entity.Ticket;

import java.math.BigDecimal;
import java.util.List;

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
