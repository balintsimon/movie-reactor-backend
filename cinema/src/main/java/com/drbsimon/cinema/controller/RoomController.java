package com.drbsimon.cinema.controller;

import com.drbsimon.cinema.model.Room;
import com.drbsimon.cinema.model.wrapper.RoomListWrapper;
import com.drbsimon.cinema.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin
@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping
    public RoomListWrapper getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoom(@PathVariable("id") Long id) {
        return roomService.getRoomById(id);
    }
}
