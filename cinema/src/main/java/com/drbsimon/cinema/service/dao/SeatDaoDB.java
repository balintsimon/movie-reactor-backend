package com.drbsimon.cinema.service.dao;

import com.drbsimon.cinema.model.Seat;
import com.drbsimon.cinema.repository.SeatRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class SeatDaoDB implements SeatDao {
    private final SeatRepository repository;

    @Override
    public List<Seat> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Seat> getSeatsByRoomId(Long id) {
        return repository.getSeatsByRoomId(id);
    }

    @Override
    public Seat getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public void save(Seat seat) {
        repository.save(seat);
    }
}
