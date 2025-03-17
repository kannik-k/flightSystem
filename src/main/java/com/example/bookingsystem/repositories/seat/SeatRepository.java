package com.example.bookingsystem.repositories.seat;

import com.example.bookingsystem.entities.seat.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
}
