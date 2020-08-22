package com.drbsimon.cinema.data_sample;

import com.drbsimon.cinema.entity.Room;
import com.drbsimon.cinema.entity.Seat;
import com.drbsimon.cinema.repository.SeatRepository;
import org.springframework.stereotype.Component;

@Component
public class SeatCreator {
    private final SeatRepository seatRepository;

    public SeatCreator(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public void createSeatsForRoomData(Room room) {
        for (int row = 1; row < room.getNumberOfRows() + 1; row++) {
            for (int seatNumber = 1; seatNumber < room.getNumberOfSeatsPerRow() + 1; seatNumber++) {
                Seat newSeat = Seat.builder()
                        .rowNumber(row)
                        .seatNumber(seatNumber)
                        .room(room)
                        .build();
                seatRepository.save(newSeat);
            }
        }
    }
}
