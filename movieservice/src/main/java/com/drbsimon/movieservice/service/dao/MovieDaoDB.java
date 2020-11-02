package com.drbsimon.movieservice.service.dao;

import com.drbsimon.movieservice.model.Movie;
import com.drbsimon.movieservice.repository.MovieRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class MovieDaoDB implements MovieDao {
    private final MovieRepository repository;

    @Override
    public Movie getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public List<Movie> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(Movie movie) {
        repository.save(movie);
    }
}
