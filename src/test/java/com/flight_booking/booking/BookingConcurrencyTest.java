package com.flight_booking.booking;

import com.flight_booking.booking.dto.BookingCreateRequestDto;
import com.flight_booking.booking.service.BookingService;
import com.flight_booking.flight.entity.Flight;
import com.flight_booking.flight.repository.FlightRepository;
import com.flight_booking.passenger.dto.PassengerCreateRequestDto;
import com.flight_booking.ticket.dto.TicketCreateRequestDto;
import com.flight_booking.user.entity.User;
import com.flight_booking.user.repository.UserRepository;
import org.junit.jupiter.api.RepeatedTest;
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
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BookingConcurrencyTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserRepository userRepository;

    private Authentication buildAuth(String username) {
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password("password")
                .roles("USER")
                .build();

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @RepeatedTest(30) // 🔥 ejecuta varias veces (bugs de concurrencia son aleatorios)
    void shouldNotOverbookWhenConcurrentRequests() throws Exception {

        // 🔹 Setup inicial
        Flight flight = new Flight();
        flight.setAvailableSeats(5);
        Flight savedFlight = flightRepository.saveAndFlush(flight); // 🔥 importante

        User user = new User();
        user.setUsername("testUser");
        userRepository.saveAndFlush(user);

        int threadCount = 500; // 🔥 más carga
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        CountDownLatch ready = new CountDownLatch(threadCount);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(threadCount);

        List<Future<Boolean>> results = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {

            results.add(executor.submit(() -> {
                try {
                    ready.countDown();     // listo
                    start.await();        // 🔥 todos esperan acá

                    BookingCreateRequestDto request = buildRequest(savedFlight.getId());

                    bookingService.create(request, buildAuth("testUser"));

                    return true;
                } catch (Exception e) {
                    return false;
                } finally {
                    done.countDown();
                }
            }));
        }

        // 🔥 esperar a que todos estén listos
        ready.await();

        // 🔥 disparar todos al mismo tiempo
        start.countDown();

        // 🔥 esperar a que todos terminen
        done.await();

        executor.shutdown();

        // 🔹 Verificación
        Flight updatedFlight = flightRepository.findById(savedFlight.getId()).orElseThrow();

        System.out.println("Seats left: " + updatedFlight.getAvailableSeats());

        long successCount = results.stream()
                .map(f -> {
                    try { return f.get(); } catch (Exception e) { return false; }
                })
                .filter(r -> r)
                .count();

        System.out.println("Success bookings: " + successCount);

        // 🔥 ASSERTS IMPORTANTES
        assertTrue(updatedFlight.getAvailableSeats() >= 0);
        assertTrue(successCount <= 5);
    }

    private BookingCreateRequestDto buildRequest(Long flightId) {
        BookingCreateRequestDto dto = new BookingCreateRequestDto();
        dto.setFlightId(flightId);

        List<TicketCreateRequestDto> tickets = new ArrayList<>();
        TicketCreateRequestDto ticket = new TicketCreateRequestDto();

        PassengerCreateRequestDto passenger = new PassengerCreateRequestDto();
        passenger.setFirstName("Test");
        passenger.setLastName("User");

        ticket.setPassengers(passenger);
        tickets.add(ticket);

        dto.setTickets(tickets);

        return dto;
    }
}