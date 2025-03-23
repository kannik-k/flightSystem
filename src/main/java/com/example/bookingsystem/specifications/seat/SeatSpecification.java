package com.example.bookingsystem.specifications.seat;

import com.example.bookingsystem.entities.seat.SeatEntity;
import org.springframework.data.jpa.domain.Specification;

public class SeatSpecification {

    public static Specification<SeatEntity> getByFlightId(Long flightId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("flightId"), flightId);
    }

    public static Specification<SeatEntity> getByClassType(String classType) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("classType"), classType);
    }

    public static Specification<SeatEntity> getByIsNearExit(Boolean isNearExit) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isNearExit"), isNearExit);
    }

    public static Specification<SeatEntity> getByHasExtraLegroom(Boolean hasExtraLegroom) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("hasExtraLegroom"), hasExtraLegroom);
    }

    public static Specification<SeatEntity> getByIsReserved(Boolean isReserved) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isReserved"), isReserved);
    }
}