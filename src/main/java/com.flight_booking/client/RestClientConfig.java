package com.flight_booking.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${app.client.aviationstack.url}")
    private String urlAviationStack;

    @Bean
    public RestClient aviationStackClient() {
        return RestClient.builder()
                .baseUrl(urlAviationStack)
                .build();
    }
}
