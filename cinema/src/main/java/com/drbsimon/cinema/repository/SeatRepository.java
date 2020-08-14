package com.drbsimon.cinema.repository;

import com.drbsimon.cinema.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> getSeatsByRoomId(Long id);

    Seat getById(Long id);
}
