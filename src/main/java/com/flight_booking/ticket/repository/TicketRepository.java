package com.flight_booking.ticket.repository;

import com.flight_booking.flight.entity.Flight;
import com.flight_booking.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByFlight(Flight flight);
}
