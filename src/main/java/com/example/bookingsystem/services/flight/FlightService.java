package com.example.bookingsystem.services.flight;

import com.example.bookingsystem.dto.flight.FlightDtoIn;
import com.example.bookingsystem.dto.flight.FlightDtoOut;
import com.example.bookingsystem.entities.flight.FlightEntity;
import com.example.bookingsystem.mappers.flight.FlightMapper;
import com.example.bookingsystem.repositories.flight.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    public FlightDtoOut addFlight(FlightDtoIn flightDtoIn) {
        FlightEntity flightEntity = flightMapper.toEntity(flightDtoIn);
        flightRepository.save(flightEntity);
        FlightDtoOut flightDtoOut = flightMapper.toDto(flightEntity);
        System.out.printf("Flight added: %s\n", flightDtoOut);
        return flightDtoOut;
    }
}
