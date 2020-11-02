package com.drbsimon.cinema.service;

import com.drbsimon.cinema.model.Room;
import com.drbsimon.cinema.model.wrapper.RoomListWrapper;
import com.drbsimon.cinema.repository.RoomRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class RoomService {
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
