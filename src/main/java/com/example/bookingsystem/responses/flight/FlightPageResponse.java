package com.example.bookingsystem.responses.flight;

import com.example.bookingsystem.dto.flight.FlightDtoOut;
import lombok.Getter;

import java.util.List;

@Getter
public class FlightPageResponse {
    private List<FlightDtoOut> flights;
    private boolean hasNextPage;

    public FlightPageResponse(List<FlightDtoOut> flights, boolean hasNextPage) {
        this.flights = flights;
        this.hasNextPage = hasNextPage;
    }
}
