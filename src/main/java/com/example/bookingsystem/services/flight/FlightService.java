package com.example.bookingsystem.services.flight;

import com.example.bookingsystem.dto.flight.FlightDtoIn;
import com.example.bookingsystem.dto.flight.FlightDtoOut;
import com.example.bookingsystem.entities.flight.FlightEntity;
import com.example.bookingsystem.mappers.flight.FlightMapper;
import com.example.bookingsystem.repositories.flight.FlightRepository;
import com.example.bookingsystem.responses.flight.FlightPageResponse;
import com.example.bookingsystem.services.seat.SeatService;
import com.example.bookingsystem.specifications.flight.FlightSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;
    private final SeatService seatService;

    /**
     * Add plane to database.
     *
     * Creates plane object, adds it to the database. It also generates seats  for this plane and assigns random seats
     * as reserved.
     */
    public FlightDtoOut addFlight(FlightDtoIn flightDtoIn) {
        FlightEntity flightEntity = flightMapper.toEntity(flightDtoIn);
        if (flightEntity.getArrivalTime().isBefore(flightEntity.getDepartureTime()) || flightEntity.getArrivalTime().isEqual(flightEntity.getDepartureTime())) {
            flightEntity.setArrivalTime(flightEntity.getArrivalTime().plusDays(1));
        }
        flightRepository.save(flightEntity);
        seatService.generateSeats(flightEntity.getFlightId());
        seatService.randomSeatsBooked(flightEntity.getFlightId());
        return flightMapper.toDto(flightEntity);
    }

    /**
     * Get flight.
     *
     * Returns flight from database based on it's id. Used mainly for testing purposes
     */
    public FlightDtoOut getFlightById(Long flightId) {
        FlightEntity flightEntity = flightRepository.findById(flightId).orElse(null);
        return flightMapper.toDto(flightEntity);
    }

    /**
     * Get flights.
     *
     * This method is used to search for flights. Flights can be filtered by departure airport, arrival airport, and
     * departure date. The departure time is set to 00:00:00 so that all flights departing on that day are included.
     * Flights with departure dates after the given date are also considered.
     *
     * Specifications and pagination are used to prevent the website from crashing when handling large amounts of data.
     * A Slice is used because it contains an isLast value, which is useful to determine whether
     * there is more data available for the next page and is used in the frontend.
     */
    public FlightPageResponse getFlights(String departureAirport, String arrivalAirport,
                                         LocalDateTime departureTime, int page, int size) {
        if (departureTime != null) {
            departureTime = departureTime.toLocalDate().atStartOfDay();
        }

        Specification<FlightEntity> specification = Specification
                .where((FlightSpecification.getByDepartureAirport(departureAirport))
                .and(FlightSpecification.getByArrivalAirport(arrivalAirport))
                .and(FlightSpecification.getByDepartureTime(departureTime)));

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Order.desc("departureTime").ignoreCase(), Sort.Order.asc("departureTime").ignoreCase()));
        Slice<FlightEntity> slice = flightRepository.findAll(specification, pageable);
        boolean isLastPage = slice.isLast();
        List<FlightDtoOut> list = flightMapper.toDtoList(slice.getContent());
        return new FlightPageResponse(list, !isLastPage);
    }
}
