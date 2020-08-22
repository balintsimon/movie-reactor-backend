package com.drbsimon.cinema.repository;

import com.drbsimon.cinema.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Room getById(Long id);

    List<Room> findAll();
}
