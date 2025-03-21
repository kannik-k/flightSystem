package com.example.bookingsystem.services.seat;

import com.example.bookingsystem.dto.seat.SeatDtoOut;
import com.example.bookingsystem.entities.seat.SeatEntity;
import com.example.bookingsystem.mappers.seat.SeatMapper;
import com.example.bookingsystem.repositories.seat.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
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
                if (row >= 1 && row <= 4) {
                    seatEntity.setClassType("first class");
                } else if (row >= 5 && row <= 7) {
                    seatEntity.setClassType("business class");
                } else {
                    seatEntity.setClassType("economy class");
                }

                if (row == 14 || row <= 4 || (row >= 5 && row <= 7)) {
                    seatEntity.setHasExtraLegroom(true);
                } else {
                    seatEntity.setHasExtraLegroom(false);
                }

                if (row <= 5 || row >= 29) {
                    seatEntity.setIsNearExit(true);
                } else {
                    seatEntity.setIsNearExit(false);
                }

                seatEntity.setIsReserved(false);

                seats.add(seatEntity);
            }
        }

        seatRepository.saveAll(seats);
    }

    public List<SeatDtoOut> randomSeatsBooked(Long flightId) {
        List<SeatEntity> seats = seatRepository.findAllByFlightId(flightId);
        Random random = new Random();
        double randomPercentage = 0.1 + (0.5 * random.nextDouble()); // 0.1 kuni 0.6 vahemik

        int seatsToBook = (int) (seats.size() * randomPercentage);

        Collections.shuffle(seats);

        for (int i = 0; i < seatsToBook; i++) {
            seats.get(i).setIsReserved(true);
        }
        for (int i = seatsToBook; i < seats.size(); i++) {
            seats.get(i).setIsReserved(false);
        }

        return seatMapper.toDtoList(seats);
    }

    public List<SeatDtoOut> allSeatsFree(Long flightId) {
        List<SeatEntity> seats = seatRepository.findAllByFlightId(flightId);
        seats.forEach(seat -> {seat.setIsReserved(false);});
        return seatMapper.toDtoList(seats);
    }

    public List<SeatDtoOut> getByFlightId(Long flightId) throws ChangeSetPersister.NotFoundException {
        List<SeatEntity> seats = seatRepository.findAllByFlightId(flightId);
        if (seats.isEmpty()) {
            throw new ChangeSetPersister.NotFoundException();
        }
        return seatMapper.toDtoList(seats);
    }
}
