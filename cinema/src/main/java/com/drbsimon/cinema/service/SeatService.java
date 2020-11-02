package com.drbsimon.cinema.service;

import com.drbsimon.cinema.model.Seat;
import com.drbsimon.cinema.model.wrapper.SeatListWrapper;
import com.drbsimon.cinema.repository.SeatRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;

    public SeatListWrapper getAllSeats() {
        SeatListWrapper seatListWrapper = new SeatListWrapper();
        List<Seat> seats = seatRepository.findAll();
        seatListWrapper.setSeats(seats);
        return seatListWrapper;
    }

    public SeatListWrapper getAllSeatsByRoomId(Long roomId) {
        SeatListWrapper seatListWrapper = new SeatListWrapper();
        List<Seat> seats = seatRepository.getSeatsByRoomId(roomId);
        seatListWrapper.setSeats(seats);
        return seatListWrapper;
    }

    public Seat getSeatById(Long id) {
        return seatRepository.getById(id);
    }
}
