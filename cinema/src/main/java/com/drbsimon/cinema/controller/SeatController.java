package com.drbsimon.cinema.controller;

import com.drbsimon.cinema.entity.Seat;
import com.drbsimon.cinema.model.SeatListWrapper;
import com.drbsimon.cinema.repository.SeatManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin
@RestController
@RequestMapping("/seat")
@RequiredArgsConstructor
public class SeatController {
    private final SeatManager seatManager;

    @GetMapping("/")
    public SeatListWrapper getAllSeats() {
        return seatManager.getAllSeats();
    }

    @GetMapping("/{id}")
    public Seat getSeatById(@PathVariable("id") Long id) {
        return seatManager.getSeatById(id);
    }

    @GetMapping("/room/{id}")
    public SeatListWrapper getSeatByRoomId(@PathVariable("id") Long roomId) {
        return seatManager.getAllSeatsByRoomId(roomId);
    }
}
