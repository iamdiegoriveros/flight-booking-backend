package com.flight_booking.sync;

import com.flight_booking.airline.dto.AirlineClientDto;
import com.flight_booking.airline.dto.AirlineDataClient;
import com.flight_booking.airline.entity.Airline;
import com.flight_booking.airline.repository.AirlineRepository;
import com.flight_booking.client.RestClientConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirlineSyncService {

    @Qualifier("aviationStackClient")
    private final RestClient client;
    private final AirlineRepository repository;

    @Value("${app.client.aviationstack.api-key}")
    private String apiKey;


    public AirlineSyncService(RestClient client, AirlineRepository repository) {
        this.client = client;
        this.repository = repository;
    }

    public void initialLoad() {
        AirlineClientDto airlines = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/airlines")
                        .queryParam("access_key", apiKey)
                        .queryParam("limit", "7")
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
        if (airlines!= null) {
            saveOrUpdate(airlines);
        }
    }

    private void saveOrUpdate(AirlineClientDto dtos) {

        List<Airline> airlines = dtos.getData().stream()
                .map(airlineApi -> toEntity(airlineApi))
                .collect(Collectors.toList());

        repository.saveAll(airlines);
    }

    private Airline toEntity(AirlineDataClient airlineDataClient) {

        Airline airline = new Airline();
        airline.setName(airlineDataClient.getAirline_name());
        airline.setCode(airlineDataClient.getIcao_code());

        return airline;
    }
}
