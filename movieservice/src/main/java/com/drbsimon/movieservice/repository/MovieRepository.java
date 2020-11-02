package com.drbsimon.movieservice.repository;

import com.drbsimon.movieservice.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie getById(Long id);

    List<Movie> findAll();
}
