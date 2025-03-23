package com.example.bookingsystem.specifications.flight;

import com.example.bookingsystem.entities.flight.FlightEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class FlightSpecification {
    private FlightSpecification() {}

    public static Specification<FlightEntity> getByFlightNumber(String flightNumber) {
        return ((root, query, criteriaBuilder) ->
                flightNumber == null ? null : criteriaBuilder.equal(criteriaBuilder.lower(root.get("flightNumber")), flightNumber.toLowerCase()));
    }

    public static Specification<FlightEntity> getByDepartureAirport(String departureAirport) {
        return ((root, query, criteriaBuilder) ->
                departureAirport == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("departureAirport")), "%" + departureAirport.toLowerCase() + "%"));
    }

    public static Specification<FlightEntity> getByArrivalAirport(String arrivalAirport) {
        return ((root, query, criteriaBuilder) ->
                arrivalAirport == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("arrivalAirport")), "%" + arrivalAirport.toLowerCase() + "%"));
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
