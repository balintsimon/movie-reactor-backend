package com.drbsimon.movieservice.service.dao;

import com.drbsimon.movieservice.model.Movie;

import java.util.List;

public interface MovieDao {
    Movie getById(Long id);
    List<Movie> findAll();
    void save(Movie movie);
}
