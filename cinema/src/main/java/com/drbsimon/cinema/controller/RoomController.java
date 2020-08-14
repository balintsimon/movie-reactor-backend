package com.drbsimon.cinema.controller;

import com.drbsimon.cinema.entity.Room;
import com.drbsimon.cinema.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomRepository roomRepository;

    @GetMapping("/")
    public List<Room> getAllRooms() {
        return roomRepository.getRooms();
    }

    @GetMapping("/{id}")
    public Room getRoom(@PathVariable("id") Long id) {
        return roomRepository.getById(id);
    }
}
