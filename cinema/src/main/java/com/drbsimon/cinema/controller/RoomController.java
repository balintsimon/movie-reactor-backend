package com.drbsimon.cinema.controller;

import com.drbsimon.cinema.entity.Room;
import com.drbsimon.cinema.model.RoomListWrapper;
import com.drbsimon.cinema.repository.RoomManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomManager roomManager;

    @GetMapping
    public RoomListWrapper getAllRooms() {
        return roomManager.getAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoom(@PathVariable("id") Long id) {
        return roomManager.getRoomById(id);
    }
}
