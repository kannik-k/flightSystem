package com.example.bookingsystem.repositories.seat;

import com.example.bookingsystem.entities.seat.SeatEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
    List<SeatEntity> findAllByFlightId(Long flightId);

    List<SeatEntity> findAll(Specification<SeatEntity> spec);
}
