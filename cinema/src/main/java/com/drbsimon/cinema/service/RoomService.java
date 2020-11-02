package com.drbsimon.cinema.service;

import com.drbsimon.cinema.model.Room;
import com.drbsimon.cinema.model.wrapper.RoomListWrapper;
import com.drbsimon.cinema.service.dao.RoomDao;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class RoomService {
    private final RoomDao roomDao;

    public RoomListWrapper getAllRooms() {
        RoomListWrapper roomListWrapper = new RoomListWrapper();
        List<Room> rooms = roomDao.findAll();
        roomListWrapper.setRooms(rooms);
        return roomListWrapper;
    }
    public Room getRoomById(Long id) {
        return roomDao.getById(id);
    }
}
