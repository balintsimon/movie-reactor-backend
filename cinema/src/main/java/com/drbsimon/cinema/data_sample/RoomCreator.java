package com.drbsimon.cinema.data_sample;

import com.drbsimon.cinema.entity.Room;
import com.drbsimon.cinema.repository.RoomRepository;
import com.drbsimon.cinema.repository.SeatRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class RoomCreator {

    private final RoomRepository roomRepository;
    private final SeatRepository seatRepository;

    private final List<String> roomNames = Arrays.asList("Patkós Irma", "Huszárik Zoltán", "Uránia");
    Random random = new Random();

    public RoomCreator(RoomRepository roomRepository, SeatRepository seatRepository) {
        this.roomRepository = roomRepository;
        this.seatRepository = seatRepository;
    }

    public void createRooms(int numberOfRooms, int numberOfRows, int numberOfSeatPerRow) {
        List<Room> rooms = new ArrayList<>();
        for (int roomId = 1; roomId < numberOfRooms+1; roomId++) {
            String name = roomNames.get(random.nextInt(roomNames.size()));
            Room newRoom = Room.builder()
                    .name(name)
                    .numberOfRows(numberOfRows)
                    .numberOfSeatsPerRow(numberOfSeatPerRow)
                    .build();
            newRoom.calculateCapacity();
            rooms.add(newRoom);
        }
        roomRepository.saveAll(rooms);
    }


}
