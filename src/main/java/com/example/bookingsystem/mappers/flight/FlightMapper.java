package com.example.bookingsystem.mappers.flight;

import com.example.bookingsystem.dto.flight.FlightDtoIn;
import com.example.bookingsystem.dto.flight.FlightDtoOut;
import com.example.bookingsystem.entities.flight.FlightEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FlightMapper {
    FlightDtoOut toDto(FlightEntity flightEntity);
    FlightEntity toEntity(FlightDtoIn flightDtoIn);
    List<FlightDtoOut> toDtoList(List<FlightEntity> flightEntities);
}
