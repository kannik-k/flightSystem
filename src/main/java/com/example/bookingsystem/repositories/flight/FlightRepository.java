package com.example.bookingsystem.repositories.flight;

import com.example.bookingsystem.entities.flight.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<FlightEntity, Long> {
}
