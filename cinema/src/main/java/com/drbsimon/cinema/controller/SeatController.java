package com.drbsimon.cinema.controller;

import com.drbsimon.cinema.model.Seat;
import com.drbsimon.cinema.model.wrapper.SeatListWrapper;
import com.drbsimon.cinema.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin
@RestController
@RequestMapping("/seat")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;

    @GetMapping
    public SeatListWrapper getAllSeats() {
        return seatService.getAllSeats();
    }

    @GetMapping("/{id}")
    public Seat getSeatById(@PathVariable("id") Long id) {
        return seatService.getSeatById(id);
    }

    @GetMapping("/room/{id}")
    public SeatListWrapper getSeatByRoomId(@PathVariable("id") Long roomId) {
        return seatService.getAllSeatsByRoomId(roomId);
    }
}
