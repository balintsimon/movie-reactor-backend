package com.drbsimon.movieservice.controller;

import com.drbsimon.movieservice.model.Movie;
import com.drbsimon.movieservice.model.wrapper.MovieListWrapper;
import com.drbsimon.movieservice.repository.MovieManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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