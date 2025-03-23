package com.example.bookingsystem.config;

import com.example.bookingsystem.entities.flight.FlightEntity;
import com.example.bookingsystem.repositories.flight.FlightRepository;
import com.example.bookingsystem.services.seat.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Configuration
public class Data {
    private final SeatService seatService;

    @Bean
    public CommandLineRunner seedFlights(FlightRepository flightRepository) {
        return args -> {
            List<String> airports = List.of(
                "Tallinn",
                "Riga",
                "Helsinki",
                "Stockholm",
                "Oslo",
                "Copenhagen"
            );

            Random random = new Random();

            for (int i = 1; i <= 15; i++) {
                String departure = airports.get(random.nextInt(airports.size()));
                String arrival;

                do {
                    arrival = airports.get(random.nextInt(airports.size()));
                } while (arrival.equals(departure));

                FlightEntity flight = new FlightEntity();
                flight.setFlightNumber("FL-" + (1000 + i));
                flight.setDepartureAirport(departure);
                flight.setArrivalAirport(arrival);
                flight.setDepartureTime(LocalDateTime.now().plusDays(random.nextInt(10) + 1));
                flight.setArrivalTime(flight.getDepartureTime().plusHours(1 + random.nextInt(3)));
                flight.setPrice(50.0 + random.nextInt(200));

                flightRepository.save(flight);
                seatService.generateSeats(flight.getFlightId());
                seatService.randomSeatsBooked(flight.getFlightId());
            }
        };
    }
}
