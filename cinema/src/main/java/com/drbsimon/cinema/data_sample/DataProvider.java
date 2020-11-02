package com.drbsimon.cinema.data_sample;

import com.drbsimon.cinema.model.Room;
import com.drbsimon.cinema.service.dao.RoomDao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("production")
public class DataProvider implements CommandLineRunner {

    private final RoomCreator roomCreator;
    private final SeatCreator seatCreator;
    private final RoomDao roomDao;

    public DataProvider(RoomCreator roomCreator, SeatCreator seatCreator, RoomDao roomDao) {
        this.roomCreator = roomCreator;
        this.seatCreator = seatCreator;
        this.roomDao = roomDao;
    }

    @Override
    public void run(String... args) {
        int roomAmount = 1;
        int numberOfRows = 5;
        int numberOfSeatsPerRow = 10;
        roomCreator.createRooms(roomAmount, numberOfRows, numberOfSeatsPerRow);

        List<Room> rooms = roomDao.findAll();
        for (Room room : rooms) {
            seatCreator.createSeatsForRoomData(room);
        }

    }
}
