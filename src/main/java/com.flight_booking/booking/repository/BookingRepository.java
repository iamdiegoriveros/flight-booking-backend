package com.flight_booking.booking.repository;

import com.flight_booking.booking.entity.Booking;
import com.flight_booking.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findBookingByUser(User user, Pageable pageable);
}
