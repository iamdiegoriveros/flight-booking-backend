package com.flight_booking.sync;

import com.flight_booking.aircraft.dto.AircraftClientDto;
import com.flight_booking.aircraft.dto.AircraftDataClient;
import com.flight_booking.aircraft.entity.Aircraft;
import com.flight_booking.aircraft.repository.AircraftRepository;
import com.flight_booking.airline.entity.Airline;
import com.flight_booking.airline.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AircraftSyncService {

    @Qualifier("aviationStackClient")
    private final RestClient client;
    private final AircraftRepository aircraftRepository;
    private final AirlineRepository airlineRepository;

    @Value("${app.client.aviationstack.api-key}")
    private String apiKey;

    public AircraftSyncService(RestClient client, AircraftRepository aircraftRepository, AirlineRepository airlineRepository) {
        this.client = client;
        this.aircraftRepository = aircraftRepository;
        this.airlineRepository = airlineRepository;
    }

    public void initialLoad() {
        AircraftClientDto aircraft = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/airplanes")
                        .queryParam("access_key", apiKey)
                        .queryParam("limit", "100")
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if (aircraft != null) {
            saveOrUpdate(aircraft);
        }
    }

    private void saveOrUpdate(AircraftClientDto aircraftClientDto) {

        List<Aircraft> aircrafts = aircraftClientDto.getData().stream()
                .map(aircraftApi -> toEntity(aircraftApi))
                .collect(Collectors.toList());

        List<Airline> airlines = airlineRepository.findAll();
        Collections.shuffle(airlines);


        aircrafts.forEach(aircraft -> {
            Airline airline = airlines.get(ThreadLocalRandom.current().nextInt(1, airlines.size()));
            int capacity = ThreadLocalRandom.current().nextInt(12, 16)*10;

            aircraft.setAirline(airline);
            aircraft.setCapacity(capacity);
            aircraft.setStatus("ACTIVE");
        });

        aircraftRepository.saveAll(aircrafts);

    }

    private Aircraft toEntity(AircraftDataClient aircraftDataClient) {

        Aircraft aircraft = new Aircraft();
        aircraft.setModel(aircraftDataClient.getModelCode());
        return aircraft;
    }
}

