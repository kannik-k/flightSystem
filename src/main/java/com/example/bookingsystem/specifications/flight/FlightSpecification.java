package com.example.bookingsystem.specifications.flight;

import com.example.bookingsystem.entities.flight.FlightEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class FlightSpecification {
    public FlightSpecification() {}

    public static Specification<FlightEntity> getByFlightNumber(String flightNumber) {
        return ((root, query, criteriaBuilder) ->
                flightNumber == null ? null : criteriaBuilder.equal(criteriaBuilder.lower(root.get("flightNumber")), flightNumber.toLowerCase()));
    }

    public static Specification<FlightEntity> getByDepartureAirport(String airport) {
        return ((root, query, criteriaBuilder) ->
                airport == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("airport")), "%" + airport.toLowerCase() + "%"));
    }

    public static Specification<FlightEntity> getByArrivalAirport(String airport) {
        return ((root, query, criteriaBuilder) ->
                airport == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("arrivalAirport")), "%" + airport.toLowerCase() + "%"));
    }

    public static Specification<FlightEntity> getByDepartureTime(LocalDateTime departureTime) {
        return ((root, query, criteriaBuilder) ->
                departureTime == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("departureTime"), departureTime));
    }

    public static Specification<FlightEntity> getByPrice(Double price) {
        return ((root, query, criteriaBuilder) ->
                price == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price));
    }
}
