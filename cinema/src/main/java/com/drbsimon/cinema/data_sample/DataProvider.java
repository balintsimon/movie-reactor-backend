package com.drbsimon.cinema.data_sample;

import com.drbsimon.cinema.entity.Room;
import com.drbsimon.cinema.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataProvider implements CommandLineRunner {

    private final RoomCreator roomCreator;
    private final SeatCreator seatCreator;
    private final RoomRepository roomRepository;

    public DataProvider(RoomCreator roomCreator, SeatCreator seatCreator, RoomRepository roomRepository) {
        this.roomCreator = roomCreator;
        this.seatCreator = seatCreator;
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(String... args) {
        int roomAmount = 1;
        int numberOfRows = 5;
        int numberOfSeatsPerRow = 9;
        roomCreator.createRooms(roomAmount, numberOfRows, numberOfSeatsPerRow);

        List<Room> rooms = roomRepository.findAll();
        for (Room room : rooms) {
            seatCreator.createSeatsForRoomData(room);
        }

    }
}
