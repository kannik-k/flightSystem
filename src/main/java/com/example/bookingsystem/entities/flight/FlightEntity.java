package com.example.bookingsystem.entities.flight;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Entity
@Table(name="flight")
public class FlightEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;
    @Column
    private String flightNumber;
    @Column
    private String departureAirport;
    @Column
    private String arrivalAirport;
    @Column
    private LocalDateTime departureTime;
    @Column
    private LocalDateTime arrivalTime;
    @Column
    private Double price;
}
