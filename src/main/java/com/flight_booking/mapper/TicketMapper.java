package com.flight_booking.mapper;

import com.flight_booking.ticket.dto.TicketCreateRequestDto;
import com.flight_booking.ticket.dto.TicketCreateResponseDto;
import com.flight_booking.ticket.entity.Ticket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    Ticket toEntity(TicketCreateRequestDto dto);
    TicketCreateResponseDto toDto(Ticket entity);
}
