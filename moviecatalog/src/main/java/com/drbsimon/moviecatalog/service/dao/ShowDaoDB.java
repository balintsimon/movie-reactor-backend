package com.drbsimon.moviecatalog.service.dao;

import com.drbsimon.moviecatalog.model.Show;
import com.drbsimon.moviecatalog.repository.ShowRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class ShowDaoDB implements ShowDao {
    private final ShowRepository repository;

    @Override
    public Show getShowById(Long showId) {
        return repository.getShowById(showId);
    }

    @Override
    public List<Show> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteAllByMovieId(Long movieId) {
        repository.deleteAllByMovieId(movieId);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void save(Show show) {
        repository.save(show);
    }
}
