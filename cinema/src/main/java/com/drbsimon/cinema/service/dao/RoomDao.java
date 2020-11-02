package com.drbsimon.cinema.service.dao;

import com.drbsimon.cinema.model.Room;

import java.util.List;

public interface RoomDao {
    Room getById(Long id);
    List<Room> findAll();
    void save(Room room);
    void saveAll(List<Room> rooms);
}
