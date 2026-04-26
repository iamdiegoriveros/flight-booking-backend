package com.flight_booking.config;

import com.flight_booking.sync.AircraftSyncService;
import com.flight_booking.sync.AirlineSyncService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoaderCLR implements CommandLineRunner {

    @Value("${app.init-data:false}")
    private boolean initData;

    private final AirlineSyncService airlineSyncService;
    private final AircraftSyncService aircraftSyncService;


    public DataLoaderCLR(AirlineSyncService airlineSyncService, AircraftSyncService aircraftSyncService) {
        this.airlineSyncService = airlineSyncService;
        this.aircraftSyncService = aircraftSyncService;
    }

    @Override
    public void run(String... args) {
        if (initData) {
            airlineSyncService.initialLoad();
            aircraftSyncService.initialLoad();
        }
    }
}
