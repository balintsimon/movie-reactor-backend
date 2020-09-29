package com.drbsimon.movieservice.controller;

import com.drbsimon.movieservice.entity.Movie;
import com.drbsimon.movieservice.model.MovieListWrapper;
import com.drbsimon.movieservice.repository.MovieManager;
import com.drbsimon.movieservice.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.security.AlgorithmConstraints;
import java.util.List;

//@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {
    private final MovieManager movieManager;

    @GetMapping
    public MovieListWrapper getAllMoviesWrapped() {
        return movieManager.getAllMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable("id") Long id) {
        return movieManager.getMovieById(id);
    }
}