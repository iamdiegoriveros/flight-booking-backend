package com.flight_booking.flight.specification;

import com.flight_booking.flight.dto.FlightFilterDto;
import com.flight_booking.flight.entity.Flight;
import org.springframework.data.jpa.domain.Specification;

public class FlightSpecificationBuilder {

    public static Specification<Flight> build(FlightFilterDto flightFilterDto) {

        Specification<Flight> specification = Specification.allOf();

        if (flightFilterDto.getOrigin() != null) {
            specification = specification.and(
                    ((root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("origin"), flightFilterDto.getOrigin()))
            );
        }

        if (flightFilterDto.getDestination() != null) {
            specification = specification.and(
                    ((root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("destination"), flightFilterDto.getDestination()))
            );
        }

        return specification;
    }
}
