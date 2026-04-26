package com.flight_booking.booking.service;

import com.flight_booking.booking.dto.BookingCreateRequestDto;
import com.flight_booking.booking.dto.PassengerSeatDto;
import com.flight_booking.flight.entity.Flight;
import com.flight_booking.flight.repository.FlightRepository;
import com.flight_booking.passenger.dto.PassengerCreateRequestDto;
import com.flight_booking.security.CustomUserDetailsServiceImpl;
import com.flight_booking.ticket.dto.TicketCreateRequestDto;
import com.flight_booking.user.entity.User;
import com.flight_booking.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @Test
    void shouldHandleConcurrentBookings() throws InterruptedException {


        User savedUser = userRepository.findByUsername("diego123").get();

        Flight flight = flightRepository.findById(158L).get();

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(savedUser.getUsername());

        Authentication authentication = UsernamePasswordAuthenticationToken
                .authenticated(userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        //Authentication auth = mockAuthentication(authentication);

        int threads = 10;

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(threads);

        AtomicInteger success = new AtomicInteger();
        AtomicInteger errors = new AtomicInteger();

        BookingCreateRequestDto request = buildRequest(flight.getId());

        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                try {
                    startLatch.await(); // 🔥 todos arrancan juntos

                    long start = System.currentTimeMillis();

                    bookingService.create(request, authentication);

                    long end = System.currentTimeMillis();

                    System.out.println("Latency update: " + (end - start) + " ms");

                    success.incrementAndGet();

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    errors.incrementAndGet();
                } finally {
                    endLatch.countDown();
                }
            });
        }

        // WHEN
        startLatch.countDown(); // 💥 dispara todos juntos
        endLatch.await();

        // THEN
        Flight updated = flightRepository.findById(flight.getId()).get();

        System.out.println("Success: " + success.get());
        System.out.println("Errors: " + errors.get());
        System.out.println("Seats left: " + updated.getAvailableSeats());

        // 🔥 VALIDACIONES CLAVE
        assertEquals(0, updated.getAvailableSeats());

        // nunca debería haber más reservas que asientos
        assertTrue(success.get() <= 150);
    }

    public BookingCreateRequestDto buildRequest(Long flightId) {

        PassengerSeatDto passengerSeatDto = new PassengerSeatDto();

        passengerSeatDto.setSeatCount(1);
        passengerSeatDto.setTravelClass("ECONOMY");

        BookingCreateRequestDto requestDto = new BookingCreateRequestDto();

        requestDto.setFlightId(flightId);
        requestDto.setPassengerSeat(passengerSeatDto);

        return requestDto;
    }
}
