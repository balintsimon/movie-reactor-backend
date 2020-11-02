package com.drbsimon.cinema.service.dao;

import com.drbsimon.cinema.model.Room;
import com.drbsimon.cinema.repository.RoomRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class RoomDaoDB implements RoomDao {
    private final RoomRepository repository;

    @Override
    public Room getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public List<Room> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(Room room) {
        repository.save(room);
    }

    @Override
    public void saveAll(List<Room> rooms) {
        repository.saveAll(rooms);
    }
}
