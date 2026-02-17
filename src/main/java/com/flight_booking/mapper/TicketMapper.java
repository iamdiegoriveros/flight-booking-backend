package com.flight_booking.mapper;

import com.flight_booking.booking.dto.TicketCreateRequestDto;
import com.flight_booking.booking.dto.TicketCreateResponseDto;
import com.flight_booking.booking.entity.Ticket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    Ticket toEntity(TicketCreateRequestDto dto);
    TicketCreateResponseDto toDto(Ticket entity);
}
