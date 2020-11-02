package com.drbsimon.cinema.controller;

import com.drbsimon.cinema.model.Room;
import com.drbsimon.cinema.model.wrapper.RoomListWrapper;
import com.drbsimon.cinema.repository.RoomManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin
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
