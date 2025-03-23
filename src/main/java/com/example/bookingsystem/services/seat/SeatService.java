package com.example.bookingsystem.services.seat;

import com.example.bookingsystem.dto.seat.SeatDtoOut;
import com.example.bookingsystem.entities.seat.SeatEntity;
import com.example.bookingsystem.mappers.seat.SeatMapper;
import com.example.bookingsystem.repositories.seat.SeatRepository;
import com.example.bookingsystem.specifications.seat.SeatSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class SeatService {
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;
    private final List<String> seatLetters = Arrays.asList("A", "B", "C", "D", "E", "F");

    public void generateSeats(Long flightId) {
        List<SeatEntity> seats = new ArrayList<>();
        for (int row = 1; row <= 33; row++) {
            for (String letter : seatLetters) {
                SeatEntity seatEntity = new SeatEntity();
                seatEntity.setSeatNumber(row + letter);
                seatEntity.setFlightId(flightId);

                // Should be replaced by enum
                if (row <= 4) {
                    seatEntity.setClassType("first");
                } else if (row <= 7) {
                    seatEntity.setClassType("business");
                } else {
                    seatEntity.setClassType("economy");
                }

                seatEntity.setHasExtraLegroom(row == 14 || row <= 4 || (row >= 5 && row <= 7));

                seatEntity.setIsNearExit(row <= 5 || row >= 29);

                seatEntity.setIsReserved(false);
                seats.add(seatEntity);
            }
        }
        seatRepository.saveAll(seats);
    }

    public List<SeatDtoOut> randomSeatsBooked(Long flightId) {
        List<SeatEntity> seats = seatRepository.findAllByFlightId(flightId);

        if (seats.isEmpty()) {
            throw new IllegalStateException("No seats found for flight ID: " + flightId);
        }

        Random random = new Random();
        double randomPercentage = 0.1 + (0.5 * random.nextDouble());
        int seatsToBook = (int) (seats.size() * randomPercentage);
        Collections.shuffle(seats);

        for (int i = 0; i < seatsToBook; i++) {
            seats.get(i).setIsReserved(true);
        }

        for (int i = seatsToBook; i < seats.size(); i++) {
            seats.get(i).setIsReserved(false);
        }

        seatRepository.saveAll(seats);

        List<SeatDtoOut> reservedSeats = seatMapper.toDtoList(seats.stream()
                .filter(SeatEntity::getIsReserved)
                .toList());

        if (reservedSeats.isEmpty()) {
            System.out.println("No seats were reserved.");
        }

        return reservedSeats;
    }

    // search function
    public List<SeatDtoOut> getAllByFlightId(Long flightId, String classType, Boolean isNearExit,
                                             Boolean hasExtraLegroom, Integer seatNums) throws IllegalArgumentException {
        if (seatNums > 198) {
            throw new IllegalArgumentException("Number of seats exceeds seats on plane " + flightId);
        }

        Specification<SeatEntity> spec = Specification.where(SeatSpecification.getByFlightId(flightId)
                .and(SeatSpecification.getByClassType(classType))
                .and(SeatSpecification.getByIsNearExit(isNearExit))
                .and(SeatSpecification.getByHasExtraLegroom(hasExtraLegroom))
                .and(SeatSpecification.getByIsReserved(false)));

        List<SeatEntity> seats = seatRepository.findAll(spec);

        return seats.stream().limit(seatNums).map(seatMapper::toDto).toList();
    }

    public List<SeatDtoOut> getByFlightId(Long flightId) throws ChangeSetPersister.NotFoundException {
        List<SeatEntity> seats = seatRepository.findAllByFlightId(flightId);
        if (seats.isEmpty()) {
            throw new ChangeSetPersister.NotFoundException();
        }
        return seatMapper.toDtoList(seats);
    }

    public List<SeatDtoOut> getFreeSeats(Long id) {
        return seatRepository.findAllByFlightId(id).stream()
                .filter(seat -> !seat.getIsReserved())
                .map(seatMapper::toDto)
                .toList();
    }

    public List<SeatDtoOut> resetSeatAvailability(Long id) {
        List<SeatEntity> seats = seatRepository.findAllByFlightId(id);
        seats.forEach(seat -> seat.setIsReserved(false));
        return seatMapper.toDtoList(seats);
    }

    // this need proper recommends seats function
}
