package com.flight_booking.airline.service;

import com.flight_booking.airline.dto.AirlineCreateResquestDto;
import com.flight_booking.airline.dto.AirlineResponseDto;
import com.flight_booking.airline.entity.Airline;
import com.flight_booking.airline.repository.AirlineRepository;
import com.flight_booking.exceptions.ResourceNotFoundException;
import com.flight_booking.mapper.AirlineMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AirlineServiceTest {

    @Mock
    private AirlineRepository airlineRepository;

    @Mock
    private AirlineMapper airlineMapper;

    @InjectMocks
    private AirlineServiceImpl airlineService;

    @Test
    void shouldCreateAirline() {

        AirlineCreateResquestDto airlineRequestDto = new AirlineCreateResquestDto();
        airlineRequestDto.setName("Aerolineas Argentinas");
        airlineRequestDto.setCode("aa123");

        Airline airline = new Airline();
        airline.setName("Aerolineas Argentinas");
        airline.setCode("aa123");

        Airline savedAirline = new Airline();
        savedAirline.setName("Aerolineas Argentinas");
        savedAirline.setCode("aa123");
        savedAirline.setId(1L);

        AirlineResponseDto airlineResponseDto = new AirlineResponseDto();
        airlineResponseDto.setName("Aerolineas Argentinas");
        airlineResponseDto.setCode("aa123");
        airlineResponseDto.setId(1L);

        when(airlineMapper.toEntity(airlineRequestDto))
                .thenReturn(airline);

        when(airlineMapper.toDto(any(Airline.class)))
                .thenReturn(airlineResponseDto);

        when(airlineRepository.save(any(Airline.class)))
                .thenReturn(savedAirline);

        AirlineResponseDto result = airlineService.create(airlineRequestDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());

    }

    @Test
    void shouldGetAllAirline() {

        List<Airline> airlines = new ArrayList<>();

        Airline airline1 = new Airline(1L, "Aerolinea 1", "aa111");
        Airline airline2 = new Airline(2L, "Aerolinea 2", "aa222");


        airlines.add(airline1);
        airlines.add(airline2);

        List<AirlineResponseDto> airlineResponseDtos = new ArrayList<>();

        AirlineResponseDto airlineResponseDto1 = new AirlineResponseDto(1L, "Aerolinea 1", "aa111");
        AirlineResponseDto airlineResponseDto2 = new AirlineResponseDto(2L, "Aerolinea 2", "aa222");


        airlineResponseDtos.add(airlineResponseDto1);
        airlineResponseDtos.add(airlineResponseDto2);

        when(airlineRepository.findAll())
                .thenReturn(airlines);

        when(airlineMapper.toDto(airline1))
                .thenReturn(airlineResponseDto1);

        when(airlineMapper.toDto(airline2))
                .thenReturn(airlineResponseDto2);

        List<AirlineResponseDto> result = airlineService.getAllAirline();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());

        verify(airlineRepository).findAll();
        verify(airlineMapper).toDto(airline1);
        verify(airlineMapper).toDto(airline2);

    }

    @Test
    void shouldReturnEmptyListWhenNoAirline() {

        List<Airline> airlines = new ArrayList<>();

        when(airlineRepository.findAll())
                .thenReturn(Collections.emptyList());

        List<AirlineResponseDto> result= airlineService.getAllAirline();

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());


        verify(airlineRepository).findAll();

        // 🔥 opcional (muy pro)
        verify(airlineMapper, never()).toDto(any());

    }

    @Test
    void shouldDeleteAirlineById() {

        Long id = 1L;

        airlineRepository.deleteById(id);

        verify(airlineRepository).deleteById(id);

    }

    @Test
    void shouldThrowExceptionWhenAirlineNotFound() {

        Long id = 1L;

        doThrow(new EmptyResultDataAccessException(1))
                .when(airlineRepository).deleteById(id);

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> airlineService.deleteAirlineById(id));

        verify(airlineRepository).deleteById(id);
    }
}
