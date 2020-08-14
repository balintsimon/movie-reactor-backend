package com.drbsimon.cinema.controller;

import com.drbsimon.cinema.entity.Seat;
import com.drbsimon.cinema.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/seat")
@RequiredArgsConstructor
public class SeatController {
    private final SeatRepository seatRepository;

    @GetMapping("/")
    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    @GetMapping("/{id}")
    public Seat getSeatById(@PathVariable("id") Long id) {
        return seatRepository.getById(id);
    }

    @GetMapping("/room/{id}")
    public List<Seat> getSeatByRoomId(@PathVariable("id") Long roomId) {
        return seatRepository.getSeatsByRoomId(roomId);
    }
}
