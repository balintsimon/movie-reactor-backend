package com.drbsimon.cinema.repository;

import com.drbsimon.cinema.entity.Room;
import com.drbsimon.cinema.model.RoomListWrapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class RoomManager {
    private final RoomRepository roomRepository;

    public RoomListWrapper getAllRooms() {
        RoomListWrapper roomListWrapper = new RoomListWrapper();
        List<Room> rooms = roomRepository.findAll();
        roomListWrapper.setRooms(rooms);
        return roomListWrapper;
    }
    public Room getRoomById(Long id) {
        return roomRepository.getById(id);
    }
}
