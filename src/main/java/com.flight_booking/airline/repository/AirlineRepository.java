package com.flight_booking.airline.repository;

import com.flight_booking.airline.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Queue;


public interface AirlineRepository extends JpaRepository<Airline, Long> {

    Queue<Airline> findByIdIn(Queue<Integer> flightIdList);
}
