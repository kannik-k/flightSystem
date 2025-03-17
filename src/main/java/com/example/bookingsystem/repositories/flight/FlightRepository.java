package com.example.bookingsystem.repositories.flight;

import com.example.bookingsystem.entities.flight.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FlightRepository extends JpaRepository<FlightEntity, Long>, JpaSpecificationExecutor<FlightEntity>, PagingAndSortingRepository<FlightEntity, Long> {

}
