package com.drbsimon.cinema.service;

import com.drbsimon.cinema.model.Seat;
import com.drbsimon.cinema.model.wrapper.SeatListWrapper;
import com.drbsimon.cinema.service.dao.SeatDao;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class SeatService {
    private final SeatDao seatDao;

    public SeatListWrapper getAllSeats() {
        SeatListWrapper seatListWrapper = new SeatListWrapper();
        List<Seat> seats = seatDao.findAll();
        seatListWrapper.setSeats(seats);
        return seatListWrapper;
    }

    public SeatListWrapper getAllSeatsByRoomId(Long roomId) {
        SeatListWrapper seatListWrapper = new SeatListWrapper();
        List<Seat> seats = seatDao.getSeatsByRoomId(roomId);
        seatListWrapper.setSeats(seats);
        return seatListWrapper;
    }

    public Seat getSeatById(Long id) {
        return seatDao.getById(id);
    }
}
