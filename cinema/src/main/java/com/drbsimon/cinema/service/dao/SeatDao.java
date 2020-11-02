package com.drbsimon.cinema.service.dao;

import com.drbsimon.cinema.model.Seat;

import java.util.List;

public interface SeatDao {
    List<Seat> findAll();
    List<Seat> getSeatsByRoomId(Long id);
    Seat getById(Long id);
    void save(Seat seat);
}
