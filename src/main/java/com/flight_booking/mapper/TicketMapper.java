package com.flight_booking.mapper;

import com.flight_booking.ticket.dto.TicketCreateRequestDto;
import com.flight_booking.ticket.dto.TicketResponseDto;
import com.flight_booking.ticket.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    Ticket toEntity(TicketCreateRequestDto dto);

    @Mapping(target = "flightId", source = "flight.id")
    TicketResponseDto toDto(Ticket entity);
}
